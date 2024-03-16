package com.example.breakpasswordnew.presentation.adapters.holders

import androidx.recyclerview.widget.RecyclerView
import com.example.breakpasswordnew.model.Password
import com.example.breakpasswordnew.presentation.views.PasswordView

internal class PasswordHolder(
    private val view: PasswordView
) : RecyclerView.ViewHolder(view) {

    fun bind(model: Password) {
        view.setModel(model)
    }
}