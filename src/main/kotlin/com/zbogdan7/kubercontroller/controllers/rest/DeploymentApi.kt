package com.zbogdan7.kubercontroller.controllers.rest

import com.zbogdan7.kubercontroller.controllers.post.Deployment
import com.zbogdan7.kubercontroller.services.DeploymentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.function.EntityResponse

@RestController
@RequestMapping("/api/deployments")
class DeploymentApi {

    @Autowired
    private lateinit var deploySvc: DeploymentService

    @GetMapping
    fun all(@RequestParam(defaultValue = "default") namespace: String): List<Deployment> {
        val result: MutableList<Deployment> = emptyList<Deployment>().toMutableList()

        deploySvc.allDeployments(namespace)?.stream()?.forEach {
            result += Deployment(
                name = it.metadata?.name,
                namespace = namespace,
                image = it.spec?.template?.spec?.containers?.get(0)?.image,
                replicas = it.spec?.replicas,
                labels = it.metadata?.labels,
                port = it.spec?.template?.spec?.containers?.get(0)?.ports?.get(0)?.containerPort
            )
        }
        return result
    }

    @PostMapping
    fun create(@RequestBody deploy: Deployment): ResponseEntity<String> =
        if (deploySvc.create(deploy)) ResponseEntity.ok("Created successfully!")
        else ResponseEntity.ok("Unable to create such object.")

    @DeleteMapping("{name}")
    fun delete(
        @RequestParam(defaultValue = "default") namespace: String, @PathVariable(name = "name") name: String
    ): ResponseEntity<String> = if (deploySvc.delete(Deployment(name = name, namespace = namespace))) {
        ResponseEntity.ok("Successfully deleted!")
    } else ResponseEntity.ok("Unable to delete this object.")

}