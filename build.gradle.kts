import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat


plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation("com.github.tkuenneth:nativeparameterstoreaccess:0.1.0")
                implementation ("org.jetbrains.compose.material:material-icons-extended-desktop:1.0.0-rc11")
                implementation("com.squareup.retrofit2:retrofit:2.9.0")
                implementation ("com.squareup.retrofit2:converter-gson:2.9.0")



            }
        }
        val jvmTest by getting
    }
}

compose.desktop {

    application {

        mainClass = "MainKt"
        nativeDistributions {

            jvmArgs(
                "-Dapple.awt.application.appearance=system"
            )
            modules("java.instrument", "java.prefs", "java.sql", "jdk.unsupported","jdk.crypto.ec","jdk.localedata")
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "SchulAppDesktop"
            packageVersion = "1.0.0"

        }
    }
}
