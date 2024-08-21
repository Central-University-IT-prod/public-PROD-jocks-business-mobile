package ru.jocks.domain.address.repository

interface AddressRepository {
    suspend fun getSuggestion(query: String) : List<String>
}