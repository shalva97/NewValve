plugins {
    kotlin("multiplatform") version "2.4.10"
    `maven-publish`
}

group = "io.github.shalva97"
version = "1.6"

repositories {
    mavenCentral()
    maven { setUrl("https://jitpack.io") }
}

kotlin {
    jvmToolchain(17)
    explicitApi()
    jvm()

    sourceSets {
        jvmMain.dependencies {
            api("com.github.teamnewpipe:NewPipeExtractor:0.26.3")
            implementation("com.squareup.okhttp3:okhttp:5.4.0")
        }
        jvmTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}
