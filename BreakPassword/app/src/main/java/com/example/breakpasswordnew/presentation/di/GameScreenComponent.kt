package com.example.breakpasswordnew.presentation.di

import com.example.breakpasswordnew.di.AppComponent
import com.example.breakpasswordnew.presentation.GameFragment
import dagger.Component

@GameScope
@Component(modules = [GameScreenModule::class], dependencies = [AppComponent::class])
interface GameScreenComponent {

    fun inject(fragment: GameFragment)

    @Component.Factory
    interface Factory {

        fun create(appComponent: AppComponent): GameScreenComponent
    }
}