package com.github.tuchg.nonasciicodecompletionhelper.config

import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import org.jetbrains.annotations.NotNull
import javax.swing.JComponent
import javax.swing.JPanel


/**
 * View层
 * @author: tuchg
 * @date: 10/10/2021 09:56
 */
class PluginSettingsView {
    private var myMainPanel: JPanel? = null
    private val myUserNameText = JBTextField()
    private val myIdeaUserStatus = JBCheckBox("Do you use IntelliJ IDEA? ")

    init {
        myMainPanel = FormBuilder.createFormBuilder()
            .addLabeledComponent(JBLabel("Enter user name: "), myUserNameText, 1, false)
            .addComponent(myIdeaUserStatus, 1)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    fun getPanel(): JPanel? {
        return myMainPanel
    }

    fun getPreferredFocusedComponent(): JComponent? {
        return myUserNameText
    }

    @NotNull
    fun getUserNameText(): String? {
        return myUserNameText.text
    }

    fun setUserNameText(@NotNull newText: String?) {
        myUserNameText.text = newText
    }

    fun getIdeaUserStatus(): Boolean {
        return myIdeaUserStatus.isSelected
    }

    fun setIdeaUserStatus(newStatus: Boolean) {
        myIdeaUserStatus.isSelected = newStatus
    }


}