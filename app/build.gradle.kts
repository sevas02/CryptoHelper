plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.cryptohelper"
    compileSdk = 35

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.example.cryptohelper"
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
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.room)
    implementation(libs.roomCommon)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    implementation(libs.gson)
    implementation(libs.retrofit)
    implementation(libs.viewModel)
    implementation(libs.liveData)
    ksp(libs.liveDataCompiler)
    implementation(libs.reactiveStreams)
    implementation(libs.picasso)


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}