package com.zbogdan7.kubercontroller.controllers

import com.zbogdan7.kubercontroller.controllers.post.Deployment
import com.zbogdan7.kubercontroller.services.DeploymentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class DeploymentController {

    @Autowired
    private lateinit var service: DeploymentService

    @GetMapping("/")
    fun index(model: Model): String {
        val data = "We've got ${service.allDeployments("bogdan")?.size} deployments in count."
        model.addAttribute("data", data)
        model.addAttribute("deploys", service.allDeployments("javatest"))
        model.addAttribute("deploy", Deployment())

        return "index"
    }

    @PostMapping
    fun deploy(@ModelAttribute deploy: Deployment, model: Model): String {
        model.addAttribute("deploy", deploy)
        model.addAttribute("message", "${deploy.name} has been deployed successfully!`")
        model.addAttribute("deploys", service.allDeployments("javatest"))

        return "index".apply { service.create(deploy) }
    }


}