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

val JAVAFX_VERSION = properties["JAVAFX_VERSION"] as String
val SLF4J_VERSION = properties["SLF4J_VERSION"] as String
val LOGBACK_VERSION = properties["LOGBACK_VERSION"] as String
val JUNIT_VERSION = properties["JUNIT_VERSION"] as String

javafx {
    version = JAVAFX_VERSION
    modules = listOf("javafx.graphics", "javafx.controls", "javafx.fxml", "javafx.media", "javafx.web")
}

dependencies {
    implementation("org.slf4j:slf4j-api:$SLF4J_VERSION")
    implementation("org.slf4j:jul-to-slf4j:$SLF4J_VERSION")
    implementation("ch.qos.logback:logback-classic:$LOGBACK_VERSION")

    testImplementation(platform("org.junit:junit-bom:$JUNIT_VERSION"))
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