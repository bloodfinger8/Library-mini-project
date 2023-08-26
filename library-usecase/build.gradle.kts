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
    implementation(project(":library-config"))
    implementation(project(":library-domain"))
    implementation(project(":library-type"))
    implementation(project(":library-exception"))
    implementation(project(":library-infrastructure"))
    implementation(project(":library-gateway"))

    implementation("org.springframework:spring-tx")
    implementation("org.springframework.boot:spring-boot-starter-security")

    // 암호화
    implementation("org.bouncycastle:bcprov-jdk15on:1.70")
}
