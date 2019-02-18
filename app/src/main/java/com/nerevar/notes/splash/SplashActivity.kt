package com.nerevar.notes.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.nerevar.notes.R
import com.nerevar.notes.notes.NotesActivity.Companion.startListActivity
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity(), SplashContract.View {

    private val vm: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        vm.onCountDown.observe(this, Observer { navigateToList() })
    }

    override fun onResume() {
        super.onResume()
        vm.startCountDown()
    }

    override fun navigateToList() {
        startListActivity()
        finish()
    }
}