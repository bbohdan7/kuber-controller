package com.zbogdan7.kubercontroller.handlers

import com.zbogdan7.kubercontroller.controllers.DeploymentController
import com.zbogdan7.kubercontroller.models.Event
import io.kubernetes.client.openapi.ApiClient
import io.kubernetes.client.openapi.Configuration
import io.kubernetes.client.openapi.apis.CoreV1Api
import io.kubernetes.client.util.Config
import org.springframework.stereotype.Component
import java.text.SimpleDateFormat

@Component
class EventReader {

    private val coreV1 = ResourceBuilder.coreV1()

    fun listEvents(namespace: String): List<Event> {
        val events = coreV1.listNamespacedEvent(
            namespace, null, null, null, null, null, null, null, null, null
        )

        val result = emptyList<Event>().toMutableList()

        events.items.forEach {
            result.add(
                Event(
                    timestamp = SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(it.lastTimestamp?.toDate()),
                    message = it.message
                )
            )
        }

        return result
    }

}