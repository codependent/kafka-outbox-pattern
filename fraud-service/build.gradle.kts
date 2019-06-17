import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.plugin.jpa") version "1.3.31"
    //id("org.springframework.boot") version "2.2.0.M3"
    id("org.springframework.boot") version "2.1.4.RELEASE"
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
    maven("https://repo.spring.io/libs-release")
    maven("https://repo.spring.io/libs-milestone")
    maven("https://repo.spring.io/libs-snapshot")
    maven("https://packages.confluent.io/maven/")
}

extra["springCloudVersion"] = "Greenwich.SR1"
//extra["springCloudStreamVersion"] = "Horsham.BUILD-SNAPSHOT"
extra["springCloudStreamVersion"] = "Germantown.RELEASE"
extra["confluentVersion"] = "5.2.1"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.cloud:spring-cloud-stream")
    implementation("org.springframework.cloud:spring-cloud-stream-schema")
    implementation("org.springframework.cloud:spring-cloud-stream-binder-kafka-streams")
    implementation("io.confluent:kafka-streams-avro-serde:${property("confluentVersion")}")
    implementation("io.confluent:kafka-avro-serializer:${property("confluentVersion")}")
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
