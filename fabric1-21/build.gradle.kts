import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("fabric-loom") version "1.17-SNAPSHOT"
    id("maven-publish")
}

version = project.property("mod_version") as String
group = project.property("maven_group") as String

base {
    archivesName.set(project.property("archives_base_name") as String)
}

val targetJavaVersion = 17
java {
    toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
    withSourcesJar()
}

loom {
    // splitEnvironmentSourceSets() убрано - мод серверный
    mods {
        register("svc-mute-fabric") {
            sourceSet("main")
        }
    }
}

// fabricApi { configureDataGeneration } убрано полностью

repositories {
    mavenCentral()
    maven("https://maven.maxhenkel.de/repository/public")
}

dependencies {
    implementation(project(":core"))

    minecraft("com.mojang:minecraft:${project.property("minecraft_version")}")
    mappings("net.fabricmc:yarn:${project.property("yarn_mappings")}:v2")
    modImplementation("net.fabricmc:fabric-loader:${project.property("loader_version")}")
    modImplementation("net.fabricmc:fabric-language-kotlin:${project.property("kotlin_loader_version")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${project.property("fabric_version")}")

    modImplementation(include("net.kyori:adventure-platform-fabric:5.14.1")!!)
    modImplementation("me.lucko:fabric-permissions-api:0.3.3")

    include(implementation("org.xerial:sqlite-jdbc:3.46.1.3")!!)

    compileOnly("de.maxhenkel.voicechat:voicechat-api:2.6.0")
}

tasks.processResources {
    inputs.property("version", project.version)
    inputs.property("minecraft_version", project.property("minecraft_version"))
    inputs.property("loader_version", project.property("loader_version"))
    inputs.property("kotlin_loader_version", project.property("kotlin_loader_version"))
    filteringCharset = "UTF-8"
    filesMatching("fabric.mod.json") {
        expand(
            "version" to project.version,
            "minecraft_version" to project.property("minecraft_version")!!,
            "loader_version" to project.property("loader_version")!!,
            "kotlin_loader_version" to project.property("kotlin_loader_version")!!
        )
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(targetJavaVersion)
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions.jvmTarget.set(JvmTarget.fromTarget(targetJavaVersion.toString()))
}

tasks.jar {
    from("LICENSE") {
        rename { "${it}_${project.base.archivesName.get()}" }
    }

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    // Вшиваем все зависимости runtime в итоговый jar
    from(project(":core").sourceSets.main.get().output)

    // Исключаем подписи, иначе jar может не запуститься из-за конфликта подписей
    exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")
}

//tasks.shadowJar{
//    dependencies {
//        include(project(":core"))
//    }
//    archiveClassifier.set("")
//}

tasks.build{
    //dependsOn(tasks.shadowJar)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = project.property("archives_base_name") as String
            from(components["java"])
        }
    }
}