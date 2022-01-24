package com.github.tuchg.nonasciicodecompletionhelper.config

import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import com.intellij.util.ui.UI
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
    private val inputPatternChoice = ComboBox<String>()
    private val forceCompletionStatus = JBCheckBox("增强补全")
    private val completeMatchStatus = JBCheckBox("多音字匹配纠正")

    init {
        myMainPanel =
            FormBuilder.createFormBuilder().addLabeledComponent(JBLabel("Enter user name: "), myUserNameText, 1, false)
                .addComponent(
                    UI.PanelFactory.panel(forceCompletionStatus)
                        .withComment("激活强力补全，效果等同于按两次补全快捷键，不建议开启，对性能影响较大")
                        .createPanel(), 1
                ).addComponentFillVertically(JPanel(), 0).panel
        inputPatternChoice.isEditable = false

//        panel {
//            row {
//                myIdeaUserStatus
//                comment("测试")
//            }
//        }
    }

    fun getPanel(): JPanel? {
        return myMainPanel
    }

    fun getPreferredFocusedComponent(): JComponent {
        return myUserNameText
    }

    @NotNull
    fun getUserNameText(): String? {
        return myUserNameText.text
    }

    fun setUserNameText(@NotNull newText: String?) {
        myUserNameText.text = newText
    }

    fun getCompleteMatchStatus(): Boolean = completeMatchStatus.isSelected

    fun setCompleteMatchStatus(newStatus: Boolean) {
        completeMatchStatus.isSelected = newStatus
    }

    fun getForceCompletionStatus(): Boolean = forceCompletionStatus.isSelected

    fun setForceCompletionStatus(newStatus: Boolean) {
        forceCompletionStatus.isSelected = newStatus
    }


}