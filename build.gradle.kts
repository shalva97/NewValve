plugins {
    kotlin("multiplatform") version "2.0.0"
    application
    `maven-publish`
}

group = "io.github.shalva97"
version = "1.3"

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
                api("com.github.teamnewpipe:NewPipeExtractor:0.24.2")
                implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.14")
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
