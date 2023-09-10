import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm")
    kotlin("kapt")
}

val jar: Jar by tasks
val bootJar: BootJar by tasks
bootJar.enabled = false
jar.enabled = true
jar.archiveClassifier.convention("")

dependencies {
    runtimeOnly("com.h2database:h2")

    // yml
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // swagger
    implementation("org.springdoc:springdoc-openapi-ui:1.6.14")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // QueryDSL
//    implementation("com.querydsl:querydsl-jpa:5.0.0")
    kapt("org.springframework.boot:spring-boot-configuration-processor")

    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    annotationProcessor("com.querydsl:querydsl-apt:5.0.0:jakarta")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")
}
