plugins {
    `java-library`
    id("io.freefair.lombok") version "6.5.0.2"
    jacoco
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
}

dependencies {
    // QR Code Library
    implementation("com.google.zxing:core:3.5.0")
    implementation("com.google.zxing:javase:3.5.0")

    // Utilities
    implementation("org.apache.commons:commons-lang3:3.12.0")

    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("org.mockito:mockito-junit-jupiter:4.6.1")
    testImplementation("org.mockito:mockito-core:4.6.1")
    testImplementation("org.mockito:mockito-inline:4.6.1")
    testImplementation("org.assertj:assertj-core:3.23.1")
    // Comparing QR codes in tests https://romankh3.github.io/image-comparison/
    testImplementation("com.github.romankh3:image-comparison:4.4.0")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(false)
        csv.required.set(true)
        html.outputLocation.set(file("${buildDir}/reports/jacoco/Html"))
        csv.outputLocation.set(file("${buildDir}/reports/jacoco/jacoco.csv"))
    }
}

