package ru.jocks.data.address.repository

import org.koin.java.KoinJavaComponent
import ru.jocks.data.AddressApi
import ru.jocks.data.api.RetrofitClient
import ru.jocks.domain.address.repository.AddressRepository

class AddressRepositoryImpl : AddressRepository {
    private val api = RetrofitClient.suggestionsApiService

    override suspend fun getSuggestion(query: String): List<String> {
        return api.getAddress(1, "json|pretty", query).body()?.suggestions?.map { it.value } ?: emptyList()
    }

}