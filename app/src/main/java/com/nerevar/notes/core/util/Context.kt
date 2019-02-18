package com.nerevar.notes.core.util

import android.content.Context
import android.content.Intent
import android.os.Bundle

inline fun <reified T> Context.startActivity(bundle: Bundle? = null, block: Intent.() -> Unit = {}) {

    val intent = Intent(this, T::class.java)

    if (bundle != null) {
        intent.putExtras(bundle)
    }

    block(intent)

    startActivity(intent)

}