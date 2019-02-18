package com.nerevar.notes.notes

import com.nerevar.domain.Note
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface NotesEndpoint {

    @GET("notes")
    fun getNotesAsync(): Deferred<List<Note>>

}