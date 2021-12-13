package com.zbogdan7.kubercontroller.controllers

import com.zbogdan7.kubercontroller.services.EventService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/events")
class EventController {

    @Autowired
    private lateinit var svc: EventService

    @GetMapping
    fun events(model: Model): String {
        model.addAttribute("all", svc.all())

        return "events"
    }
}