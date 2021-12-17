package com.zbogdan7.kubercontroller.controllers

import com.zbogdan7.kubercontroller.controllers.post.Deployment
import com.zbogdan7.kubercontroller.services.CoreApiService
import com.zbogdan7.kubercontroller.services.DeploymentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class DeploymentController {

    @Autowired
    private lateinit var service: DeploymentService

    @Autowired
    private lateinit var coreService: CoreApiService

    companion object Defaults {
        var namespace: String = "default"
    }

    @GetMapping("/")
    fun index(@RequestParam(defaultValue = "default") namespace: String, model: Model): String {
        val data = "We've got ${service.allDeployments(namespace)?.size} deployments in count."
        println("Query param is $namespace")
        model.addAttribute("data", data)
        model.addAttribute("deploys", service.allDeployments(namespace))
        model.addAttribute("deploy", Deployment())
        model.addAttribute("namespaces", coreService.listNamespaces())
        model.addAttribute("namespace", namespace)

        return "index"
    }

    @PostMapping
    fun deploy(@ModelAttribute deploy: Deployment, model: Model): String {
        model.addAttribute("deploy", deploy)
        model.addAttribute("message", "${deploy.name} has been deployed successfully!`")
        model.addAttribute("deploys", service.allDeployments(Defaults.namespace))

        println("Post invoked")

        service.create(deploy.also { it.namespace = Defaults.namespace })

        return "redirect:/"
    }

    @GetMapping("/delete/{name}")
    fun deleteDeployment(@PathVariable("name") name: String): String {
        println("Deployment name to be deleted: $name")

        service.delete(Deployment(name = name, namespace = Defaults.namespace))

        return "redirect:/"
    }

}