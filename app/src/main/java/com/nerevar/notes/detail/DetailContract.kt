package com.nerevar.notes.detail

import androidx.lifecycle.LiveData
import com.nerevar.domain.Note
import com.nerevar.notes.Config
import com.nerevar.notes.core.api.RestClient
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

object DetailContract {

    interface View {

        fun navigateUp()

    }

    interface Model {
        suspend fun getNote(id: Long): Note

        suspend fun createNote(content: String): Note

        suspend fun updateNote(note: Note): Note

        suspend fun deleteNote(id: Long)
    }

    interface VM {
        val loading: LiveData<Boolean>

        val navigateUp: LiveData<Unit>

        val content: LiveData<String>

        fun loadNote()

        fun deleteNote()

        fun createOrUpdateNote(content: String)
    }

    val module = module {
        factory<DetailEndpoint> { RestClient.createService(get(), Config.BASE_URL) }
        factory { DetailModel(get()) }
        viewModel { (id: Long) -> DetailViewModel(id, get()) }
    }

}