package com.example.breakpasswordnew.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.breakpasswordnew.domain.GameController
import com.example.breakpasswordnew.domain.GameControllerImpl
import com.example.breakpasswordnew.model.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject

class GameScreenViewModel(
    changePasswords: BehaviorSubject<Event> = BehaviorSubject.create(),
    private val controller: GameController = GameControllerImpl(changePasswords = changePasswords)
) : ViewModel(), GameController by controller {

    init {
        changePasswords
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                when (it) {
                    is Update -> {
                        _passwords.value = it.passwords
                    }
                    is Attempt -> {
                        _countAttempts.value = (it.attempts - 1).coerceAtLeast(0)
                    }
                    is CreateNewGame -> {
                        _countAttempts.value = 0
                        _createGame.value = it.passwords
                        _winState.value = false
                    }
                    is Win -> {
                        _winEvent.value = it.attempts
                        _winState.value = true
                    }
                }
            }
    }

    private val _createGame: MutableLiveData<List<Password>> = MutableLiveData()
    val createGame: LiveData<List<Password>> = _createGame

    private val _passwords: MutableLiveData<List<Password>> = MutableLiveData(allPasswords)
    val passwords: LiveData<List<Password>> = _passwords

    private val _winEvent: MutableLiveData<Int> = MutableLiveData()
    val winEvent: LiveData<Int> = _winEvent

    private val _countAttempts: MutableLiveData<Int> = MutableLiveData(0)
    val countAttempts: LiveData<Int> = _countAttempts

    private val _winState: MutableLiveData<Boolean> = MutableLiveData()
    val winState: LiveData<Boolean> = _winState
}