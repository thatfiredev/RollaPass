package io.github.rosariopfernandes.rollapass.room

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import io.github.rosariopfernandes.rollapass.viewmodel.AccountViewModel

class AccountViewModelFactory(val app:Application, val accountId: Int) :
        ViewModelProvider.AndroidViewModelFactory(app) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AccountViewModel(app, accountId) as T
    }

}