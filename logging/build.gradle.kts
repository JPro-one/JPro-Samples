plugins {
    application
    id("org.openjfx.javafxplugin")
    id("jpro-gradle-plugin")
}

group = "one.jpro.samples"
version = "0.0.1"

repositories {
    mavenCentral()
}

javafx {
    version = properties["JAVAFX_VERSION"] as String
    modules = listOf("javafx.graphics", "javafx.controls", "javafx.fxml", "javafx.media", "javafx.web")
}

dependencies {
    implementation("org.slf4j:slf4j-api:2.0.7")
    implementation("org.slf4j:jul-to-slf4j:2.0.7")
    implementation("ch.qos.logback:logback-classic:1.4.7")

    testImplementation(platform("org.junit:junit-bom:5.9.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

application {
    mainModule.set("one.jpro.samples.logging")
    // Define the main class for the application.
    mainClass.set("one.jpro.samples.logging.SampleApp")
}

jpro {
    port = 8080
}

tasks.test {
    useJUnitPlatform()
}