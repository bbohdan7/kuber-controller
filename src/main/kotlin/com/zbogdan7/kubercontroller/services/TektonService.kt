package com.zbogdan7.kubercontroller.services

import com.zbogdan7.kubercontroller.handlers.TektonHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TektonService {

    @Autowired
    private lateinit var handler: TektonHandler

    fun all(): List<String> = handler.listTasks()
    fun taskRuns(): List<String> = handler.listTaskRuns()
    fun startTask(): Unit = handler.runTask()
    fun deleteTaskRun(name: String): Unit = handler.deleteTaskRun(name)
}