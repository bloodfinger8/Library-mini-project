import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.10" apply false
	id("io.spring.dependency-management") version "1.0.15.RELEASE" apply false
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21" apply false
	kotlin("plugin.jpa") version "1.6.21" apply false
	kotlin("kapt") version "1.6.21" apply false
}

subprojects{
	repositories {
		mavenCentral()
	}

	group = "com.group.libraryapp"
	version = "0.0.1-SNAPSHOT"

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "11"
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}

	tasks.withType<JavaCompile>{
		sourceCompatibility = "11"
		targetCompatibility = "11"
	}

	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "org.jetbrains.kotlin.plugin.spring")
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")

	dependencies {
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		implementation("org.springframework.boot:spring-boot-starter-data-jpa")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
		testImplementation("org.springframework.boot:spring-boot-starter-test")

		//kotest
		testImplementation("io.kotest:kotest-runner-junit5:5.5.5")
		testImplementation("io.kotest:kotest-assertions-core:5.5.5")
		testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.2")
	}
}