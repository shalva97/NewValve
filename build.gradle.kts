plugins {
    kotlin("multiplatform") version "1.9.0"
    application
    id("io.realm.kotlin") version "1.11.0"
    `maven-publish`
}

group = "io.github.shalva97"
version = "1.0"

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
                implementation("com.github.teamnewpipe:NewPipeExtractor:0.22.7")
                implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation("io.realm.kotlin:library-base:1.11.0")
                implementation(kotlin("test"))
            }
        }
    }
}

application {
    mainClass.set("MainKt")
}
