package com.github.tuchg.nonasciicodecompletionhelper.model

import com.intellij.codeInsight.lookup.DefaultLookupItemRenderer
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementPresentation

/**
 * @author: tuchg
 * @date: 2020/8/6 21:46
 * @description:
 */
class ChineseLookupElement(
        // 据此排序
    val index: Int = 100,
        // 原文本

    private val original: String?,
        // 取拼音后的文本

    private val pinyin: String?
) : LookupElement() {

    private var lookupElement: LookupElement? = null

    /**
     * 据此搜索
     */
    override fun getLookupString(): String {
        return original!!
    }

//    override fun handleInsert(context: InsertionContext) {
// //        val end = context.tailOffset
// //        context.document.insertString(end + this.originalSuffix.length, this.pinyinSuffix)
// //        context.document.deleteString(end, end + this.originalSuffix.length)
//    }

    override fun renderElement(presentation: LookupElementPresentation) {
//        if (this.detail != null) {
//            presentation.typeText = this.detail
//        }

//        presentation.isItemTextBold = true
        // presentation.isStrikeout = this.deprecated
        presentation.itemText = "${this.original}【${this.pinyin}】"
        lookupElement?.let {
            presentation.icon = DefaultLookupItemRenderer.getRawIcon(lookupElement)
        }

//        presentation.appendTailTextItalic(this.originalSuffix, true)
//        presentation.tailText = this.originalSuffix
    }

    /**
     * 用于复制一些特殊信息
     */
    fun copyOtherLookup(lookupElement: LookupElement): ChineseLookupElement {
        this.lookupElement = lookupElement
        return this
    }
}
