package com.example.breakpasswordnew

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("onClick")
fun View.clickView(action: (() -> Unit)?) {
    setOnClickListener {
        action?.invoke()
    }
}