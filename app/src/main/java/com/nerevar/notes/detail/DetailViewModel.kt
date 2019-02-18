package com.nerevar.notes.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.hadilq.liveevent.LiveEvent
import com.nerevar.domain.Note
import com.nerevar.notes.core.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(
    private val noteId: Long, model: DetailModel
) :
    BaseViewModel<DetailModel>(model),
    DetailContract.VM {

    private val _loading = MutableLiveData<Boolean>().apply { value = false }

    private val _content = MutableLiveData<Note>()

    private val _navigateUp = LiveEvent<Unit>()

    private val actualNoteId: Long
        get() = _content.value?.id ?: noteId

    override val loading: LiveData<Boolean>
        get() = _loading

    override val navigateUp: LiveData<Unit>
        get() = _navigateUp

    override val content: LiveData<String>
        get() = Transformations.map(_content) { it.title }

    override fun loadNote() {
        launch {
            val id = actualNoteId

            if (actualNoteId == -1L) {
                _content.value = Note(-1L, "")
                return@launch
            } else {
                _loading.value = true

                val note = withContext(Dispatchers.IO) { model.getNote(id) }
                _content.value = note

                _loading.value = false
            }
        }
    }

    override fun deleteNote() {
        launch {
            val id = actualNoteId

            if (actualNoteId == -1L) {
                _navigateUp.value = Unit
            } else {
                _loading.value = true

                withContext(Dispatchers.IO) { model.deleteNote(id) }
                _navigateUp.value = Unit

                _loading.value = false
            }
        }
    }

    override fun createOrUpdateNote(content: String) {
        launch {
            val id = actualNoteId

            if (actualNoteId == -1L) {
                val note = withContext(Dispatchers.IO) { model.createNote(content) }
                _content.value = note
            } else {
                withContext(Dispatchers.IO) { model.updateNote(Note(id, content)) }
            }
        }
    }

}