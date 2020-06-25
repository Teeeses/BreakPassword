package ru.explead.breakpassword.adapters

import androidx.recyclerview.widget.RecyclerView
import ru.explead.breakpassword.databinding.KeyboardItemBinding

class KeyboardItemHolder(
        private val viewHolder: KeyboardItemBinding,
        private val listener: KeyboardAdapter.OnKeyboardClickListener
): RecyclerView.ViewHolder(viewHolder.getRoot()) {

    var value: Int = 0

    fun onBind(value: Int) {
        this.value = value
        viewHolder.item.text = value.toString()
        viewHolder.item.setOnClickListener {
            listener.onKeyboardItemClick(value)
        }
    }
}