package com.nerevar.notes.notes

import com.nerevar.domain.Note

class NotesModel(private val endpoint: NotesEndpoint) : NotesContract.Model {

    override suspend fun getNotes(): List<Note> {
        return endpoint.getNotesAsync().await()
    }

}