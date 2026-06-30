pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/") {
            name = "Fabric"
        }
        maven("https://maven.kikugie.dev/releases")
        gradlePluginPortal()
    }
}
plugins {
    kotlin("jvm") version "2.4.0" apply false
    id("dev.kikugie.stonecutter") version "0.9.6"
}

stonecutter {
    create(rootProject) {

        version("1.21")
        version("1.21.1")
        version("1.21.2")
        version("1.21.3")
        version("1.21.4")

        vcsVersion = "1.21"
    }
}

includeBuild("..") {

    dependencySubstitution {

        substitute(
            module("${rootProject.name}:core")
        )
            .using(
                project(":core")
            )
    }
}