package com.nerevar.notes.notes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nerevar.domain.Note
import com.nhaarman.mockito_kotlin.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


class NotesViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun test_loadNotes() = runBlocking {
        val viewModel = NotesViewModel(
            model = mock {
                on { runBlocking { getNotes() } } doReturn notes()
            }
        )

        val observer = mock<Observer<List<NotesContract.View.NoteItem>>>()

        viewModel.loadNotes()
        viewModel.notes.observeForever(observer)

        verify(observer).onChanged(argThat { size == 2 })
    }

    @Test
    fun test_navigateToNoteDetail() = runBlocking {
        val viewModel = NotesViewModel(
            model = mock {
                on { runBlocking { getNotes() } } doReturn notes()
            }
        )

        val observer = mock<Observer<Long>>()

        viewModel.loadNotes()

        delay(1000)

        viewModel.showNote(0)

        viewModel.navigateToNoteDetail.observeForever(observer)

        verify(observer).onChanged(eq(0L))
    }

    private fun notes(): List<Note> = listOf(
        Note(0, "first"),
        Note(1, "second")
    )

}