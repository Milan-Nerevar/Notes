package com.nerevar.notes.splash

import androidx.lifecycle.LiveData
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

object SplashContract {

    interface View {

        /**
         * Navigate to ListActivity.
         */
        fun navigateToList()
    }

    interface Model {

        /**
         * Count down splash screen delay.
         */
        suspend fun countDown()
    }

    interface VM {

        val onCountDown: LiveData<Unit>

        /**
         * Start splash count down delay.
         */
        fun startCountDown()

    }

    val module = module {
        viewModel { SplashViewModel(get()) }
        factory<Model> { SplashModel() }
    }

}