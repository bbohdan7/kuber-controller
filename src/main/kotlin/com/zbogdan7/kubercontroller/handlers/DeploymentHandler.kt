package com.zbogdan7.kubercontroller.handlers

import io.kubernetes.client.openapi.ApiClient
import io.kubernetes.client.openapi.ApiException
import io.kubernetes.client.openapi.Configuration
import io.kubernetes.client.openapi.apis.AppsV1Api
import io.kubernetes.client.openapi.apis.CoreV1Api
import io.kubernetes.client.openapi.models.*
import io.kubernetes.client.util.Config
import org.springframework.stereotype.Component

@Component
class DeploymentHandler {

    private val apiClient: ApiClient = Config.defaultClient()
    private val coreV1: CoreV1Api
    private val appsV1: AppsV1Api

    init {
        Configuration.setDefaultApiClient(apiClient)
        coreV1 = CoreV1Api(apiClient)
        appsV1 = AppsV1Api(apiClient)
    }

    fun createDeployment(
        name: String, image: String, namespace: String, labels: Map<String, String>, port: Int? = null
    ): Boolean {
        if (!findDeploymentByName(name, namespace)) {
            appsV1.createNamespacedDeployment(
                namespace,
                V1DeploymentBuilder().withApiVersion("apps/v1").withKind("Deployment").withNewMetadata().withName(name)
                    .withLabels(mapOf("app" to name)).endMetadata().withNewSpec().withReplicas(2).withNewSelector()
                    .withMatchLabels(mapOf("app" to name)).endSelector().withNewTemplate().withNewMetadata()
                    .withLabels(mapOf("app" to name)).endMetadata().withNewSpec().withContainers(V1Container().apply {
                        this.name = name
                        this.image = image

                        if (port != null) {
                            this.addPortsItem(V1ContainerPortBuilder().withContainerPort(port).build())
                        }
                    }).endSpec().endTemplate().endSpec().build(),
                null,
                null,
                null
            )
            return true

        }

        return false
    }

    fun deleteDeployment(name: String, namespace: String): Boolean = if (findDeploymentByName(name, namespace)) {
        appsV1.deleteNamespacedDeployment(name, namespace, null, null, null, null, null, null)
        true
    } else false

    fun allDeployments(namespace: String): List<V1Deployment>? = appsV1.listNamespacedDeployment(
        namespace, null, null, null, null, null, null, null, null, null
    ).items

    private fun findDeploymentByName(name: String, namespace: String): Boolean {
        return try {
            appsV1.readNamespacedDeployment(name, namespace, null, true, null)
            true
        } catch (ex: ApiException) {
            false
        }
    }

}