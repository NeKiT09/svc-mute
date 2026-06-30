
import java.util.Properties
import java.io.FileInputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

val secretsFile = rootProject.file("secret.properties")
val secrets = Properties()

if (secretsFile.exists()) {
    secrets.load(InputStreamReader(FileInputStream(secretsFile), StandardCharsets.UTF_8))
}

tasks.register("prepareKotlinBuildScriptModel"){}

plugins {
    kotlin("jvm")
    id("com.gradleup.shadow")
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "org.zawarka"
version = 1.2
val prevVersion = "1.0"
val projectName = "svc-mute"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }

    maven("https://maven.maxhenkel.de/repository/public")
}

dependencies {
    implementation(project(":core"))

    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.bstats:bstats-bukkit:3.2.1")

    compileOnly("de.maxhenkel.voicechat:voicechat-api:2.6.0")

    //compileOnly("ru.dimaskama.voicemessages:voicemessages-api:0.0.1")


}

tasks {
    runServer {
        // Configure the Minecraft version for our task.
        // This is the only required configuration besides applying the plugin.
        // Your plugin's jar (or shadowJar if present) will be used automatically.
        minecraftVersion("1.18")
    }
}

val targetJavaVersion = 17
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.build {
    dependsOn("shadowJar")
    finalizedBy("copyPlugin")
}

tasks.shadowJar{
    //configurations = project.configurations.runtimeClasspath.map { setOf(it) }

    dependencies {
        // Only merge bStats into the final jar, no other dependencies
    }

    // Relocate bStats into the plugin's package to avoid conflicts with other
    // plugins using bStats
    relocate("org.bstats", project.group.toString())

    archiveClassifier.set("")
    archiveFileName.set("${projectName}-${project.version}.jar")
}


tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}

tasks.register<Copy>("copyPlugin") {
    dependsOn(tasks.named("shadowJar"))

    from(project.layout.buildDirectory.file("libs/${projectName}-${project.version}.jar"))
    into("${secrets["SERVER_PATH"]}/plugins") // путь к папке plugins
}

tasks.register<Exec>("startServer") {
    workingDir = file("${secrets["SERVER_PATH"]}")
    commandLine("cmd", "/c", "start.bat")
}