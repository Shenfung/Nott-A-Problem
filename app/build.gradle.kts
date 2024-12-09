plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.nott_a_problem"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.nott_a_problem"
        minSdk = 33
        targetSdk = 35
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
    packagingOptions {
        resources{
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
        }
        resources.excludes.add("META-INF/DEPENDENCIES")
        resources.excludes.add("META-INF/LICENSE")
        resources.excludes.add("META-INF/LICENSE.txt")
        resources.excludes.add("META-INF/license.txt")
        resources.excludes.add("META-INF/NOTICE")
        resources.excludes.add("META-INF/NOTICE.txt")
        resources.excludes.add("META-INF/notice.txt")
        resources.excludes.add("META-INF/ASL2.0")
        resources.excludes.add("META-INF/*.kotlin_module")
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation(platform("androidx.compose:compose-bom:2024.11.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation ("androidx.compose.material:material:1.7.5")
    implementation("com.google.firebase:firebase-auth:23.1.0")
    implementation("com.google.firebase:firebase-firestore-ktx:25.1.1")
    implementation("com.android.volley:volley:1.2.1")
    implementation("com.google.android.gms:play-services-base:18.5.0")
    implementation("com.google.firebase:firebase-storage-ktx:21.0.1")
    implementation("com.google.android.gms:play-services-maps:19.0.0")
    implementation("androidx.lifecycle:lifecycle-runtime-testing:2.8.7")

    debugImplementation("androidx.compose.ui:ui-tooling")
    val composeBom = platform("androidx.compose:compose-bom:2024.10.01")
    implementation(composeBom)
    implementation ("androidx.navigation:navigation-compose:2.8.4")
    implementation ("androidx.compose.runtime:runtime-saveable:1.7.5")
    implementation (platform("com.google.firebase:firebase-bom:33.6.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.android.gms:play-services-auth:21.2.0")
    androidTestImplementation(composeBom)

    val credentialsManagerVersion = "1.5.0-alpha05"
    implementation("androidx.credentials:credentials:$credentialsManagerVersion")
    implementation("androidx.credentials:credentials-play-services-auth:$credentialsManagerVersion")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")
    implementation ("androidx.compose.material:material-icons-extended:1.7.5")
    implementation ("androidx.navigation:navigation-compose:2.8.4")
    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation ("com.airbnb.android:lottie-compose:6.0.1")
    implementation ("com.google.firebase:firebase-firestore-ktx")
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.15.1")
    implementation ("com.google.firebase:firebase-storage-ktx:21.0.1")
    implementation ("com.squareup.picasso:picasso:2.71828")
    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation ("com.google.android.gms:play-services-location:21.3.0")
    implementation("androidx.compose.ui:ui:1.7.5")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.github.bumptech.glide:glide:4.15.1")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.7")
    implementation ("com.google.maps.android:android-maps-utils:3.5.0")
    implementation ("com.google.maps.android:maps-compose:2.3.0")
    implementation ("com.google.firebase:firebase-storage:21.0.1")
    implementation("androidx.concurrent:concurrent-futures-ktx:1.2.0")
    androidTestImplementation("io.mockk:mockk-android:1.13.13")
    androidTestImplementation ("io.mockk:mockk-agent:1.13.13")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    debugImplementation ("androidx.fragment:fragment-testing:1.8.5")
}