package io.github.rosariopfernandes.rollapass.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import io.github.rosariopfernandes.rollapass.model.Account
import io.github.rosariopfernandes.rollapass.room.PassDatabase


class AccountViewModel(app:Application, accountId:Int) : AndroidViewModel(app) {

    var database: PassDatabase? = null
    var account:LiveData<Account>? = null

    init {
        database = PassDatabase.getInstance(app.applicationContext)
        account = database!!.accountDao().getAccount(accountId)
    }
}