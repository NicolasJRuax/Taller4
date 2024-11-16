plugins {
    id("com.android.application")
}

android {
    namespace = "com.myproyect.taller_4"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.myproyect.taller_4"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    // Dependencias principales de Android
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Dependencia para fragmentos
    implementation("androidx.fragment:fragment-ktx:1.5.5")

    // Gson para serialización y deserialización de JSON (para persistencia de datos)
    implementation("com.google.code.gson:gson:2.9.0")

    // Dependencias para LiveData y ViewModel (si las necesitas más adelante)
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation(libs.firebase.crashlytics.buildtools)

    // Dependencias de prueba (opcional)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
