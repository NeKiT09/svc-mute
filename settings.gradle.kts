rootProject.name = "svc-mute"


pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/") {
            name = "Fabric"
        }
        gradlePluginPortal()
    }
}

plugins {
    kotlin("jvm") version "2.4.0" apply false
    id("com.gradleup.shadow") version "9.0.0-beta4" apply false
}

include(
    "paper",
    "core"
)

includeBuild("fabric"){

}

includeBuild("neoforge"){

}
