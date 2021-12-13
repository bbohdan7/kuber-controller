package com.zbogdan7.kubercontroller.handlers

import io.fabric8.tekton.client.DefaultTektonClient
import org.springframework.stereotype.Component

@Component
class TektonHandler {

    private val tkn = DefaultTektonClient()

    fun listTasks(): Unit = tkn.v1beta1().tasks().list().items.map { it.metadata.name }.forEach(System.out::println)

}