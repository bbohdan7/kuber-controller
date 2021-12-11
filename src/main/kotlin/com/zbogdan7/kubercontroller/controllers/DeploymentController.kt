package com.zbogdan7.kubercontroller.controllers

import com.zbogdan7.kubercontroller.services.DeploymentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class DeploymentController {

    @Autowired
    private lateinit var service: DeploymentService

    @GetMapping("/")
    fun index(model: Model): String {
        val data = "We've got ${service.allDeployments("bogdan")?.size} deployments in count."
        model.addAttribute("data", data)
        model.addAttribute("deploys", service.allDeployments("bogdan"))

        return "index"
    }
}