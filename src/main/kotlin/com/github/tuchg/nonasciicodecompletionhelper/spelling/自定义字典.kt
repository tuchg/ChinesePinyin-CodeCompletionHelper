package com.github.tuchg.nonasciicodecompletionhelper.spelling

import com.github.tuchg.nonasciicodecompletionhelper.config.PluginSettingsState
import java.io.File
import java.util.*

fun convertRIMEDict(yamlPath: String = "") {
    val lastDict = PluginSettingsState.instance.dict
    lastDict.clear()

    File(yamlPath).readLines(
        Charsets.UTF_8
    ).map { it.trim() }.forEach {
        val row = it.split("\t")
        if (row.size <= 1 || row[0].length != 1) return@forEach

        if (lastDict.containsKey(row[0][0])) {
            lastDict[row[0][0]]!!.add(row[1])
        } else {
            lastDict[row[0][0]] = arrayListOf(row[1])
        }
    }
}

fun 自定义拼写(dict: MutableMap<Char, ArrayList<String>>, c: Char, caseType: CaseType): Array<String> =
    dict[c]?.map { s ->
        when (caseType) {
            CaseType.FIRST_UP_CASE -> {
                s.replaceFirst(s[0], s[0].uppercaseChar())
            }

            else -> {
                s.lowercase()
            }
        }
    }?.toTypedArray()
        ?: arrayOf(c.toString())