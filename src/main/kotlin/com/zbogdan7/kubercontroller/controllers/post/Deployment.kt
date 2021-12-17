package com.zbogdan7.kubercontroller.controllers.post

data class Deployment(
    var name: String? = null,
    var namespace: String? = null,
    var image: String? = null,
    var replicas: Int? = 1,
    var labels: Map<String, String?>? = mapOf("app" to name),
    var port: Int? = null
)