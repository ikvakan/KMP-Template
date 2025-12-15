package hr.asee.multiplatform.licensing.sdk.di

import hr.asee.multiplatform.licensing.sdk.core.data.HttpClientFactory
import hr.asee.multiplatform.licensing.sdk.core.exception.DataExceptionMapper
import hr.asee.multiplatform.licensing.sdk.core.exception.ExceptionMappers
import org.koin.core.module.Module
import org.koin.dsl.module

private const val BASE_URL = "BASE_URL"
private const val API_KEY = "API_KEY"

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get(), baseUrl = null, apiKey = null) }
    single<ExceptionMappers.Data> { DataExceptionMapper() }
}