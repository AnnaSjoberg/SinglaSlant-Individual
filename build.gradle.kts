plugins {
    java
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"
    jacoco
    checkstyle
}
group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation ("io.rest-assured:rest-assured:5.3.2")
    testImplementation("io.rest-assured:spring-mock-mvc:5.3.2")
    implementation("org.jsoup:jsoup:1.16.1")
}
tasks {
    val commonTestSettings: Test.() -> Unit = {
        useJUnitPlatform {}
        testLogging {
            events("passed", "failed", "skipped")
        }
    }

    val unitTest by registering(Test::class) {
        description = "Runs unit tests."
        commonTestSettings()
        useJUnitPlatform {
            excludeTags("IntegrationTest")
            excludeTags("SystemTest")
        }
    }

    val integrationTest by registering(Test::class) {
        description = "Runs integration tests."
        commonTestSettings()
        useJUnitPlatform {
            includeTags("IntegrationTest")
        }
    }

    val apiTest by registering(Test::class) {
        description = "Runs API tests."
        commonTestSettings()
        useJUnitPlatform {
            includeTags("SystemTest")
        }
    }
}
//runs all tests
tasks.test {
    finalizedBy(tasks.jacocoTestReport)

    testLogging {
        events("passed", "failed", "skipped")
    }
}
tasks.withType<Test> {
    useJUnitPlatform()
}

/*
//runs only unit tests (all tests not tagged as integration tests)
task<Test>("unitTest") {
    description = "Runs unit tests."
    group = "verification"
    testClassesDirs = sourceSets.test.get().output.classesDirs
    classpath = sourceSets.test.get().runtimeClasspath
    useJUnitPlatform {
        excludeTags ("IntegrationTest")
        excludeTags ("SystemTest")
    }
    testLogging {
        events("passed", "failed", "skipped")
    }
}
//runs only integration tests
task<Test>("integrationTest") {
    description = "Runs integration tests."
    group = "verification"
    testClassesDirs = sourceSets.test.get().output.classesDirs
    classpath = sourceSets.test.get().runtimeClasspath
    useJUnitPlatform {
        includeTags ("IntegrationTest")
        excludeTags ("SystemTest")
    }
    testLogging {
        events("passed", "failed", "skipped")
    }
}//runs only API tests
task<Test>("apiTest") {
    description = "Runs API tests."
    group = "verification"
    testClassesDirs = sourceSets.test.get().output.classesDirs
    classpath = sourceSets.test.get().runtimeClasspath
    useJUnitPlatform {
        includeTags("SystemTest")
    }
    testLogging {
        events("passed", "failed", "skipped")
    }
}
tasks.withType<Test> {
    useJUnitPlatform()
}
*/


tasks.jacocoTestReport {
    dependsOn(tasks.test)
}
jacoco {
    toolVersion = "0.8.9"
    reportsDirectory.set(layout.buildDirectory.dir("customJacocoReportDir"))
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
}

checkstyle {
    configFile = file("checkstyle/custom_checks.xml")
    toolVersion = "10.12.4"
    val output: Provider<Directory> = layout.buildDirectory.dir("/checkstyle")
    reportsDir = output.get().asFile
    maxErrors = 0
    maxWarnings = 5
}
tasks.withType<Checkstyle>().configureEach {
    reports {
        xml.required.set(false)
        html.required.set(true)
    }
}
