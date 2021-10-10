package com.github.tuchg.nonasciicodecompletionhelper.completion

import com.intellij.codeInsight.completion.InsertionContext
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementPresentation
import javax.swing.Icon

/**
 * @author: tuchg
 * @date: 2020/8/6 21:46
 * @description:
 */
class ChineseLookupElement(
    // 原文本
    private val original: String?,
    // 取拼音后的文本
    private val pinyin: String?,
    private val icon: Icon? = null
) : LookupElement() {

    private var lookupElement: LookupElement? = null

    /**
     * 据此进行文本匹配
     */
    override fun getLookupString(): String {
        return original!!
    }

    /**
     * 控制该项在补全列表最终显示效果
     */
    override fun renderElement(presentation: LookupElementPresentation) {
        // 若传入图片则按图片渲染
        icon?.let {
            presentation.icon = icon
        }

        // 复制原元素类型,包位置,icon等信息
        lookupElement?.renderElement(presentation)
        // 文本【WenBen】
        presentation.itemText = "${this.original}【${this.pinyin}】"
    }

    /**
     * 借助原元素的编辑器文本插入能力, 如补全方法()调用等
     */
    override fun handleInsert(context: InsertionContext) {
        this.lookupElement?.let {
            if (it !is ChineseLookupElement) {
                it.handleInsert(context)
                return
            }
        }
        super.handleInsert(context)
    }

    /**
     * 用于复制一些特殊信息
     */
    fun copyFrom(lookupElement: LookupElement): ChineseLookupElement {
        this.lookupElement = lookupElement
        return this
    }
}
