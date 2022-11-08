package com.github.tuchg.nonasciicodecompletionhelper.config

import com.github.tuchg.nonasciicodecompletionhelper.spelling.convertRIMEDict
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.*

/**
 * View层
 * @author: tuchg
 * @date: 10/10/2021 09:56
 */
class PluginSettingsView {
    // V-M 的双向绑定
    val 配置 = 数据模型()
    var panel: DialogPanel

    init {
        panel = panel {
            group("拼写模式") {
                buttonsGroup(indent = false) {
                    row {
                        val 选项 = PatternType.values().map {
                            radioButton(it.name, it)
                        }

                        val 自定义 = 选项.last()

                        textFieldWithBrowseButton(
                            fileChooserDescriptor = FileChooserDescriptorFactory
                                .createSingleFileDescriptor("yaml")
                        )
                            .bindText(配置::自定义字典位置)
                            .visibleIf(自定义.selected)

                        button("重载字典") { 刷新自定义字典() }
                            .visibleIf(自定义.selected)

                    }.rowComment("选择输入模式")
                }.bind(配置::输入模式)
            }

            group("杂项配置") {
                row {
                    checkBox("增强补全")
                        .comment("效果等同于按两次补全快捷键，不建议开启，对性能影响较大")
                        .bindSelected(配置::增强补全)
                }
                row {
                    checkBox("多音字匹配纠正")
                        .comment("尝试解决多音字显示拼音与预期不一致，目前输入预期多音拼音即可显示预期效果")
                        .bindSelected(配置::多音字匹配纠正)
                        .enabled(false)
                }
                row {
                    checkBox("禁用 ASCII 命名检查")
                        .comment("一键关闭 ASCII 命名检查，避免中文标识符的警示效果")
                        .bindSelected(配置::`禁用 ASCII 命名检查`)
                        .enabled(false)
                }
                row {
                    checkBox("禁用驼峰命名检查")
                        .comment("一键关闭驼峰命名检查，避免中文标识符的警示效果")
                        .bindSelected(配置::禁用驼峰命名检查)
                        .enabled(false)
                }
            }
        }
    }

    private fun 刷新自定义字典() {
        convertRIMEDict(配置.自定义字典位置)
    }

}

data class 数据模型(
    var 增强补全: Boolean = false,
    var 多音字匹配纠正: Boolean = false,
    var `禁用 ASCII 命名检查`: Boolean = false,
    var 禁用驼峰命名检查: Boolean = false,

    var 输入模式: PatternType = PatternType.全拼,
    var 自定义字典位置: String = "",
)