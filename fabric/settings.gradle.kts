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
        //version("26.1")
        //version("1.21")
        //version("1.21.1")
        //version("1.21.2")
        //version("1.21.3")
        //version("1.21.4")
        //version("1.21.5")
        //version("1.21.6")
        //version("1.21.7")
        //version("1.21.8")
        //version("1.21.9")
        //version("1.21.10")
        //version("1.21.11")

        //version("26.2")

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