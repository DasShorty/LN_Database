plugins {
    id("java")
    id("maven-publish")
}

group = "com.laudynetwork"
version = "latest"

publishing {
    repositories {
        maven {
            url = uri("https://repo.laudynetwork.com/repository/maven")
            credentials {
                username = "root"
                password = "2mo6LYMV4zMXsNsJ6xExZLabFdgMbV"
            }
        }
    }

    publications {
        create<MavenPublication>("maven") {
            groupId = "com.laudynetwork"
            artifactId = "database"
            version = "latest"

            from(components["java"])
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("mysql:mysql-connector-java:8.0.32")
    annotationProcessor("org.projectlombok:lombok:1.18.26")
    implementation("biz.paluch.redis:lettuce:4.5.0.Final")
    implementation("org.projectlombok:lombok:1.18.26")
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
}