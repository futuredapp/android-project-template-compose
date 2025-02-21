import app.futured.androidprojecttemplate.Clean
import app.futured.androidprojecttemplate.DependencyUpdates
import app.futured.androidprojecttemplate.LintCheck
import io.gitlab.arturbosch.detekt.report.ReportMergeTask

tasks {
    register<Clean>("clean")
    register<LintCheck>("lintCheck")
    register<DependencyUpdates>("dependencyUpdates")
    register<ReportMergeTask>("detektReportMerge") {
        output.set(rootProject.layout.buildDirectory.file("reports/detekt/merged.xml"))
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
