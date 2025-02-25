package app.futured.androidprojecttemplate

import ProjectSettings
import org.gradle.api.DefaultTask
import org.gradle.api.plugins.ExtraPropertiesExtension
import org.gradle.kotlin.dsl.configure

open class LintCheck : DefaultTask() {

    init {
        group = ProjectSettings.Gradle.TaskGroup

        /*
        These tasks runs for each subproject that has applied ktlint or detekt plugins.
        */
        configure<ExtraPropertiesExtension> {
            project.subprojects
                .filter { it.plugins.hasPlugin("org.jlleitschuh.gradle.ktlint") }
                .forEach {
                    dependsOn("${it.path}:ktlintCheck")
                }

            project.subprojects
                .filter { it.plugins.hasPlugin("io.gitlab.arturbosch.detekt") }
                .forEach {
                    dependsOn("${it.path}:detekt")
                }
        }
    }
}
