plugins {
    kotlin("jvm")
    id("fabric-loom") version "1.17-SNAPSHOT"
}

version = rootProject.property("mod_version")!!
group = rootProject.group

repositories {
    mavenCentral()

    maven("https://maven.maxhenkel.de/repository/public")

}

fun prop(name: String): String =
    project.property(name).toString()

dependencies {

    include(implementation("svc-mute:core")!!)

    minecraft(
        "com.mojang:minecraft:${prop("minecraft_version")}"
    )

    val mc = prop("minecraft_version")

    if (mc.startsWith("26.")) {

        mappings(
            loom.layered {
                officialMojangMappings()
            }
        )

    } else {

        mappings(
            "net.fabricmc:yarn:${prop("yarn_mappings")}:v2"
        )
    }

    modImplementation(
        "net.fabricmc:fabric-loader:${prop("loader_version")}"
    )

    modImplementation(
        "net.fabricmc.fabric-api:fabric-api:${prop("fabric_version")}"
    )

    modImplementation(
        "net.fabricmc:fabric-language-kotlin:${prop("kotlin_loader_version")}"
    )

    modImplementation("net.kyori:adventure-platform-fabric:${prop("adventure_version")}")
    include(implementation("org.xerial:sqlite-jdbc:3.46.1.3")!!)

    compileOnly("de.maxhenkel.voicechat:voicechat-api:2.6.0")
    modImplementation("me.lucko:fabric-permissions-api:${prop("permission_api_version")}")
}

tasks.processResources {

    val props = mapOf(
        "version" to project.version,

        "minecraft_version" to prop("minecraft_version"),

        "loader_version" to prop("loader_version"),

        "fabric_version" to prop("fabric_version"),

        "kotlin_loader_version" to prop("kotlin_loader_version")
    )

    inputs.properties(props)

    filesMatching("fabric.mod.json") {
        expand(props)
    }
}

base{
    archivesName.set("svc-mute-fabric-${prop("minecraft_version")}")
}