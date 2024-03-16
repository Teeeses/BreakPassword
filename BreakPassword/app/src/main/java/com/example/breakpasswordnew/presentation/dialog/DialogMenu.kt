package com.example.breakpasswordnew.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import com.example.breakpasswordnew.R
import com.example.breakpasswordnew.domain.Complexity

class DialogMenu(context: Context, currentLevel: Complexity, private val listener: MenuListener) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_menu)
        setCancelable(false)
        findViewById<View>(R.id.btnEasy).setOnClickListener {
            listener.chooseLevel(Complexity.EASY)
            dismiss()
        }
        findViewById<View>(R.id.btnMedium).setOnClickListener {
            listener.chooseLevel(Complexity.MEDIUM)
            dismiss()
        }
        findViewById<View>(R.id.btnHard).setOnClickListener {
            listener.chooseLevel(Complexity.HARD)
            dismiss()
        }
        findViewById<View>(R.id.btnVeryHard).setOnClickListener {
            listener.chooseLevel(Complexity.VERY_HARD)
            dismiss()
        }
        findViewById<Button>(R.id.btnCancel).setOnClickListener { dismiss() }
    }

    interface MenuListener {

        fun chooseLevel(level: Complexity)
    }
}