package com.github.tuchg.nonasciicodecompletionhelper.config

import com.intellij.openapi.components.ServiceManager.getService
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil.copyBean
import org.jetbrains.annotations.Nullable

/**
 * @author: tuchg
 * @date: 2020/11/20 14:01
 * @description:
 */
@State(name = "CCompletionHelperSettings", storages = [(Storage("chinese_completion_helper.xml"))])
class Settings : PersistentStateComponent<Settings> {
    companion object {
        val instance: Settings
            get() = getService(Settings::class.java)
    }

    /**
     * 配置项
     */
    var version = "Unknown"

    @Nullable
    override fun getState() = this

    override fun loadState(state: Settings) {
        copyBean(state, this)
    }
}