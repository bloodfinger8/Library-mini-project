plugins {
	kotlin("jvm")
}

dependencies {
	implementation(project(":library-controller"))
	implementation(project(":library-config"))

	implementation("org.springframework.boot:spring-boot-starter-web")
}