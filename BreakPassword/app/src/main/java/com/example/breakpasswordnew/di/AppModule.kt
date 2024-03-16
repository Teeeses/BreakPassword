package com.example.breakpasswordnew.di

import android.content.Context
import android.util.DisplayMetrics
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @PerApplication
    @Provides
    fun provideDisplayMetrics(context: Context): DisplayMetrics {
        return context.resources.displayMetrics
    }
}