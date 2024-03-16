package com.example.breakpasswordnew.model

class Cell(
    var symbol: Int = NONE_SYMBOL,
    var placeType: PlaceType = PlaceType.NONE,
    var isFocused: Boolean = false
) {

    val symbolText: String
        get() {
            return if (symbol == NONE_SYMBOL) {
                ""
            } else {
                symbol.toString()
            }
        }

    fun equalSymbol(symbol: Int): Boolean {
        return this.symbol == symbol
    }

    companion object {

        const val NONE_SYMBOL = -1
    }
}
