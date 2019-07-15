package ru.explead.breakpassword.beans

class Data(
        /**
         * Количество цифр на своих местах
         */
        val onPlace: Int,
        /**
         * Колоичество цифр которые совпали
         */
        val matches: Int, val probable: String)
