import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.myapps.mywallet"
    compileSdk = 34

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.myapps.mywallet"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        //Cargando los valores del archivo .properties
        val keystoreFile = project.rootProject.file("api.properties")
        val properties = Properties()
        properties.load(keystoreFile.inputStream())

        //Retornar clave vacia en caso de erro
        val apiKey = properties.getProperty("API_KEY") ?: ""
        val apiUrl = properties.getProperty("API_URL") ?: ""

        buildConfigField("String", "API_KEY", apiKey)
        buildConfigField("String", "API_URL", apiUrl)
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.preference)
    implementation(libs.navigation.runtime)
    implementation(libs.legacy.support.v4)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation (libs.material.v150)

    //Navigation
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.navigation.dynamic.features.fragment)
    androidTestImplementation(libs.navigation.testing)
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")

    //Retrofit
    implementation(libs.retrofit)
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    //Hashing
    implementation(libs.password4j)

}