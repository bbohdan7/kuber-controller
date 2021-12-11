package com.zbogdan7.kubercontroller.services

import com.zbogdan7.kubercontroller.handlers.DeploymentHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class DeploymentService {

    @Autowired
    private lateinit var handler: DeploymentHandler

    @PostConstruct
    fun init() {
        if (!handler.createDeployment("test-java", "nginx:latest", "bogdan", mapOf("app" to "test-java"), port = 80)) {
            handler.deleteDeployment("test-java", "bogdan")
        }

    }
}