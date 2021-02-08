package com.github.tuchg.nonasciicodecompletionhelper.extensions

import com.github.tuchg.nonasciicodecompletionhelper.model.ChineseLookupElement
import com.github.tuchg.nonasciicodecompletionhelper.model.ChinesePrefixMatcher
import com.github.tuchg.nonasciicodecompletionhelper.utils.countContainsSomeChar
import com.github.tuchg.nonasciicodecompletionhelper.utils.toPinyin
import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.completion.PrioritizedLookupElement
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.openapi.util.text.StringUtil
import pansong291.simplepinyin.Pinyin

/**
 * @author tuchg
 * @date 2020-8-1
 */
// 自定义了的语言
val languages = arrayOf("Go")

open class ChineseCompletionContributor() : CompletionContributor() {
    override fun fillCompletionVariants(parameters: CompletionParameters, result: CompletionResultSet) {
        // 手工过滤没必要的
        if (languages.contains(parameters.originalFile.fileType.name) && this.javaClass.simpleName == "ChineseCompletionContributor") {
            return
        }

        val prefix = result.prefixMatcher.prefix.toLowerCase()
        val resultSet = result
            .withPrefixMatcher(ChinesePrefixMatcher(prefix))
        resultSet.addLookupAdvertisement("输入拼音,补全中文标识符;若无满意结果,请再次激活补全快捷键或给出更精确的输入;不能正常使用可以试试字母汉字组合")
        /**
         * todo 可暴力解决 bug:[需二次激活获取补全] 但性能影响较大
         * parameters.withInvocationCount(2)
         */
        // 先跳过当前 Contributors 获取包装后的 lookupElement而后进行修改装饰
        resultSet.runRemainingContributors(parameters) { r ->
            val element = r.lookupElement
            if (Pinyin.hasChinese(element.lookupString)) {
                var flag = false
                // 从多音字列表提取命中次数最多的一个
                val closest = toPinyin(element.lookupString, Pinyin.FIRST_UP_CASE).let {
                    if (it.size == 1) {
                        return@let if (countContainsSomeChar(it[0].toLowerCase(), prefix) >= prefix.length) {
                            flag = true
                            it[0]
                        } else null
                    }
                    it.maxBy { str ->
                        val count = countContainsSomeChar(str.toLowerCase(), prefix)
                        if (count >= prefix.length) {
                            flag = true
                            count
                        } else -1
                    }
                }
                closest?.let {
                    val priority = StringUtil.difference(it, prefix) * 100.0
                    if (flag) {
                        // 追加补全列表
                        renderElementHandle(element, it, priority, resultSet)
                    }
                }
            } else
                resultSet.passResult(r)
        }
        // 修复 输入单个字符本贡献器无响应
        resultSet.restartCompletionWhenNothingMatches()
    }

    open val renderElementHandle: (element: LookupElement, pinyin: String, priority: Double, rs: CompletionResultSet) -> Unit =
        { element, pinyin, priority, rs ->
            val chineseLookupElement =
                ChineseLookupElement(element.lookupString, pinyin)
                    .copyFrom(element)
            rs.addElement(PrioritizedLookupElement.withPriority(chineseLookupElement, priority))
        };
}
