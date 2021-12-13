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

    object Default {
        const val defaultNamespace: String = "javatest"
    }

    private val apiClient: ApiClient = Config.defaultClient()
    private val coreV1: CoreV1Api

    init {
        Configuration.setDefaultApiClient(apiClient)
        this.coreV1 = CoreV1Api(apiClient)
    }

    fun listEvents(): List<Event> {
        val events = coreV1.listNamespacedEvent(
            DeploymentController.namespace, null, null, null, null, null, null, null, null, null
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