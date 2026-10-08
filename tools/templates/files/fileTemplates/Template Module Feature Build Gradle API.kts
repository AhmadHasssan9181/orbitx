plugins {
    id("tech.orbitweb.orbitx-library")
}

android {
    namespace = "tech.orbitweb.orbitx.features.${MODULE_NAME}.api"
}

dependencies {
    implementation(projects.libraries.architecture)
}
