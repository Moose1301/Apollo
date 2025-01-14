plugins {
    id("apollo.shadow-conventions")
}

dependencies {
    compileOnly(libs.velocity)

    api(project(path = ":apollo-api", configuration = "shadow"))
    api(project(":apollo-common"))
    api(project(":extra:apollo-extra-adventure4"))

    annotationProcessor(libs.velocity)
}
