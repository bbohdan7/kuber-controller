package com.zbogdan7.kubercontroller.controllers

import com.zbogdan7.kubercontroller.handlers.ResourceBuilder
import com.zbogdan7.kubercontroller.services.CoreApiService
import com.zbogdan7.kubercontroller.services.EventService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/events")
class EventController {

    @Autowired
    private lateinit var svc: EventService

    @Autowired
    private lateinit var coreSvc: CoreApiService


    @GetMapping
    fun events(@RequestParam(defaultValue = "default") namespace: String, model: Model): String {
        println("events for namespace $namespace")

        model.addAttribute("all", svc.all(namespace))
        model.addAttribute("namespace", namespace)
        model.addAttribute("namespaces", coreSvc.listNamespaces())

        return "events"
    }
}