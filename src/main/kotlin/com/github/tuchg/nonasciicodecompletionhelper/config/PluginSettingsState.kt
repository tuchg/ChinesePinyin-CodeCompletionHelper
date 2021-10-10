package com.github.tuchg.nonasciicodecompletionhelper.config

import com.intellij.openapi.components.ServiceManager.getService
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil.copyBean
import org.jetbrains.annotations.Nullable

/**
 * 持久保存用户配置 -Model
 * @author: tuchg
 * @date: 2020/11/20 14:01
 */
@State(name = "CCompletionHelperSettings", storages = [(Storage("chinese_completion_helper.xml"))])
class PluginSettingsState : PersistentStateComponent<PluginSettingsState> {
    companion object {
        val instance: PluginSettingsState
            get() = getService(PluginSettingsState::class.java)
    }

    /**
     * 配置项
     */
    var version = "Unknown"

    @Nullable
    override fun getState() = this

    override fun loadState(state: PluginSettingsState) {
        copyBean(state, this)
    }
}