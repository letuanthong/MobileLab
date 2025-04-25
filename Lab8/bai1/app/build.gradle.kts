plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "dev.mobile.bai1"
    compileSdk = 35

    defaultConfig {
        applicationId = "dev.mobile.bai1"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation (libs.okhttp)
    implementation(libs.appcompat.v161)
    implementation(libs.material.v1100)
    implementation(libs.constraintlayout.v214)
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.v115)
    androidTestImplementation(libs.espresso.core.v351)
}