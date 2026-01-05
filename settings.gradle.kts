// settings.gradle.kts
pluginManagement {
    repositories {
        google()
        mavenCentral()
        // KSP, Hilt 같은 플러그인을 찾기 위해 이 저장소를 추가하는 것이 매우 중요합니다.
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        maven("https://maven.google.com")
        mavenCentral()
    }
}

rootProject.name = "Gangnam2kiAndroidStudy"
include(":app")
