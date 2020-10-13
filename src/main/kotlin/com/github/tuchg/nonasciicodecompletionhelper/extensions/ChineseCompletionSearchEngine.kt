package com.github.tuchg.nonasciicodecompletionhelper.extensions

import com.intellij.find.FindInProjectSearchEngine
import com.intellij.find.FindInProjectSearchEngine.FindInProjectSearcher
import com.intellij.find.FindModel
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

/**
 * @author: tuchg
 * @date: 2020/10/13 21:51
 * @description: 提供在搜索框内的中文补全能力,API 尚处于试验阶段,暂且搁置
 */
class ChineseCompletionSearchEngine : FindInProjectSearchEngine {
    /**
     * Constructs a searcher for a given {@param findModel} which serves as a input query.
     */
    override fun createSearcher(findModel: FindModel, project: Project): ChineseCompletionSearcher {

        TODO("Not yet implemented")
    }

    class ChineseCompletionSearcher : FindInProjectSearcher {
        /**
         * @return files that contain non-trivial search results for corresponding [FindModel].
         */
        override fun searchForOccurrences(): MutableCollection<VirtualFile> {
            TODO("Not yet implemented")
        }

        /**
         * @return true if there are no occurrences can be found outside result of [FindInProjectSearcher.searchForOccurrences],
         * otherwise false.
         */
        override fun isReliable(): Boolean {
            TODO("Not yet implemented")
        }

        /**
         * Returns true if {@param file} is a part of "indexed" scope of corresponding search engine and no need to open file's content to find a query,
         * otherwise false.
         *
         *
         * Called only in case when searcher is not reliable (see [FindInProjectSearcher.isReliable]).
         */
        override fun isCovered(file: VirtualFile): Boolean {
            TODO("Not yet implemented")
        }

    }

}