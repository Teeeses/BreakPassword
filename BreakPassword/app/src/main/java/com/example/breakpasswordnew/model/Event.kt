package com.example.breakpasswordnew.model

sealed interface Event

class Update(val passwords: List<Password>): Event

class Attempt(val attempts: Int): Event

class CreateNewGame(val numberCells: Int, val passwords: List<Password>): Event

class Win(val attempts: Int): Event