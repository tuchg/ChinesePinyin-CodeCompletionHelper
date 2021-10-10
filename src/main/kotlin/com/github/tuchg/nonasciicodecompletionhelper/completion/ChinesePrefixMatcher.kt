package com.github.tuchg.nonasciicodecompletionhelper.completion

import com.github.tuchg.nonasciicodecompletionhelper.utils.countContainsSomeChar
import com.github.tuchg.nonasciicodecompletionhelper.utils.toPinyin
import com.intellij.codeInsight.CodeInsightSettings
import com.intellij.codeInsight.completion.PlainPrefixMatcher
import com.intellij.codeInsight.completion.impl.CamelHumpMatcher
import com.intellij.psi.codeStyle.MinusculeMatcher
import com.intellij.psi.codeStyle.NameUtil
import pansong291.simplepinyin.Pinyin

/**
 * 转换中文使之与IDE内提取标识符进行有效识别的关键类
 * @author: tuchg
 * @date: 2021/2/4 13:58
 */
class ChinesePrefixMatcher : PlainPrefixMatcher {
    private var myMatcher: MinusculeMatcher
    private var myCaseSensitive = false
    private var myTypoTolerant = false

    companion object {
//        val log = getLogger(ChinesePrefixMatcher::class)
    }

    constructor(prefix: String) : this(prefix, false, true)

    constructor(prefix: String, caseSensitive: Boolean, typoTolerant: Boolean) : super(prefix) {
        myCaseSensitive = caseSensitive
        myTypoTolerant = typoTolerant
        myMatcher = createMatcher(myCaseSensitive)
    }

    /**
     * copied from CamelHumpMathcer
     * 创建MinusculeMatcher 用于字符模式匹配
     */
    private fun createMatcher(caseSensitive: Boolean): MinusculeMatcher {
        val prefix = CamelHumpMatcher.applyMiddleMatching(myPrefix)
        var builder = NameUtil.buildMatcher(prefix)
        if (caseSensitive) {
            val setting = CodeInsightSettings.getInstance().COMPLETION_CASE_SENSITIVE
            if (setting == CodeInsightSettings.FIRST_LETTER) {
                builder = builder.withCaseSensitivity(NameUtil.MatchingCaseSensitivity.FIRST_LETTER)
            } else if (setting == CodeInsightSettings.ALL) {
                builder = builder.withCaseSensitivity(NameUtil.MatchingCaseSensitivity.ALL)
            }
        }
        if (myTypoTolerant) {
            builder = builder.typoTolerant()
        }
        return builder.build()
    }

    override fun prefixMatches(name: String): Boolean {
//        log.info { "${prefix} - ${name} ${myMatcher.matches(name)}" }
        return if (Pinyin.hasChinese(name)) {
            for (s in toPinyin(name, Pinyin.LOW_CASE)) {
                if (countContainsSomeChar(s, prefix) >= prefix.length) {
                    return true
                }
            }
            return false
        } else myMatcher.matches(name)
    }


    override fun cloneWithPrefix(prefix: String) = if (prefix == this.prefix) this else ChinesePrefixMatcher(prefix)
}
