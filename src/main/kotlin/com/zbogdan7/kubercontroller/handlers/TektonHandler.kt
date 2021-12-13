package com.zbogdan7.kubercontroller.handlers

import io.fabric8.tekton.client.DefaultTektonClient
import io.fabric8.tekton.pipeline.v1beta1.TaskRunBuilder
import org.springframework.stereotype.Component
import java.util.stream.Collectors

@Component
class TektonHandler {

    object Defaults {
        const val namespace: String = "bogdan"
    }

    private val tkn = DefaultTektonClient()

    //Notice that I'm using Java's stream() and had to collect a new List with Collectors static factory.
    fun listTasks(): List<String> = tkn.v1beta1().tasks().list().items.stream().map { it.metadata.name }.collect(
        Collectors.toList()
    )

    //Notice that here I'm using Kotlin's map which automatically returns a List
    fun listTaskRuns(): List<String> = tkn.v1beta1().taskRuns().list().items.map { it.metadata.name }

    fun runTask(): Unit {
        tkn.v1beta1().taskRuns().inNamespace(Defaults.namespace).create(
            TaskRunBuilder().withNewMetadata().withGenerateName("deploy-backend-task-run-").endMetadata().withNewSpec()
                .withNewTaskRef().withName("deploy-backend-task").endTaskRef().withNewResources().withInputs()
                .addNewInput().withName("payment-processor-backend").withNewResourceRef()
                .withName("payment-processor-git").endResourceRef().endInput().withOutputs().addNewOutput()
                .withName("wasapp-piped").withNewResourceRef().withName("wasapp-image").endResourceRef().endOutput()
                .endResources().endSpec().build()
        )
    }

}