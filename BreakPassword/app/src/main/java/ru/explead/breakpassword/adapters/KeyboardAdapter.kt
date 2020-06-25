package ru.explead.breakpassword.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.explead.breakpassword.databinding.KeyboardItemBinding

class KeyboardAdapter(
        private val context: Context,
        private val listener: OnKeyboardClickListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnKeyboardClickListener {
        fun onKeyboardItemClick(value: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = KeyboardItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return KeyboardItemHolder(binding, listener)
    }

    override fun getItemCount(): Int {
        return KEYBOARD_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val value = if(position == KEYBOARD_ITEM - 1) 0 else position
        (holder as KeyboardItemHolder).onBind(value)
    }

    companion object {
        const val KEYBOARD_ITEM = 10
    }
}