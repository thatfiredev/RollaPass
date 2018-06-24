package io.github.rosariopfernandes.rollapass.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import io.github.rosariopfernandes.rollapass.model.Account

class AccountsViewModel(val app:Application) : AndroidViewModel(app) {
    var accounts: LiveData<List<Account>>? = null
    //var database: PassDatabase? = null

    init {
        //database = PassDatabase.getInstance(app.applicationContext)
        //accounts = database!!.categoryDao().selectAll()
    }
}