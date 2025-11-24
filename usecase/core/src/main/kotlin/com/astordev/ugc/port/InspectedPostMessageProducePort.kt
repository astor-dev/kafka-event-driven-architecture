package com.astordev.ugc.port

import com.astordev.ugc.inspectedpost.model.InspectedPost

interface InspectedPostMessageProducePort {
    fun sendCreateMessage(inspectedPost: InspectedPost)
    fun sendUpdateMessage(inspectedPost: InspectedPost)
    fun sendDeleteMessage(inspectedPost: InspectedPost)
}
