plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
    maven("https://maven.maxhenkel.de/repository/public")
}

dependencies {
    compileOnly("de.maxhenkel.voicechat:voicechat-api:2.6.0")
}

val targetJavaVersion = 17
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.register("prepareKotlinBuildScriptModel"){}