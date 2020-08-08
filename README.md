# ChinesePinyinCodeCompletionHelper

![Build](https://github.com/tuchg/ChinesePinyin-CodeCompletionHelper/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/14838.svg)](https://plugins.jetbrains.com/plugin/14838)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/14838.svg)](https://plugins.jetbrains.com/plugin/14838)


![Java演示](screenshots/java.GIF)

<!-- Plugin description -->

<a href="https://github.com/tuchg/ChinesePinyin-CodeCompletionHelper">Github</a>主页 | <a href="https://github.com/tuchg/ChinesePinyin-CodeCompletionHelper/issues">Issues</a>页  

Make the Intellij Platform support Chinese Pinyin Completion

让你的 IDE 支持中文编程，享受和英文环境一致的中文智能编码体验

* 拼音输入补全，如输入zw，补全提示中文【ZhongWen】
* 多音字无差别补全
* 函数，变量，类名 ...等标识符，只要能用中文标识的通通都支持
* 支持全拼，五笔等各式输入方法（下一版本发布）

兼容JB 全家桶，无差别中文编程，无论是 Java，Kotlin，JavaScript，Golang ,C# 亦或者 Haskell 通通都支持....

<p></p>

提示：当提示 `no ascii`时，可按提示电灯泡关闭该提示

如果对您有所帮助，别忘了给本项目<a href="https://github.com/tuchg/ChinesePinyin-CodeCompletionHelper">Github</a>主页一个小星星😁

<!-- Plugin description end -->



## Installation

- Using IDE built-in plugin system:
  
  <kbd>Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "ChinesePinyin-CodeCompletionHelper"</kbd> >
  <kbd>Install Plugin</kbd>
  
- Manually:

  Download the [latest release](https://github.com/tuchg/ChinesePinyin-CodeCompletionHelper/releases/latest) and install it manually using
  <kbd>Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

## ToDo list
- [x] 支持多音字补全
- [ ] 支持非拼音输入补全，如五笔等

![Python 演示](screenshots/py.GIF)

---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
