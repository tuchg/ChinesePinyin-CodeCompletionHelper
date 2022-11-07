package com.github.tuchg.nonasciicodecompletionhelper.utils


/**
 * 获取多音字组合 (笛卡尔积)
 *
 * @param 词组   输入字符串
 * @param 拼写   拼写函数，用于封装调用
 * @return 多音字拼接的结果数组
 * @Author tuchg
 */
fun 取多音字组合(词组: String, 拼写: (word: Char) -> Array<String>): Array<String> {
    val 笛卡尔积 = 词组
        .map { 单字 -> 拼写(单字) }
        .reduce { acc, strings ->
            acc.flatMap { a ->
                strings.map { b -> a + b }
            }.toTypedArray()
        }
    return 笛卡尔积
}

val counted = mutableSetOf<Int>()

/**
 * 计算字符串内包含需要的字符的数量
 * @param origin 文本串
 * @param needed 模式串
 */
fun countContainsSomeChar(origin: String, needed: String): Int {
    counted.clear()
    var i = 0
    var j = 0
    while (i < needed.length) {
        val indexOf = origin.indexOf(needed[i], j++)
        if (indexOf != -1) {
            if (counted.add(indexOf)) {
                i++
            }
        } else {
            i++
        }
    }
    return counted.size
}

