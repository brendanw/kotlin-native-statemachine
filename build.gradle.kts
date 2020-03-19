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
        val macosMain by getting {
            dependencies {
                //implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:1.3.3")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:1.3.3-native-mt")
            }
        }
        val macosTest by getting {
        }
    }
}
