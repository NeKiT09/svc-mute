plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
    maven("https://maven.maxhenkel.de/repository/public")
}

dependencies {
    compileOnly("de.maxhenkel.voicechat:voicechat-api:2.6.0")

    compileOnly("org.jetbrains.kotlin:kotlin-stdlib")
}

val targetJavaVersion = 17
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.register("prepareKotlinBuildScriptModel"){}