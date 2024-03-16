package com.example.breakpasswordnew.domain

import android.util.Log
import com.example.breakpasswordnew.App.Companion.LOG_TAG
import com.example.breakpasswordnew.model.*
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.*

class GameControllerImpl(
    override var level: Complexity = Complexity.MEDIUM,
    private val changePasswords: BehaviorSubject<Event> = BehaviorSubject.create()
): GameController {

    private var password: List<Int>

    override var allPasswords = mutableListOf(createEmptyPassword())

    private val currentPassword: Password
        get() = allPasswords.last()

    private val currentAttempt: Int
        get() = allPasswords.size

    private var isWin = false

    init {
        password = generatePassword()
    }

    override fun applyPassword() {
        if (isWin) {
            restart()
            return
        }

        if (currentPassword.isAllFill()) {
            var isAllTrue = true
            currentPassword.cells.forEach { it.isFocused = false }
            currentPassword.cells.forEachIndexed { index, cell ->
                when {
                    cell.equalSymbol(password[index]) -> {
                        cell.placeType = PlaceType.ON_PLACE
                    }
                    password.any { cell.equalSymbol(it) } -> {
                        cell.placeType = PlaceType.IT_HAS
                        isAllTrue = false
                    }
                    else -> {
                        cell.placeType = PlaceType.WRONG
                        isAllTrue = false
                    }
                }
            }
            currentPassword.finishChangePassword()
            if (isAllTrue) {
                isWin = true
                win()
            } else {
                createNewAttemptPassword()
            }
        } else {
            // не все ячейки заполнены
        }

    }

    override fun setSymbol(value: Int) {
        currentPassword.setSymbol(value)
        changePasswords.onNext(Update(allPasswords))
    }

    override fun deleteSymbol() {
        currentPassword.deleteSymbol()
        changePasswords.onNext(Update(allPasswords))
    }

    private fun win() {
        currentPassword.activeType = PasswordActiveType.NO_ACTIVE
        changePasswords.onNext(Win(currentAttempt))
    }


    override fun restart() {
        isWin = false
        password = generatePassword()
        allPasswords = mutableListOf(createEmptyPassword())
        changePasswords.onNext(CreateNewGame(level.numberCells, allPasswords))
    }

    private fun createNewAttemptPassword() {
        allPasswords.add(createEmptyPassword())
        changePasswords.onNext(Update(allPasswords))
        changePasswords.onNext(Attempt(currentAttempt))
    }

    override fun chooseLevel(level: Complexity) {
        if (this.level != level) {
            this.level = level
            restart()
        }
    }

    /**
     * Генерируем пароль
     */
    private fun generatePassword(): List<Int> {
        val password: MutableList<Int> = mutableListOf()
        for (i in 0 until level.numberCells) {
            var number: Int
            do {
                number = Random().nextInt(10)
            } while (password.contains(number))
            password.add(number)
        }
        Log.d(LOG_TAG, "password: $password")
        return password
    }

    private fun createEmptyPassword(): Password {
        val cells = List(level.numberCells) { Cell() }.apply {
            this[0].isFocused = true
        }
        return Password(cells)
    }

}