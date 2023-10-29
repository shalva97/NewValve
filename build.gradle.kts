plugins {
    kotlin("jvm") version "1.9.0"
    application
    id("io.realm.kotlin") version "1.11.0"
}

group = "io.github.shalva97"
version = "1.0"

repositories {
    mavenCentral()
    maven { setUrl("https://jitpack.io") }
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.github.teamnewpipe:NewPipeExtractor:0.22.7")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    testImplementation("io.realm.kotlin:library-base:1.11.0")
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
}

application {
    mainClass.set("MainKt")
}
