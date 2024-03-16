package com.example.breakpasswordnew.domain

import com.example.breakpasswordnew.model.Password

interface GameController: KeyboardActon {

    val level: Complexity

    val allPasswords: List<Password>

    fun chooseLevel(level: Complexity)

    fun restart()

}