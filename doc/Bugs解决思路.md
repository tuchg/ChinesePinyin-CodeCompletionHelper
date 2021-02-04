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

### 目前解决方法

```java
public static CompletionResultSet withCamelHumpPrefixMatcher(CompletionResultSet resultSet){
        // .....
        CompletionResultSet var10000=resultSet
            .withPrefixMatcher(
                    createPrefixMatcher(
                            resultSet.getPrefixMatcher()
                                .getPrefix()
                            )
                    );
        //.....
        return var10000;
        }
```
从以上代码可知通过修改prefix可进行一定范围的操作
## kotlin

## rider (c#)

可按上述思路展开，但就目前所知，因C#平台特殊性，intellij与C#有前后端之分，恐很难实现