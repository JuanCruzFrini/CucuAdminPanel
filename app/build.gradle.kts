plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    //Firebase
    id("com.google.gms.google-services")
    //Necesario para compartir objetos entre screens con compose navigation
    id("kotlin-parcelize")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.serialization") version ("1.8.0")
}

android {
    namespace = "com.cucu.cucuadminpanel"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.cucu.cucuadminpanel"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

dependencies {
    val navVersion = "2.7.1"
    val lifecycle_version = "2.6.1"

    //json //Necesario para compartir objetos entre screens con compose navigation
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    implementation("androidx.navigation:navigation-compose:$navVersion")

    //constraint compose
    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    //view model compose
    implementation ("androidx.compose.runtime:runtime-livedata:1.5.0")

    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation ("com.google.firebase:firebase-firestore-ktx")
    implementation ("com.google.firebase:firebase-storage-ktx")

    //Coil
    implementation("io.coil-kt:coil-compose:2.4.0")

    //Glide
    implementation ("com.github.bumptech.glide:compose:1.0.0-alpha.5")

    //Dagger - Hilt
    implementation ("com.google.dagger:hilt-android:2.45")
    kapt ("com.google.dagger:hilt-android-compiler:2.44")
    kapt ("androidx.hilt:hilt-compiler:1.0.0")
    //para hiltViewModel()
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")

    // View Model and livedata KTX
    implementation ("androidx.lifecycle:lifecycle-common:$lifecycle_version")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")

    // Kotlin Flow
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.2")
    // Dependencia de Kotlin Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")
    // Dependencia de Kotlin Coroutines para Reactive Streams
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:1.7.2")


    //splash api
    implementation ("androidx.core:core-splashscreen:1.0.1")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}