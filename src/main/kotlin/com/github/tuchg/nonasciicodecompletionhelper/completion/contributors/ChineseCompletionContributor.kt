package com.github.tuchg.nonasciicodecompletionhelper.completion.contributors

import com.github.tuchg.nonasciicodecompletionhelper.completion.ChineseLookupElement
import com.github.tuchg.nonasciicodecompletionhelper.completion.ChinesePrefixMatcher
import com.github.tuchg.nonasciicodecompletionhelper.config.PluginSettingsState
import com.github.tuchg.nonasciicodecompletionhelper.utils.countContainsSomeChar
import com.github.tuchg.nonasciicodecompletionhelper.utils.toPinyin
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.openapi.util.text.StringUtil
import pansong291.simplepinyin.Pinyin

/**
 * @author tuchg
 * @date 2020-8-1
 */
// 自定义了的语言
val languages = arrayOf("Go", "Kotlin", "C#")

open class ChineseCompletionContributor() : CompletionContributor() {
    override fun fillCompletionVariants(parameters: CompletionParameters, result: CompletionResultSet) {
        val pluginSettingsState = PluginSettingsState.instance

        // 手工过滤没必要执行的贡献器流程
        if (languages.contains(parameters.originalFile.fileType.name) && this.javaClass.simpleName == "ChineseCompletionContributor") {
            return
        }

        //feature:可暴力解决 bug:二次激活获取补全 但性能影响较大
        if (pluginSettingsState.enableForceCompletion) {
            parameters.withInvocationCount(2)
        }

        val prefix = result.prefixMatcher.prefix

        val resultSet = result
            .withPrefixMatcher(ChinesePrefixMatcher(result.prefixMatcher))
        resultSet.addLookupAdvertisement("输入拼音,补全中文标识符;若无满意结果,请再次激活补全快捷键或给出更精确的输入;不能正常使用可以试试字母汉字组合")

        // 先跳过当前 Contributors 获取包装后的 lookupElement而后进行修改装饰
        resultSet.runRemainingContributors(parameters) { r ->
            val element = r.lookupElement
            if (Pinyin.hasChinese(element.lookupString)) {
                // 从多音字列表提取命中次数最多的一个
                val closest = toPinyin(
                    element.lookupString,
                    Pinyin.FIRST_UP_CASE
                ).maxByOrNull { str -> countContainsSomeChar(str.toLowerCase(), prefix) }
                closest?.let {
                    //todo 完全匹配的优先级需提高
                    val priority = if (prefix.isNotEmpty()) StringUtil.difference(it, prefix) * 2.0 else 1.0
                    // 追加补全列表
                    renderElementHandle(element, it, priority, resultSet, r)
                }
            } else
                resultSet.passResult(r)
        }
        // 修复 输入单个字符本贡献器无响应
        resultSet.restartCompletionWhenNothingMatches()
    }

    open val renderElementHandle: (element: LookupElement, pinyin: String, priority: Double, rs: CompletionResultSet, r: CompletionResult) -> Unit =
        { element, pinyin, priority, rs, r ->
            val chineseLookupElement =
                ChineseLookupElement(element.lookupString, pinyin)
                    .copyFrom(element)
            val withPriority = PrioritizedLookupElement.withPriority(chineseLookupElement, priority)
            val wrap = CompletionResult.wrap(withPriority, r.prefixMatcher, r.sorter)
            if (wrap != null) {
                rs.passResult(wrap)
            }
        }
}
