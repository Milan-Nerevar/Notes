package com.nerevar.notes.core.di

import com.nerevar.notes.core.api.RestClient
import org.koin.dsl.module.module

val appModule = module {
    single { RestClient.defaultBuilder.build() }
}