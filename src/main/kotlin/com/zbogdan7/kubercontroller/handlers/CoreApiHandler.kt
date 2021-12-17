package com.zbogdan7.kubercontroller.handlers

import org.springframework.stereotype.Component
import java.util.stream.Collectors
import javax.annotation.PostConstruct

@Component
class CoreApiHandler {

    private var coreV1 = ResourceBuilder.coreV1()

    fun listNamespaces(): List<String> =
        coreV1.listNamespace(null, null, null, null, null, null, null, null, null).items.stream()
            .map { it.metadata?.name }.collect(Collectors.toList()) as List<String>
}