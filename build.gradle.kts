import app.futured.androidprojecttemplate.DependencyUpdates
import app.futured.androidprojecttemplate.LintCheck
import io.gitlab.arturbosch.detekt.report.ReportMergeTask

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.detekt) apply false
}

tasks {
    register<LintCheck>("lintCheck")
    register<DependencyUpdates>("dependencyUpdates")
    register<ReportMergeTask>("detektReportMerge") {
        output.set(rootProject.layout.buildDirectory.file("reports/detekt/merged.xml"))
    }
}
