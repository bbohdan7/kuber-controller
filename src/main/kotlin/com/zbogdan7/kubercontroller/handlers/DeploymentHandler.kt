package com.zbogdan7.kubercontroller.handlers

import com.zbogdan7.kubercontroller.controllers.post.Deployment
import io.kubernetes.client.openapi.ApiException
import io.kubernetes.client.openapi.models.V1Container
import io.kubernetes.client.openapi.models.V1ContainerPortBuilder
import io.kubernetes.client.openapi.models.V1Deployment
import io.kubernetes.client.openapi.models.V1DeploymentBuilder
import org.springframework.stereotype.Component

@Component
class DeploymentHandler {

    private val appsV1 = ResourceBuilder.appsV1()

    fun createDeployment(deploy: Deployment): Boolean {
        if (!findDeploymentByName(deploy)) {
            appsV1.createNamespacedDeployment(
                deploy.namespace,
                V1DeploymentBuilder().withApiVersion("apps/v1").withKind("Deployment").withNewMetadata()
                    .withName(deploy.name).withLabels(deploy.labels).endMetadata().withNewSpec()
                    .withReplicas(deploy.replicas).withNewSelector().withMatchLabels(deploy.labels).endSelector()
                    .withNewTemplate().withNewMetadata().withLabels(deploy.labels).endMetadata().withNewSpec()
                    .withContainers(V1Container().apply {
                        this.name = deploy.name
                        this.image = deploy.image

                        if (deploy.port != null) {
                            this.addPortsItem(V1ContainerPortBuilder().withContainerPort(deploy.port).build())
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

    fun deleteDeployment(deploy: Deployment): Boolean = if (findDeploymentByName(deploy)) {
        appsV1.deleteNamespacedDeployment(deploy.name, deploy.namespace, null, null, null, null, null, null)
        true
    } else false

    fun allDeployments(namespace: String): List<V1Deployment>? = appsV1.listNamespacedDeployment(
        namespace, null, null, null, null, null, null, null, null, null
    ).items

    private fun findDeploymentByName(deploy: Deployment): Boolean {
        return try {
            appsV1.readNamespacedDeployment(deploy.name, deploy.namespace, null, true, null)
            true
        } catch (ex: ApiException) {
            false
        }
    }

}