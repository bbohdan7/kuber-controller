package com.zbogdan7.kubercontroller.services

import com.zbogdan7.kubercontroller.handlers.DeploymentHandler
import io.kubernetes.client.openapi.models.V1Deployment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DeploymentService {

    @Autowired
    private lateinit var handler: DeploymentHandler

    fun create(
        name: String, image: String, namespace: String, labels: Map<String, String>, port: Int? = null
    ): Boolean {
        return handler.createDeployment(name, image, namespace, labels, port)
    }

    fun delete(name: String, namespace: String): Boolean {
        return handler.deleteDeployment(name, namespace)
    }

    fun allDeployments(namespace: String): List<V1Deployment>? = handler.allDeployments(namespace)
}