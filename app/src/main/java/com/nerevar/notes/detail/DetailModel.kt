package com.nerevar.notes.detail

import com.nerevar.domain.Note

class DetailModel(private val endpoint: DetailEndpoint): DetailContract.Model {

    override suspend fun deleteNote(id: Long) {
        endpoint.deleteNoteAsync(id).await()
    }

    override suspend fun createNote(content: String): Note {
        return endpoint.createNoteAsync(DetailEndpoint.UpdateBody(content)).await()
    }

    override suspend fun updateNote(note: Note): Note {
        return endpoint.updateNoteAsync(note.id, DetailEndpoint.UpdateBody(note.title)).await()
    }

    override suspend fun getNote(id: Long) : Note {
        return endpoint.getNoteAsync(id).await()
    }

}