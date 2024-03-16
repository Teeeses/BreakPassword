package com.example.breakpasswordnew.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component

@PerApplication
@Component(modules = [AppModule::class])
interface AppComponent {

    /** NoComment **/
    val context: Context

    @Component.Factory
    interface Factory {

        /** NoComment **/
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
}