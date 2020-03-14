plugins {
    id("org.jetbrains.kotlin.multiplatform").version("1.3.61")
}

repositories {
    maven("https://jitpack.io")
    maven("https://dl.bintray.com/kotlin/kotlinx")
    maven("https://dl.bintray.com/kotlin/ktor")
    maven("https://dl.bintray.com/sargunster/maven")
    maven("https://dl.bintray.com/kotlin/squash")
    maven("https://dl.bintray.com/kotlin/kotlin-dev")

    mavenLocal()
    mavenCentral()
    google()
    jcenter()
}

kotlin {
    // For ARM, should be changed to iosArm32 or iosArm64
    // For Linux, should be changed to e.g. linuxX64
    // For MacOS, should be changed to e.g. macosX64
    // For Windows, should be changed to e.g. mingwX64
    macosX64("macos") {
        binaries {
            executable {
                // Change to specify fully qualified name of your application's entry point:
               entryPoint = "sample.main"
                // Specify command-line arguments, if necessary:
                runTask?.args("")
            }
        }
    }
    sourceSets {
        // Note: To enable common source sets please comment out 'kotlin.import.noCommonSourceSets' property
        // in gradle.properties file and re-import your project in IDE.
        val macosMain by getting {
            dependencies {
                //implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:1.3.4")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:1.3.3-native-mt") {
                    version {
                        strictly("1.3.3-native-mt")
                    }
                }
            }
        }
        val macosTest by getting {
        }
    }
}

// Use the following Gradle tasks to run your application:
// :runReleaseExecutableMacos - without debug symbols
// :runDebugExecutableMacos - with debug symbols
