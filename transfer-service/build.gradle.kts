import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.plugin.jpa") version "1.3.31"
    id("org.springframework.boot") version "2.2.0.M3"
    id("io.spring.dependency-management") version "1.0.7.RELEASE"
    id("com.commercehub.gradle.plugin.avro") version "0.17.0"
    kotlin("jvm") version "1.3.31"
    kotlin("plugin.spring") version "1.3.31"
}

group = "com.codependent.outboxpattern.transfer"
version = "1.0.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
    maven("http://repo.spring.io/libs-release")
    maven("http://repo.spring.io/libs-milestone")
    maven("http://repo.spring.io/libs-snapshot")
}

extra["springCloudVersion"] = "Greenwich.SR1"
extra["springCloudStreamVersion"] = "Horsham.BUILD-SNAPSHOT"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.cloud:spring-cloud-stream")
    implementation("org.springframework.cloud:spring-cloud-stream-schema")
    implementation("org.springframework.cloud:spring-cloud-starter-stream-kafka")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.apache.avro:avro:1.9.0")
    runtime("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.cloud:spring-cloud-stream-test-support")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-stream-dependencies:${property("springCloudStreamVersion")}")
        //mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}
