package com.nerevar.notes

import android.app.Application
import com.nerevar.notes.core.di.Modules
import org.koin.android.ext.android.startKoin
import org.koin.android.logger.AndroidLogger

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(
            androidContext = this,

            logger = AndroidLogger(),

            modules = Modules.modules
        )

    }
}