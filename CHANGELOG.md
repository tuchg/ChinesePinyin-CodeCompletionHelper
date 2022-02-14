<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# Changelog

## [0.1.9] 2022-01-14

### Fixed

- fix plugin with platform compatible
- 修复 插件与平台的部分补全调用冲突问题
- fix some bugs
- 修复 推荐更新将 IDE至 2022.1 版本，可完美解决 kotlin、go、c/c++ 等语言的补全问题

### Changed

- added wubi、shuangpin is coming soon
- 预增 加五笔、双拼等支持，后续陆续支持
- improved performance
- 优化 插件性能优化
- upgrade API version
- 依赖 API升级

### Security

## [0.1.8] 2021-07-01

### Fixed

- 修复 安装插件后输入首字母补全无返回问题 issues#45 #I3PJ0Y
- 修复 中途智能输入问题 issues#44

### Security

## [0.1.7] 2021-03-07

### Fixed

- 修复.输入越界异常

### Security

## [0.1.6] 2021-02-09

### Added

### Changed

- 修改 列表优先级算法

### Deprecated

### Removed

### Fixed

- 修复 JavaScript 补全失效问题
- 修复 大多数语言长久以来的排序不能置顶问题

### Security

## [0.1.5] 2021-02-04

### Added

### Changed

- 优化代码结构

### Deprecated

### Removed

### Fixed

- 初步修复 Kotlin、Go补全问题，若不能正常补全，请按有效ascii字符(字母等)+中文的方式曲线救国
- 修复 输入单个字符无响应
- 修复 Go补全丢失图标问题

### Security

## [0.1.4] 2020-12-31

### Added

### Changed

### Deprecated

### Removed

### Fixed

* 修复启动后插件丢失问题

### Security

## [0.1.3] 2020-12-03

### Fixed

- 修复2020.3兼容问题

## [0.1.2] 2020-11-23

### Fixed

- 修复AndroidStudio兼容问题

## [0.1.1] 2020-11-20

### Added

- 增加插件配置，通知提醒
- 增加JetBrains支持

### Fixed

- 修复提醒问题
- 修复大小写混搭输入匹配问题
- 修复#9排序问题,为完全匹配项提供最高优先级

### Changed

- 平台依赖升级，增加忽略kotlin打包

## [0.1.0] 2020-11-20

### Added

- 增加插件配置，通知提醒
- JetBrains支持

### Fixed

- 修复大小写混搭输入匹配问题
- 修复#9排序问题,为完全匹配项提供最高优先级

### Changed

- 平台依赖升级，增加忽略kotlin打包

## [0.0.5] - 2020-09-23

### Fixed

- 大写英文带中文补全问题

## [0.0.4] - 2020-08-27

### Fixed

- 修复补全时部分提示信息丢失,如变量数据类型,包位置等
- 改进排序问题

### Changed

- 优化部分性能表现
- 优化缓存机制
- Kotlin 更新至 1.4.0

## [0.0.3] - 2020-08-16

### Changed

- 优化部分性能表现

### Fixed

- 出现部分无命中匹配的问题

## [0.0.2] - 2020-08-08

### Fixed

- 低版本调用高版本API,获取 Icon 的崩溃问题
- 调用方法等时方法体丢失的问题

## [0.0.1] - 2020-08-08

### Added

- 支持拼音输入补全
- 支持多音字补全
- Initial scaffold created
  from [IntelliJ Platform Plugin Template](https://github.com/JetBrains/intellij-platform-plugin-template)

## [Unreleased]