package com.zbogdan7.kubercontroller.services

import com.zbogdan7.kubercontroller.handlers.EventReader
import com.zbogdan7.kubercontroller.models.Event
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EventService {

    @Autowired
    private lateinit var reader: EventReader

    fun all(): List<Event> = reader.listEvents()


}