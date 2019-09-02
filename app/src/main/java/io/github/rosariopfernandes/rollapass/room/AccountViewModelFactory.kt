package io.github.rosariopfernandes.rollapass.room

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.rosariopfernandes.rollapass.viewmodel.AccountViewModel

class AccountViewModelFactory(val app:Application, val accountId: Int) :
        ViewModelProvider.AndroidViewModelFactory(app) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AccountViewModel(app, accountId) as T
    }

}