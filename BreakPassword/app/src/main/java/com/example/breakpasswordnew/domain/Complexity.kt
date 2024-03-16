package com.example.breakpasswordnew.domain

import androidx.annotation.DimenRes
import com.example.breakpasswordnew.R

enum class Complexity(
    val numberCells: Int,
    @DimenRes val textSize: Int
) {
    EASY(3, R.dimen.more_standard_text, ),
    MEDIUM(4, R.dimen.standard_text),
    HARD(5, R.dimen.more_mini_text),
    VERY_HARD(6, R.dimen.mini_text);
}