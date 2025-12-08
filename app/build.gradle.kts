plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.survivalcoding.gangnam2kiandroidstudy"
    compileSdk = 36 // 직접 숫자로 명시하는 것이 더 명확할 수 있습니다.

    defaultConfig {
        applicationId = "com.survivalcoding.gangnam2kiandroidstudy"
        minSdk = 24
        targetSdk = 36
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
    buildFeatures {
        compose = true
    }
}

dependencies {
    // --- 기본 구현 의존성 ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    // Compose Material Icons Extended 라이브러리 (필요시 사용)
    implementation("androidx.compose.material:material-icons-extended")

    // --- 코일 (이미지 로딩) ---
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)


    // --- 로컬 유닛 테스트 (test) ---
    testImplementation(libs.junit)

    // ▼▼▼▼▼ 바로 이 부분이 추가되어야 합니다! ▼▼▼▼▼
    // 로컬 테스트 환경에서 Compose UI 테스트를 실행하기 위한 라이브러리
    testImplementation(libs.androidx.compose.ui.test.junit4)
    // ▲▲▲▲▲ 여기까지가 핵심입니다 ▲▲▲▲▲


    // --- 안드로이드 계측 테스트 (androidTest) ---
    // toml에 정의된 버전을 사용하거나, 최신 안정 버전으로 직접 명시
    androidTestImplementation(libs.androidx.junit) // "androidx.test.ext:junit:1.1.5"
    androidTestImplementation(libs.androidx.espresso.core) // "androidx.test.espresso:espresso-core:3.5.1"
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)


    // --- 디버그용 의존성 ---
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
