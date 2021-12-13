package com.zbogdan7.kubercontroller.services

import com.zbogdan7.kubercontroller.handlers.TektonHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class TektonService {

    @Autowired
    private lateinit var handler: TektonHandler

    @PostConstruct
    fun init(): Unit = handler.listTasks()
}