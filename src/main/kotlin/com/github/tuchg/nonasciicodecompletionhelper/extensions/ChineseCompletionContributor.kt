package com.github.tuchg.nonasciicodecompletionhelper.extensions

import com.github.tuchg.nonasciicodecompletionhelper.model.ChineseLookupElement
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementWeigher
import com.intellij.openapi.util.text.StringUtil
import pansong291.simplepinyin.Pinyin

// private val cache = HashMap<String, Boolean>()
/**
 * @author tuchg
 * @date 2020-8-1
 */
class ChineseCompletionContributor : CompletionContributor() {

    override fun fillCompletionVariants(parameters: CompletionParameters, result: CompletionResultSet) {
        val resultSet = result
                .withPrefixMatcher(ChinesePrefixMatcher(result.prefixMatcher.prefix))
                .withRelevanceSorter(CompletionSorter.defaultSorter(parameters, result.prefixMatcher).weigh(
                        object : LookupElementWeigher("ChineseCompletionElementWeigher", false, true) {
                            override fun weigh(element: LookupElement) = if (element is ChineseLookupElement) element.index else Int.MAX_VALUE
                        }
                ))
        resultSet.restartCompletionOnAnyPrefixChange()
        resultSet.addLookupAdvertisement("输入拼音,补全中文标识符")
        resultSet.runRemainingContributors(parameters) { r ->
            val element = r.lookupElement
            if (Pinyin.hasChinese(element.lookupString)) {
                // 填充补全列表
                val list = mutableListOf<ChineseLookupElement>()
                // 从多音字列表提取命中次数最多的一个
                val closest = Pinyin.toPinyin(element.lookupString, Pinyin.FIRST_UP_CASE).maxBy {
                    val count = countContainsSomeChar(it.toLowerCase(), r.prefixMatcher.prefix)
                    if (count >= r.prefixMatcher.prefix.length)
                        count
                    else
                        -1
                }
                closest?.let {
                    list.add(ChineseLookupElement(Int.MIN_VALUE, element.lookupString, it).copyOtherLookup(r.lookupElement))
                }
                resultSet.addAllElements(list)
            } else
                resultSet.passResult(r)
        }
    }
}

class ChinesePrefixMatcher(prefix: String) : PlainPrefixMatcher(prefix) {

    override fun prefixMatches(name: String): Boolean {
        return if (Pinyin.hasChinese(name)) {
            for (s in Pinyin.toPinyin(name, Pinyin.LOW_CASE)) {
                if (countContainsSomeChar(s, prefix) >= prefix.length) {
                    return true
                }
            }
            return false
        } else super.prefixMatches(name)
    }

    override fun cloneWithPrefix(prefix: String) = if (prefix == this.prefix) this else ChinesePrefixMatcher(prefix)
}

/**
 * 计算字符串内包含需要的字符的数量
 */
private fun countContainsSomeChar(origin: String, needed: String): Int {
    var count = 0
    for (c in needed) {

        if (StringUtil.containsChar(origin, c)) {
            count++
        }
    }
    return count
}
