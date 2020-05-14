import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.3.72")) // handle gradle.properties
    }

    // skip test
    gradle.taskGraph.whenReady {
        tasks.forEach { task ->
            if (task.name.contains("test")) {
                task.enabled = false
            }
        }
    }
}

plugins {
    id("org.springframework.boot") version "2.1.6.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.spring") version "1.3.72" // support spring annotation
    kotlin("plugin.jpa") version "1.3.72" // support jpa annotation
    kotlin("plugin.allopen") version "1.3.72" // custom
    kotlin("kapt") version "1.3.72"
}

group = "me.ely"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
    implementation("com.h2database:h2:")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("commons-codec:commons-codec:1.11")
    implementation("org.apache.commons:commons-lang3:3.8.1")
    implementation("io.springfox:springfox-swagger2:2.9.2")
    implementation("org.springframework.boot:spring-boot-starter-web")
    // implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:2.1.2.RELEASE")
    kapt("org.hibernate:hibernate-jpamodelgen:5.4.4.Final")
    implementation("org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.2.Final")
    implementation("org.hibernate:hibernate-core:5.4.4.Final")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.8")
    testImplementation("org.springframework.boot:spring-boot-starter-test")// {
    //     exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    // }
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")


    annotationProcessor("org.hibernate:hibernate-jpamodelgen:5.4.4.Final")
}

kapt {
    // generateStubs = true // 'kapt.generateStubs' is not used by the 'kotlin-kapt' plugin
    arguments {
        arg("addGeneratedAnnotation", "true")
    }
}
allOpen {
    annotation("me.ely.NoArgCons")
}

sourceSets {
    main {
        java.srcDirs.add(File("$buildDir/generated/source/kapt/main"))
        // this.allSource.srcDirs.add(File("$buildDir/generated/source/kapt/main"))
    }
    // named("main") {
    //     this.allSource.srcDirs.add(File("$buildDir/generated/source/kapt/main"))
    //     // kotlin.srcDirs("$buildDir/generated/source/kapt/main", "$buildDir/classes/kotlin/main")
    // }
    // main {
    //     kotlin {
    //         withConvention(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class) {
    //             // Configure Kotlin SourceDirectorySet
    //             kotlin.srcDirs("$buildDir/generated/source/kapt/main", "$buildDir/classes/kotlin/main")
    //         }
    //     }
    // }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// tasks.withType<JavaCompile> {
//     this.options.annotationProcessorGeneratedSourcesDirectory = File("src/generated/java")
// }

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}


// compileKotlin {
//     dependsOn 'copyJavaTemplates'
// }
