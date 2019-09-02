package io.github.rosariopfernandes.rollapass.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.github.rosariopfernandes.rollapass.model.Account
import io.github.rosariopfernandes.rollapass.room.PassDatabase


class AccountsViewModel(app:Application) : AndroidViewModel(app) {
    var accounts: LiveData<List<Account>>? = null
    var database: PassDatabase? = null

    init {
        database = PassDatabase.getInstance(app.applicationContext)
        accounts = database!!.accountDao().getAll()
    }
}