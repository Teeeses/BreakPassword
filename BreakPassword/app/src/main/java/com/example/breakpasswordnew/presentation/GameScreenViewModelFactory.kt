package com.example.breakpasswordnew.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.breakpasswordnew.domain.GameControllerImpl
import javax.inject.Inject

class GameScreenViewModelFactory @Inject constructor() : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GameScreenViewModel() as T
    }
}