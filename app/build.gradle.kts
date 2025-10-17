plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.desafiodevspace.devquiz"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.desafiodevspace.devquiz"
        minSdk = 31
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

    //spash screen
        implementation("androidx.core:core-splashscreen:1.0.1")
    //lottie
    implementation("com.airbnb.android:lottie-compose:6.4.0")
    // --- Core Android ---
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("androidx.activity:activity-compose:1.9.2")

    // --- Compose Base ---
    implementation(platform("androidx.compose:compose-bom:2024.09.02"))
    implementation("androidx.compose.ui:ui:1.7.5")
    implementation("androidx.compose.ui:ui-graphics:1.7.5")
    implementation("androidx.compose.ui:ui-tooling-preview:1.7.5")

    // --- Material 3 ---
    implementation("androidx.compose.material3:material3:1.3.0")

    // --- Ícones Padrão (para BottomNavigation e outros) ---
    implementation("androidx.compose.material:material-icons-extended:1.7.5")

    // --- Navegação Compose ---
    implementation("androidx.navigation:navigation-compose:2.8.3")

    // --- Lifecycle Compose Integration ---
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.4")

    // --- Testes ---
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.09.02"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.7.5")

    // --- Debug Tools ---
    debugImplementation("androidx.compose.ui:ui-tooling:1.7.5")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.7.5")
}