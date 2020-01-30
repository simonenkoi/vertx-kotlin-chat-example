plugins {
    kotlin("jvm") version "1.3.60"
    id("io.vertx.vertx-plugin") version "1.0.1"
}

group = "com.github.simonenko"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.vertx:vertx-core")
    implementation("io.vertx:vertx-web")
    implementation("io.vertx:vertx-lang-kotlin")
    implementation("org.koin:koin-core:2.0.1")
}

vertx {
    mainVerticle = "io.vertx.koin.example.BootstrapVerticle"
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}