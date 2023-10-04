plugins {
    java
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"
    jacoco
    checkstyle
}
checkstyle {
    configFile = file("checkstyle/custom_checks.xml")
    toolVersion = "10.12.4"
    reportsDir = file("${project.buildDir}/checkstyle")
    maxErrors = 0
    maxWarnings = 5
}
tasks.withType<Checkstyle>().configureEach {
    reports {
        xml.required.set(false)
        html.required.set(true)
        // html.stylesheet = resources.text.fromFile("config/xsl/checkstyle-custom.xsl")
    }
}

//runs only unit tests (all tests not tagged as integration tests)
task<Test>("unitTest") {
    description = "Runs unit tests."
    group = "verification"
    testClassesDirs = sourceSets.test.get().output.classesDirs
    classpath = sourceSets.test.get().runtimeClasspath
    filter {
        excludeTestsMatching("*IntegrationTest") // exclude integration tests
    }
    testLogging {
        events("passed")
    }
}
//runs only integration tests
task<Test>("integrationTest") {
    description = "Runs integration tests."
    group = "verification"
    testClassesDirs = sourceSets.test.get().output.classesDirs
    classpath = sourceSets.test.get().runtimeClasspath
    filter {
        includeTestsMatching("*IntegrationTest")
    }
    testLogging {
        events("passed")
    }
}
//runs all tests
tasks.test {
    finalizedBy(tasks.jacocoTestReport) // rapport skapas efter att testerna körts

    testLogging {
        events("passed")
    }
}
tasks.jacocoTestReport {
    //dependsOn(tasks.test, tasks.named("unitTest"))
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

}

tasks.withType<Test> {
    useJUnitPlatform()
}
