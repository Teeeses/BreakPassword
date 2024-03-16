package com.example.breakpasswordnew.presentation.adapters

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.breakpasswordnew.model.Password
import com.example.breakpasswordnew.presentation.adapters.holders.PasswordHolder
import com.example.breakpasswordnew.presentation.views.PasswordView

internal class PasswordAdapter(
    private val context: Context
) : RecyclerView.Adapter<PasswordHolder>() {

    private var data: MutableList<Password> = mutableListOf()

    private var cellsNumber: Int = 0

    fun setItems(data: List<Password>, cellsNumber: Int) {
        this.data = data.toMutableList()
        this.cellsNumber = cellsNumber
        notifyDataSetChanged()
    }

    fun clearItems() {
        data.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordHolder {
        val view = PasswordView(context = context, cellsNumber = cellsNumber)
        return PasswordHolder(view)
    }

    override fun onBindViewHolder(holder: PasswordHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}