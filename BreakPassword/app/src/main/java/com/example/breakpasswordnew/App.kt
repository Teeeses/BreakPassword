package com.example.breakpasswordnew

import android.app.Application
import android.content.SharedPreferences
import com.example.breakpasswordnew.di.AppComponent
import com.example.breakpasswordnew.di.DaggerAppComponent

/** NoComment **/
class App: Application() {

    /**
     * Главный компонент приложения
     */
    var component: AppComponent? = null
        private set

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.factory().create(this)
    }

    companion object {

        const val LOG_TAG = "game_tag"
    }
}