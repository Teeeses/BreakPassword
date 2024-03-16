package com.example.breakpasswordnew.presentation.di

import com.example.breakpasswordnew.presentation.GameScreenViewModel
import com.example.breakpasswordnew.presentation.GameScreenViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class GameScreenModule {

    @Provides
    @GameScope
    fun provideViewModel(
        factory: GameScreenViewModelFactory
    ): GameScreenViewModel {
        return factory.create(GameScreenViewModel::class.java)
    }
}