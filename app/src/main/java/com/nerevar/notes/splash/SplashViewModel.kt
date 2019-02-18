package com.nerevar.notes.splash

import androidx.lifecycle.LiveData
import com.hadilq.liveevent.LiveEvent
import com.nerevar.notes.core.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashViewModel(model: SplashContract.Model) : BaseViewModel<SplashContract.Model>(model), SplashContract.VM {

    private val _onCountDown = LiveEvent<Unit>()

    override val onCountDown: LiveData<Unit>
        get() = _onCountDown

    override fun startCountDown() {
        launch {
            withContext(Dispatchers.IO) { model.countDown() }

            _onCountDown.value = Unit
        }
    }

}