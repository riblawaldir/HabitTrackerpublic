plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.tuempresa.habittracker"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.tuempresa.habittracker"
        minSdk = 26
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
// Room dependencies
    implementation("androidx.room:room-runtime:2.7.1")
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    annotationProcessor("androidx.room:room-compiler:2.7.1")
    // Optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.7.1")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

}