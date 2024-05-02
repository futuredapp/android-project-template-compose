package app.futured.androidprojecttemplate

import ProjectSettings
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.TaskAction

open class Clean : Delete() {

    init {
        group = ProjectSettings.TASK_GROUP
    }

    @TaskAction
    fun cleanProject() {
        delete(project.rootProject.layout.buildDirectory)
        project.rootProject.subprojects.forEach {
            delete(it.layout.buildDirectory)
        }
    }
}
