package ru.jocks.domain.business.model

data class BusinessCreateModel(
    val name: String,
    val description: String,
    val addresses: List<String>,
    val userContactEmail: String,
    val email: String,
    val password: String,
)
