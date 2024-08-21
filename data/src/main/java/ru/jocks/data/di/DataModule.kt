package ru.jocks.data.di

import org.koin.dsl.module
import ru.jocks.data.AddressApi
import ru.jocks.data.address.repository.AddressRepositoryImpl
import ru.jocks.data.api.RetrofitClient
import ru.jocks.data.business.datasources.BusinessLocalDataSource
import ru.jocks.data.business.repository.BusinessRepositoryImpl
import ru.jocks.domain.address.repository.AddressRepository
import ru.jocks.domain.business.repository.BusinessRepository

val dataModule = module {
    single<AddressRepository> {
        AddressRepositoryImpl()
    }
    single<BusinessRepository> {
        BusinessRepositoryImpl()
    }
    single<BusinessLocalDataSource> {
        BusinessLocalDataSource(get())
    }
}