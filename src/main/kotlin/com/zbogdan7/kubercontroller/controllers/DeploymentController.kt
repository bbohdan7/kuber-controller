package com.zbogdan7.kubercontroller.controllers

import com.zbogdan7.kubercontroller.controllers.post.Deployment
import com.zbogdan7.kubercontroller.services.DeploymentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class DeploymentController {

    @Autowired
    private lateinit var service: DeploymentService

    companion object Defaults {
        const val namespace: String = "javatest"
    }

    @GetMapping("/")
    fun index(model: Model): String {
        val data = "We've got ${service.allDeployments("bogdan")?.size} deployments in count."
        model.addAttribute("data", data)
        model.addAttribute("deploys", service.allDeployments(Defaults.namespace))
        model.addAttribute("deploy", Deployment())

        return "index"
    }

    @PostMapping
    fun deploy(@ModelAttribute deploy: Deployment, model: Model): String {
        model.addAttribute("deploy", deploy)
        model.addAttribute("message", "${deploy.name} has been deployed successfully!`")
        model.addAttribute("deploys", service.allDeployments(Defaults.namespace))

        println("Post invoked")

        service.create(deploy)

        return "redirect:/"
    }

    @GetMapping("/delete/{name}")
    fun deleteDeployment(@PathVariable("name") name: String): String {
        println("Deployment name to be deleted: $name")

        service.delete(Deployment(name = name, namespace = Defaults.namespace))

        return "redirect:/"
    }


}