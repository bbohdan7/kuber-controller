package com.zbogdan7.kubercontroller.handlers

import io.kubernetes.client.openapi.ApiClient
import io.kubernetes.client.openapi.Configuration
import io.kubernetes.client.openapi.apis.AppsV1Api
import io.kubernetes.client.openapi.apis.CoreV1Api
import io.kubernetes.client.util.Config

object ResourceBuilder {

    private val apiClient: ApiClient = Config.defaultClient()
    private val coreV1: CoreV1Api
    private val appsV1: AppsV1Api

    init {
        Configuration.setDefaultApiClient(apiClient)
        coreV1 = CoreV1Api(apiClient)
        appsV1 = AppsV1Api(apiClient)
    }

    fun coreV1(): CoreV1Api = this.coreV1

    fun appsV1(): AppsV1Api = this.appsV1
}