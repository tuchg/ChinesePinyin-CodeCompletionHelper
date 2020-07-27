package com.github.tuchg.nonasciicodecompletionhelper.services

import com.intellij.openapi.project.Project
import com.github.tuchg.nonasciicodecompletionhelper.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
