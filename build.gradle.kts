plugins {
    java
    kotlin("jvm") version "1.3.72"

    id("com.github.johnrengelman.shadow") version "6.0.0"
}

apply(plugin = "com.github.johnrengelman.shadow")

group = "com.noth"
version = "0.0.1"

val jdaVersion = "4.2.0_184"
val xodusVersion = "1.3.232"
val logbackVersion = "1.1.3"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))

    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    //Database Stufffff
    implementation("org.jetbrains.xodus:xodus-openAPI:$xodusVersion")
    implementation("org.jetbrains.xodus:xodus-environment:$xodusVersion")

    //Discord Rich Rest,WebSocket Client API
    implementation("net.dv8tion:JDA:$jdaVersion")

    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    testCompile("junit", "junit", "4.12")
}

val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Main-Class"] = "com.noth.customemojibot.CustomEmojiBotKt"
    }

    from(

        configurations.compile.get().map {

            if (it.isDirectory) it else zipTree(it)

        }

    )
}

val shadowJar : com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar by tasks

shadowJar.apply {
    manifest {
        attributes["Main-Class"] = "com.noth.customemojibot.CustomEmojiBotKt"
    }
}