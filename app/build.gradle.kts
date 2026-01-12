plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.kotlin.plugin.serialization")
    alias(libs.plugins.ksp)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.survivalcoding.gangnam2kiandroidstudy"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.survivalcoding.gangnam2kiandroidstudy"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // Build Types
    buildTypes {
        debug {
            isDebuggable = true
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // Flavor
    flavorDimensions += "env"

    productFlavors {
        create("dev") {
            dimension = "env"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            buildConfigField("String", "ENV_NAME", "\"dev\"")
        }

        create("qa") {
            dimension = "env"
            applicationIdSuffix = ".qa"
            versionNameSuffix = "-qa"
            buildConfigField("String", "ENV_NAME", "\"qa\"")
        }

        create("prod") {
            dimension = "env"
            buildConfigField("String", "ENV_NAME", "\"prod\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    /*
     * Compose + ViewBinding을 동시에 사용하는 설정
     * - Compose: 신규 화면
     * - ViewBinding: 레거시 Fragment
     */
    buildFeatures {
        compose = true
        viewBinding = true
        buildConfig = true
    }

    testOptions {
        unitTests.all {
            it.setExcludes(
                listOf(
                    "com.survivalcoding.gangnam2kiandroidstudy.presentation.component.button.BigButtonUITest"
                )
            )
        }
    }
}

dependencies {

    /* ---------------- Firebase ---------------- */

    implementation(platform("com.google.firebase:firebase-bom:34.7.0"))
    implementation("com.google.firebase:firebase-auth")

    /* -------- Credential Manager (Google Login) -------- */

    implementation("androidx.credentials:credentials:1.5.0")
    implementation("androidx.credentials:credentials-play-services-auth:1.5.0")

    /* ---------------- AndroidX Core ---------------- */

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    /* ---------------- Compose ---------------- */

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation("androidx.compose.material:material-icons-extended")

    /* ---------------- Navigation ---------------- */

    implementation("androidx.navigation:navigation-compose:2.8.0-beta05")

    // Navigation3
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.material3.adaptive.navigation3)

    /* ---------------- Lifecycle ---------------- */

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.3")

    /* ---------------- Serialization ---------------- */

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.7")

    /* ---------------- Image ---------------- */

    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    /* ---------------- DI (Koin) ---------------- */

    implementation("io.insert-koin:koin-android:3.5.6")
    implementation("io.insert-koin:koin-androidx-compose:3.5.6")

    /* ---------------- Room ---------------- */

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.googleid)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.recyclerview)
    ksp(libs.androidx.room.compiler)

    /* ---------------- Unit Test ---------------- */

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.truth)

    /* ---------------- Android Test ---------------- */

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.truth)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    /* ---------------- Debug ---------------- */

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
