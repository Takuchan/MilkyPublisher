plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "com.takuchan.milkypublisher"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.takuchan.milkypublisher"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core.android)
    implementation(libs.androidx.constraintlayout)

    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.lifecycle.runtime.compose.android)
    kapt(libs.androidx.room.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.hilt.android)
    implementation (libs.androidx.hilt.navigation.compose)
    kapt(libs.hilt.android.compiler)


    val camerax_version = "1.2.1"
    implementation("androidx.camera:camera-core:${camerax_version}")
    implementation("androidx.camera:camera-camera2:${camerax_version}")
    implementation("androidx.camera:camera-lifecycle:${camerax_version}")
    implementation("androidx.camera:camera-video:${camerax_version}")
    implementation("androidx.camera:camera-view:${camerax_version}")
    implementation("androidx.camera:camera-extensions:${camerax_version}")

    implementation ("com.google.mlkit:pose-detection:18.0.0-beta3")
    implementation ("com.google.mlkit:pose-detection-accurate:18.0.0-beta3")
    //MLKit faseDetection
    implementation ("com.google.android.gms:play-services-mlkit-face-detection:17.1.0")

    //camera Permisisson
    val accompanist_version = "0.33.1-alpha"
    implementation("com.google.accompanist:accompanist-permissions:$accompanist_version")

    implementation ("androidx.vectordrawable:vectordrawable:1.1.0")
    implementation ("com.jakewharton.timber:timber:4.7.1")

    implementation ("com.google.android.gms:play-services-mlkit-face-detection:17.1.0")

}