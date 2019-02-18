package com.nerevar.notes.detail

import com.nerevar.domain.Note
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface DetailEndpoint {

    @GET("notes/{id}")
    fun getNoteAsync(@Path("id") id: Long): Deferred<Note>

    @POST("notes")
    fun createNoteAsync(@Body body: UpdateBody): Deferred<Note>

    @PUT("notes/{id}")
    fun updateNoteAsync(@Path("id") id: Long, @Body body: UpdateBody): Deferred<Note>

    @DELETE("notes/{id}")
    fun deleteNoteAsync(@Path("id") id: Long): Deferred<Response<Unit>>

    data class UpdateBody(val title: String)
}