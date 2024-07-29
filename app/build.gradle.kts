plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)

    //DAGGER HILT
    alias(libs.plugins.kspPlugin)
    alias(libs.plugins.daggerHiltPlugin)
}

android {
    namespace = "com.example.habitsappcourse"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.habitsappcourse"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.habitsappcourse.home.HiltTestRunner"
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
        kotlinCompilerExtensionVersion = "1.5.13"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    //MODULOS
    implementation(project(":core:core_presentation"))
    implementation(project(":settings:settings_presentation"))
    implementation(project(":onboarding:onboarding_presentation"))
    implementation(project(":onboarding:onboarding_data"))
    implementation(project(":onboarding:onboarding_domain"))
    implementation(project(":authentication:authentication_presentation"))
    implementation(project(":authentication:authentication_data"))
    implementation(project(":authentication:authentication_domain"))
    implementation(project(":home:home_presentation"))
    implementation(project(":home:home_domain"))
    implementation(project(":home:home_data"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //LIVE DATA
    implementation(libs.liveData)
    implementation(libs.runtime.livedata)

    //DAGGER HILT
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.android.compiler)
    ksp(libs.hilt.compiler)

    //NAVVIGATION  COMPOSE (NAV HOST)
    implementation(libs.nav.host)

    //MOCKITO
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.mockk)
    androidTestImplementation(libs.mockito.mockito.android)

    //CORUTINAS TEST
    testImplementation(libs.kotlinx.coroutines.test)

    //JUPITER
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.androidx.core.testing)

    //HILT TEST
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.android.compiler)

    //WORK MANAGER TEST
    androidTestImplementation(libs.androidx.work.testing)

    //MOCKITO TEST UI
    testImplementation(libs.mockito.core)
    androidTestImplementation(libs.mockito.mockito.android)
    testImplementation(libs.mockito.kotlin)

    //WORK MANAGER
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.work)

}
