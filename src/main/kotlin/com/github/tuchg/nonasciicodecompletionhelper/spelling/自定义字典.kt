package com.github.tuchg.nonasciicodecompletionhelper.spelling

import com.github.tuchg.nonasciicodecompletionhelper.config.PluginSettingsState
import java.io.File
import kotlin.collections.ArrayList

fun convertRIMEDict(yamlPath: String = "") {
    if (yamlPath.isEmpty()) {
        return
    }
    val lastDict = PluginSettingsState.instance.dict
    lastDict.clear()

    File(yamlPath).readLines(
        Charsets.UTF_8
    ).map { it.trim() }.forEach {
        val row = it.split("\t")
        if (row.size <= 1 || row[0].length != 1) return@forEach

        val key = row[0][0].toString()
        if (lastDict.containsKey(key)) {
            lastDict[key]!!.add(row[1])
        } else {
            lastDict[key] = arrayListOf(row[1])
        }
    }
}

fun 自定义拼写(dict: MutableMap<String, ArrayList<String>>, c: String, caseType: CaseType): Array<String> =
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