// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

    alias(libs.plugins.googleServices) apply false
    alias(libs.plugins.androidHilt) apply false
    id("com.google.firebase.crashlytics") version "3.0.3" apply false
}