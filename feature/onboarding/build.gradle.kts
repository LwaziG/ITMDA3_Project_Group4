plugins {

    id("naveenapps.plugin.android.feature")
    id("naveenapps.plugin.kotlin.basic")
    id("naveenapps.plugin.hilt")
    id("naveenapps.plugin.room")
    id("naveenapps.plugin.compose")
}


android {
    namespace = "com.naveenapps.expensemanager.feature.onboarding"

    defaultConfig {
        // The schemas directory contains a schema file for each version of the Room database.
        // This is required to enable Room auto migrations.
        // See https://developer.android.com/reference/kotlin/androidx/room/AutoMigration.
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }
}

dependencies {
    implementation(project(":feature:country"))
    implementation(project(":feature:currency"))
    implementation(project(":feature:account"))
    implementation(libs.lottie.compose)
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
}