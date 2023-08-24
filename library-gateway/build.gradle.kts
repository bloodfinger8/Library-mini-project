import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm")
}

val jar: Jar by tasks
val bootJar: BootJar by tasks
bootJar.enabled = false
jar.enabled = true
jar.archiveClassifier.convention("")

dependencies {
    implementation("com.github.pengrad:java-telegram-bot-api:6.7.0")

    implementation("org.springframework.boot:spring-boot-starter-data-redis")
}
