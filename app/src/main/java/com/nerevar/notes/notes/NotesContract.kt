package com.nerevar.notes.notes

import androidx.lifecycle.LiveData
import com.nerevar.domain.Note
import com.nerevar.notes.Config
import com.nerevar.notes.core.api.RestClient
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

object NotesContract {

    interface View {

        fun navigateToCreateNote()

        fun navigateToNoteDetail(id: Long)

        data class NoteItem(val title: String)
    }

    interface Model {

        suspend fun getNotes(): List<Note>

    }

    interface VM {

        val loading: LiveData<Boolean>

        val navigateToNoteDetail: LiveData<Long>

        val navigateToCreateDetail: LiveData<Unit>

        val notes: LiveData<List<View.NoteItem>>

        fun loadNotes()

        fun createNote()

        fun showNote(index: Int)

    }

    val module = module {

        factory<NotesEndpoint> { RestClient.createService(get(), Config.BASE_URL) }
        factory { NotesModel(get()) }
        viewModel { NotesViewModel(get()) }
    }

}