# 目前已测试补全失效平台

## go

### 问题解决思路

* 在执行贡献器流程中前缀匹配器`PrfixMatcher` 执行到`go.jar!\com\goide\completion\GoReferenceCompletionProvider.class`

* 后被强行修改为驼峰前缀匹配，导致功能失效
  > 执行函数被修饰为`protected`且似乎未开放EPs(ExtensionsPoints)
  >
  > 另jetbrains明令说明未来将封禁反射，故无法直接在此处修改
* 通过静态工具类`go.jar!\com\goide\completion\GoCompletionUtil.class`对传入匹配器进行修改
  > 无法修改

### 解决方法

1. 费大力气对各语言psi作一次解析，经调查同类插件均以此法实现
2. 请求jetbrains对自家各语言前缀匹配器做出修改
3. 暴力覆盖驼峰前缀适配器

## kotlin

与上类似，在`CompletionSession`被修改

## rider (c#)

可按上述思路展开，但就目前所知，因C#平台特殊性，intellij与C#有前后端之分，恐难以实现