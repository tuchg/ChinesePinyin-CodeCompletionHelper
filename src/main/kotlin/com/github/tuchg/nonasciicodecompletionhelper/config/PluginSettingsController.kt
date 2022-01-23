package com.github.tuchg.nonasciicodecompletionhelper.config

import com.intellij.openapi.options.Configurable
import javax.swing.JComponent

/**
 * 连通State与Component
 * @author: tuchg
 * @date: 10/10/2021 09:56
 */
/**
 * Provides controller functionality for application settings.
 */
class PluginSettingsController : Configurable {
    private var mySettingsComponent: PluginSettingsView? = null

    // A default constructor with no arguments is required because this implementation
    // is registered as an applicationConfigurable EP
    override fun getDisplayName(): String = "测试界面"

    override fun getPreferredFocusedComponent(): JComponent {
        return mySettingsComponent?.getPreferredFocusedComponent()!!
    }

    override fun createComponent(): JComponent? {
        mySettingsComponent = PluginSettingsView()
        return mySettingsComponent?.getPanel()
    }

    override fun isModified(): Boolean {
        val settings: PluginSettingsState = PluginSettingsState.instance
//        var modified: Boolean = !mySettingsComponent?.getUserNameText().equals(settings.userId)
//        modified = modified or (mySettingsComponent?.getIdeaUserStatus() ?:  !== settings.ideaStatus)
        return false
    }

    override fun apply() {
        val settings: PluginSettingsState = PluginSettingsState.instance
//        settings.userId = mySettingsComponent?.getUserNameText()
//        settings.ideaStatus = mySettingsComponent?.getIdeaUserStatus()
    }

    override fun reset() {
        val settings: PluginSettingsState = PluginSettingsState.instance
//        mySettingsComponent?.setUserNameText(settings.userId)
//        mySettingsComponent?.setIdeaUserStatus(settings.ideaStatus)
    }

    override fun disposeUIResources() {
        mySettingsComponent = null
    }
}