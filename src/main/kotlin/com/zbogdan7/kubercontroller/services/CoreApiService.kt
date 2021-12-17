package com.zbogdan7.kubercontroller.services

import com.zbogdan7.kubercontroller.handlers.CoreApiHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CoreApiService {

    @Autowired
    private lateinit var handler: CoreApiHandler

    fun listNamespaces(): List<String> = handler.listNamespaces()
}