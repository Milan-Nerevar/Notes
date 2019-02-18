package com.nerevar.notes.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.hadilq.liveevent.LiveEvent
import com.nerevar.domain.Note
import com.nerevar.notes.core.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotesViewModel(model: NotesModel) : BaseViewModel<NotesModel>(model), NotesContract.VM {

    private val _loading = MutableLiveData<Boolean>().apply { value = false }
    private val _navigateToNoteDetail = LiveEvent<Long>()
    private val _navigateToCreateDetail = LiveEvent<Unit>()
    private val _notes = MutableLiveData<List<Note>>()

    override val loading: LiveData<Boolean>
        get() = _loading

    override val navigateToNoteDetail: LiveData<Long>
        get() = _navigateToNoteDetail

    override val navigateToCreateDetail: LiveData<Unit>
        get() = _navigateToCreateDetail

    override val notes: LiveData<List<NotesContract.View.NoteItem>>
        get() = Transformations.map(_notes) { list -> list.map { NotesContract.View.NoteItem(it.title) } }

    override fun showNote(index: Int) {
        val note = _notes.value?.get(index) ?: return

        _navigateToNoteDetail.value = note.id
    }

    override fun createNote() {
        _navigateToCreateDetail.value = Unit
    }

    override fun loadNotes() {
        launch {

            val firstTime = _notes.value?.isEmpty() ?: true

            if (firstTime) {
                _loading.value = true
            }

            val notes = withContext(Dispatchers.IO) { model.getNotes() }
            _notes.value = notes

            _loading.value = false
        }
    }

}