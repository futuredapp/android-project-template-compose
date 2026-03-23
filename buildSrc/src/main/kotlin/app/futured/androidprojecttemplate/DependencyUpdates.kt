package app.futured.androidprojecttemplate

import ProjectSettings
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import com.github.benmanes.gradle.versions.updates.resolutionstrategy.ComponentSelectionWithCurrent
import org.gradle.api.Action

@Suppress("UnnecessaryAbstractClass")
abstract class DependencyUpdates : DependencyUpdatesTask() {

    init {
        group = ProjectSettings.Gradle.TaskGroup

        this.resolutionStrategy {
            componentSelection {
                all(
                    Action<ComponentSelectionWithCurrent> {
                        if (isNonStable(candidate.version) && !isNonStable(currentVersion)) {
                            reject("Release candidate")
                        }
                    }
                )
            }
        }
    }

    fun isNonStable(version: String): Boolean {
        val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
        val regex = "^[0-9,.v-]+(-r)?$".toRegex()
        val isStable = stableKeyword || regex.matches(version)
        return isStable.not()
    }
}
