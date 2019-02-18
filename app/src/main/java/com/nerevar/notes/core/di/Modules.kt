package com.nerevar.notes.core.di

import com.nerevar.notes.detail.DetailContract
import com.nerevar.notes.detail.DetailModel
import com.nerevar.notes.notes.NotesContract
import com.nerevar.notes.splash.SplashContract

object Modules {

    val modules = listOf(

        appModule,

        SplashContract.module,
        NotesContract.module,
        DetailContract.module
    )

}