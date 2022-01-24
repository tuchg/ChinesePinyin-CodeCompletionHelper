package com.github.tuchg.nonasciicodecompletionhelper.completion

import com.github.tuchg.nonasciicodecompletionhelper.utils.countContainsSomeChar
import com.github.tuchg.nonasciicodecompletionhelper.utils.toPinyin
import com.intellij.codeInsight.completion.PlainPrefixMatcher
import com.intellij.codeInsight.completion.PrefixMatcher
import pansong291.simplepinyin.Pinyin

/**
 * 转换中文使之与IDE内提取标识符进行有效识别的关键类
 * @author: tuchg
 * @date: 2021/2/4 13:58
 */
class ChinesePrefixMatcher(prefixMatcher: PrefixMatcher) : PlainPrefixMatcher(prefixMatcher.prefix) {
    private var originalMatcher: PrefixMatcher? = prefixMatcher

    override fun prefixMatches(name: String): Boolean {
//        log.info { "${prefix} - ${name} ${myMatcher.matches(name)}" }
        return if (Pinyin.hasChinese(name)) {
            for (s in toPinyin(name, Pinyin.LOW_CASE)) {
                if (countContainsSomeChar(s, prefix) >= prefix.length) {
                    return true
                }
            }
            return false
        } else originalMatcher?.prefixMatches(name) == true
    }


    override fun cloneWithPrefix(prefix: String) =
        if (prefix == this.prefix) this else ChinesePrefixMatcher(originalMatcher!!.cloneWithPrefix(prefix))
}
