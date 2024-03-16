package com.example.breakpasswordnew.presentation.router

interface Router {

    /**
     * Показать экран игры
     */
    fun showGameFragment()

    /**
     * Показать экран победы
     *
     * @param attempts количество попыток ввода было совершено
     */
    fun showWinFragment(attempts: Int, bestValue: Int)

    /**
     * Показать диалог правил игры
     */
    fun showRulesGameDialog()

    /**
     * Нажата кнопка назад во фрагменте
     */
    fun onBackPressed()
}