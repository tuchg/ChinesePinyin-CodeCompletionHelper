package com.github.tuchg.nonasciicodecompletionhelper.config

import com.intellij.openapi.components.ServiceManager.getService
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil.copyBean

/**
 * 持久保存用户配置 -Model
 * @author: tuchg
 * @date: 2020/11/20 14:01
 */
@State(name = "CCompletionHelperSettings", storages = [(Storage("pinyin_completion_helper.xml"))])
class PluginSettingsState : PersistentStateComponent<PluginSettingsState> {
    // 提示方式设置，如全拼、五笔等
    public var inputPattern: PatternType = PatternType.全拼
    // 自定义码表，支持自定义码表 并提供图形支持

    // 用于解决一词多音不正确显示问题的选项
    public var enableCompleteMatch: Boolean = true

    // 激活强力补全 用于暴力补全部分补全未显示的问题
    public var enableForceCompletion: Boolean = false

    // todo 默认增加一个禁用 Ascii intention 的功能

    companion object {
        val instance: PluginSettingsState
            get() = getService(PluginSettingsState::class.java)
    }

    /**
     * 配置项
     */
    var version = "Unknown"

    override fun getState() = this

    override fun loadState(state: PluginSettingsState) {
        copyBean(state, this)
    }
}