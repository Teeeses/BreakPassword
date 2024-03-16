package com.example.breakpasswordnew.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.breakpasswordnew.R
import com.example.breakpasswordnew.databinding.KeyboardViewBinding
import com.example.breakpasswordnew.domain.KeyboardActon
import com.example.breakpasswordnew.model.KeyboardModel

class KeyboardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private val viewHolder: KeyboardViewBinding =
        KeyboardViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setActionHandler(handler: KeyboardActon) {
        viewHolder.actionHandler = handler
    }

    fun setWinState(state: Boolean) {
        with(viewHolder) {
            item0.isEnabled = !state
            item1.isEnabled = !state
            item2.isEnabled = !state
            item3.isEnabled = !state
            item4.isEnabled = !state
            item5.isEnabled = !state
            item6.isEnabled = !state
            item7.isEnabled = !state
            item8.isEnabled = !state
            item9.isEnabled = !state
            delete.isEnabled = !state

            apply.text = context.resources.getString(if (state) R.string.again else R.string.hack)
        }
    }
}