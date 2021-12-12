package com.zbogdan7.kubercontroller.services

import com.zbogdan7.kubercontroller.controllers.post.Deployment
import com.zbogdan7.kubercontroller.handlers.DeploymentHandler
import io.kubernetes.client.openapi.models.V1Deployment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class DeploymentService {

    @Autowired
    private lateinit var handler: DeploymentHandler

    fun create(deploy: Deployment): Boolean {
        return handler.createDeployment(deploy)
    }

    fun delete(deploy: Deployment): Boolean {
        return handler.deleteDeployment(deploy)
    }

    fun allDeployments(namespace: String): List<V1Deployment>? = handler.allDeployments(namespace)


}