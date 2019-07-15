package ru.explead.breakpassword.beans

import android.widget.RelativeLayout
import android.widget.TextView

import ru.explead.breakpassword.MainActivity
import ru.explead.breakpassword.R

class Cell(val id: Int,
           /**
            * Layout под клетку
            */
           val layout: RelativeLayout,
           /**
            * TextView под клетку
            */
           val tvCell: TextView) {
    /**
     * Значение в клетке
     */
    private var value: Int = 0


    init {
        this.value = NO_ACTIVE
    }

    fun getValue(): Int {
        return value
    }

    fun setValue(value: Int) {
        this.value = value
        setText()
    }

    /**
     * Устанавливаем текст внутри клетки
     */
    private fun setText() {
        tvCell.text = if (value != NO_ACTIVE) Integer.toString(value) else ""
    }

    fun setFocus() {
        tvCell.setBackgroundDrawable(MainActivity.getRes().getDrawable(R.drawable.focus_circle_bg))
    }

    fun removeFocus() {
        tvCell.setBackgroundDrawable(MainActivity.getRes().getDrawable(R.drawable.circle_bg))
    }

    companion object {
        /**
         * Цифра не введена
         */
        var NO_ACTIVE = -1
    }
}
