plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.medisync"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.medisync"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
}
