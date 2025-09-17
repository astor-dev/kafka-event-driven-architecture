package com.astordev.ugc.inspectedpost.model

data class AutoInspectionResult (
    val status: InspectionStatus,
    val tags: List<String>
)