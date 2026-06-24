import java.util.Properties
import java.io.FileInputStream

val secretsFile = rootProject.file("secret.properties")
val secrets = Properties()

if (secretsFile.exists()) {
    secrets.load(FileInputStream(secretsFile))
}

plugins {
    kotlin("jvm") version "2.4.0"
    id("com.gradleup.shadow") version "8.3.0"
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "org.zawarka"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven("https://maven.maxhenkel.de/repository/public")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compileOnly("de.maxhenkel.voicechat:voicechat-api:2.6.0")
}

val targetJavaVersion = 17
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.build {
    dependsOn("shadowJar")
    finalizedBy("copyPlugin")
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("paper-plugin.yml") {
        expand(props)
    }
}

tasks.register<Copy>("copyPlugin") {
    dependsOn(tasks.named("shadowJar"))

    from(layout.buildDirectory.file("libs/${project.name}-${project.version}-all.jar"))
    into("${secrets["SERVER_PATH"]}/plugins") // путь к папке plugins
}

tasks.register<Exec>("startServer") {
    workingDir = file("${secrets["SERVER_PATH"]}")
    commandLine("cmd", "/c", "start.bat")
}



