package com.example.breakpasswordnew.model

import com.example.breakpasswordnew.model.Cell.Companion.NONE_SYMBOL

/**
 * Модель попытки пароля
 */
data class Password(
    val cells: List<Cell>,
    var activeType: PasswordActiveType = PasswordActiveType.ACTIVE
) {

    private var currentCell: Cell = cells.first()

    private var currentViewPort = 0

    fun setSymbol(value: Int) {
        if (activeType == PasswordActiveType.ACTIVE) {
            currentCell.symbol = value
            currentCell.isFocused = false
            currentViewPort = (currentViewPort + 1).coerceAtMost(cells.size - 1)
            currentCell = cells[currentViewPort]
            currentCell.isFocused = true
        }
    }

    fun deleteSymbol() {
        if (activeType == PasswordActiveType.ACTIVE) {
            // если еще не введен символ
            if (cells[currentViewPort].symbol == NONE_SYMBOL) {
                currentCell.isFocused = false
                currentViewPort = (currentViewPort - 1).coerceAtLeast(0)
                currentCell = cells[currentViewPort]
                currentCell.symbol = NONE_SYMBOL
                currentCell.isFocused = true
            } else {
                currentCell.symbol = NONE_SYMBOL
            }
        }
    }

    /**
     * Проверка что все ячейки заполнены
     */
    fun isAllFill(): Boolean {
        return cells.none { it.symbol == NONE_SYMBOL }
    }

    fun finishChangePassword() {
        activeType = PasswordActiveType.NO_ACTIVE
    }
}