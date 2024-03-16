package com.example.breakpasswordnew

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.breakpasswordnew.domain.Complexity

object SharedPreferencesUtils {

    private const val BEST_EASY = "best_easy"
    private const val BEST_MEDIUM = "best_medium"
    private const val BEST_HARD = "best_hard"
    private const val BEST_VERY_HARD = "best_very_hard"

    private const val APP_PREFERENCES = "settings"

    /**
     * Сохранение настроек
     * @param level - уровень
     * @param times - количество попыток
     */
    fun saveSettings(context: Context, times: Int, level: Complexity) {
        saveSettingsChoice(context, times, getSaver(level))
    }

    fun getBestValue(context: Context, level: Complexity): Int {
        return getPref(context).getInt(getSaver(level), 0)
    }

    private fun getSaver(level: Complexity): String {
        return when (level) {
            Complexity.EASY -> BEST_EASY
            Complexity.MEDIUM -> BEST_MEDIUM
            Complexity.HARD -> BEST_HARD
            Complexity.VERY_HARD -> BEST_VERY_HARD
        }
    }

    private fun saveSettingsChoice(context: Context, times: Int, saver: String?) {
        val pref = getPref(context)
        if (times < pref.getInt(saver, Int.MAX_VALUE)) {
            val editor: SharedPreferences.Editor = pref.edit()
            editor.putInt(saver, times)
            editor.apply()
        }
    }

    private fun getPref(context: Context): SharedPreferences {
        return context.getSharedPreferences(APP_PREFERENCES, Application.MODE_PRIVATE)
    }
}