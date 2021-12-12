package com.zbogdan7.kubercontroller.controllers.post

data class Deployment(
    val name: String? = null,
    val namespace: String? = null,
    val image: String? = null,
    val replicas: Int? = 1,
    val labels: Map<String, String?>? = mapOf("app" to name),
    val port: Int? = null
)