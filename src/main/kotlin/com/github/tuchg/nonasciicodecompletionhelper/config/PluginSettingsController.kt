package com.github.tuchg.nonasciicodecompletionhelper.config

import com.github.tuchg.nonasciicodecompletionhelper.spelling.convertRIMEDict
import com.intellij.openapi.options.BoundConfigurable
import com.intellij.openapi.ui.DialogPanel

/**
 * 连通State与Component
 * @author: tuchg
 * @date: 10/10/2021 09:56
 */
/**
 * Provides controller functionality for application settings.
 */
class PluginSettingsController : BoundConfigurable("配置中心") {
    private var settingsComp: PluginSettingsView? = null
    private lateinit var model: 数据模型
    private lateinit var state: PluginSettingsState

    override fun createPanel(): DialogPanel {
        settingsComp = PluginSettingsView()
        state = PluginSettingsState.instance
        model = settingsComp!!.配置
        return settingsComp!!.panel
    }

    override fun apply() {
        super.apply()
        state.enableForceCompletion = model.增强补全
        state.enableCompleteMatch = model.多音字匹配纠正
        state.inputPattern = model.输入模式
        state.disableCamelInspection = model.禁用驼峰命名检查
        state.disableAsciiInspection = model.`禁用 ASCII 命名检查`
        convertRIMEDict(model.自定义字典位置)
        state.customLocation = model.自定义字典位置
    }


    override fun reset() {
        model.增强补全 = state.enableForceCompletion
        model.多音字匹配纠正 = state.enableCompleteMatch
        model.输入模式 = state.inputPattern
        model.`禁用 ASCII 命名检查` = state.disableAsciiInspection
        model.禁用驼峰命名检查 = state.disableCamelInspection
        model.自定义字典位置 = state.customLocation
//                convertRIMEDict(state.customLocation)
        super.reset()
    }

    override fun disposeUIResources() {
        super.disposeUIResources()
        settingsComp = null
    }
}