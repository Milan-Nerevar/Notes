package com.nerevar.notes.notes

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.nerevar.notes.R
import com.nerevar.notes.core.util.startActivity
import kotlinx.android.synthetic.main.activity_notes.*

class NotesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        setSupportActionBar(toolbar)
    }

    companion object {
        fun Context.startListActivity() = startActivity<NotesActivity>()
    }
}