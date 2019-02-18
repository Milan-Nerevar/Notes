package com.nerevar.notes.splash

import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

class SplashModel : SplashContract.Model {

    override suspend fun countDown() = delay(TimeUnit.SECONDS.toMillis(3))

}