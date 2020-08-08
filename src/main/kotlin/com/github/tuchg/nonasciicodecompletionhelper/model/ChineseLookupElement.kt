package com.github.tuchg.nonasciicodecompletionhelper.model

import com.intellij.codeInsight.completion.InsertionContext
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

    override fun renderElement(presentation: LookupElementPresentation) {
        // 文本【WenBen】
        presentation.itemText = "${this.original}【${this.pinyin}】"

        lookupElement?.let {
            // 版本向下兼容问题
            presentation.icon = DefaultLookupItemRenderer.getRawIcon(lookupElement, true)
        }
    }

    /**
     * 借助原元素的编辑器文本插入能力, 如补全方法调用
     */
    override fun handleInsert(context: InsertionContext) {
        this.lookupElement?.let {
            if (!(it is ChineseLookupElement))
                it.handleInsert(context)
        }
        super.handleInsert(context)
    }

    /**
     * 用于复制一些特殊信息
     */
    fun copyOtherLookup(lookupElement: LookupElement): ChineseLookupElement {
        this.lookupElement = lookupElement
        return this
    }
}
