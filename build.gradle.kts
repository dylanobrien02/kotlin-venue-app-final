plugins {
    kotlin("jvm") version "2.0.10" // Ensure you have this Kotlin version or change accordingly
}

group = "ie.setu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral() // Use Maven Central to fetch dependencies
}

dependencies {
    implementation(kotlin("stdlib")) // Add Kotlin standard library
    testImplementation(kotlin("test")) // For testing with Kotlin

    // For XML and JSON Streaming
    implementation("com.thoughtworks.xstream:xstream:1.4.18")
    implementation("org.codehaus.jettison:jettison:1.4.1")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(16) // Ensure your JVM toolchain is set correctly
}
