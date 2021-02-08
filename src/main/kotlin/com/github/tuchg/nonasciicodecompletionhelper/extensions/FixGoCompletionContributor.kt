package com.github.tuchg.nonasciicodecompletionhelper.extensions

import com.github.tuchg.nonasciicodecompletionhelper.model.ChineseLookupElement
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.completion.PrioritizedLookupElement
import com.intellij.codeInsight.lookup.LookupElement

/**
 * @author: tuchg
 * @date: 2021/2/4 13:52
 * @description: 针对GO的图标丢失问题
 */
class FixGoCompletionContributor : ChineseCompletionContributor() {
    override val renderElementHandle: (element: LookupElement, pinyin: String, priority: Double, rs: CompletionResultSet) -> Unit =
        { element: LookupElement, pinyin: String, priority: Double, rs: CompletionResultSet ->
            val chineseLookupElement =
                ChineseLookupElement(element.lookupString, pinyin, element.psiElement?.getIcon(0))
                    .copyFrom(element)
            rs.addElement(PrioritizedLookupElement.withPriority(chineseLookupElement, priority))
        }
}