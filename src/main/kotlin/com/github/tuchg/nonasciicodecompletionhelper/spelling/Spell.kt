package com.github.tuchg.nonasciicodecompletionhelper.spelling

import com.github.tuchg.nonasciicodecompletionhelper.config.PatternType
import com.github.tuchg.nonasciicodecompletionhelper.config.PluginSettingsState
import com.github.tuchg.nonasciicodecompletionhelper.utils.取多音字组合
import pansong291.simplepinyin.Pinyin

enum class CaseType {
    FIRST_UP_CASE,
    LOW_CASE
}

var state: PluginSettingsState? = null

fun spellings(text: String, caseType: CaseType): Array<String> {
    if (state == null) {
        state = PluginSettingsState.instance
    }

    return when (state!!.inputPattern) {
        PatternType.全拼 -> {
            取多音字组合(text) { Pinyin.toPinyin(it, caseType.ordinal) }
        }


        else -> {
            取多音字组合(text) { 自定义拼写(state!!.dict, it, caseType) }
        }
    }

}