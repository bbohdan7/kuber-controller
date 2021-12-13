package com.zbogdan7.kubercontroller.controllers

import com.zbogdan7.kubercontroller.services.TektonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/tasks")
class TektonController {

    @Autowired
    private lateinit var svc: TektonService

    @GetMapping
    fun tasks(model: Model): String {
        model.addAttribute("allTasks", svc.all())
        model.addAttribute("allTaskRuns", svc.taskRuns())
        return "tasks"
    }


    @GetMapping("start-new-task/{name}")
    fun startTask(@PathVariable name: String, model: Model): String {
        model.addAttribute("allTasks", svc.all())
        model.addAttribute("allTaskRuns", svc.taskRuns())

        svc.startTask()

        return "redirect:/tasks"
    }

    @GetMapping("delete-task-run/{name}")
    fun deleteTaskRun(@PathVariable name: String): String {
        svc.deleteTaskRun(name)

        return "redirect:/tasks"
    }

}