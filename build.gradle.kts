plugins {
    kotlin("multiplatform") version "1.9.23"
    application
    `maven-publish`
}

group = "io.github.shalva97"
version = "1.1"

repositories {
    mavenCentral()
    maven { setUrl("https://jitpack.io") }
}

allprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
    explicitApi()
    jvm()

    sourceSets {
        val jvmMain by getting {
            dependencies {
                api("com.github.teamnewpipe:NewPipeExtractor:0.23.1")
                implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.12")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

application {
    mainClass.set("MainKt")
}
