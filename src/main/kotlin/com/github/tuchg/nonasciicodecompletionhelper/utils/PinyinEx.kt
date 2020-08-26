package com.github.tuchg.nonasciicodecompletionhelper.utils

import pansong291.simplepinyin.Pinyin

class PinyinEx {
    companion object {
        @JvmStatic
        val cache1 = HashMap<String, Int>()

        @JvmStatic
        val cache2 = HashMap<String, String>()
    }
}


/**
 * 获取多音字组合
 *
 * @param str      输入字符串
 * @param caseType 大小写类型
 * @return 多音字拼接的结果数组
 * @Author tuchg
 */
fun toPinyin(str: String, caseType: Int): Array<String> {
    return str
            .map { element -> Pinyin.toPinyin(element, caseType) }
            .reduce { acc, strings ->
                acc.flatMap { a ->
                    strings.map { b -> a + b }
                }.toArray { size ->
                    arrayOfNulls<String>(size)
                }
            }
}

/**
 * 计算字符串内包含需要的字符的数量
 * @param origin 文本串
 * @param needed 模式串
 */
fun countContainsSomeChar(origin: String, needed: String): Int {
    val key = "$origin-$needed"
    PinyinEx.cache1[key]?.let {
        return it
    }
    val counted = mutableSetOf<Int>()
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
    val size = counted.size
    PinyinEx.cache1[key] = size
    return size
}

