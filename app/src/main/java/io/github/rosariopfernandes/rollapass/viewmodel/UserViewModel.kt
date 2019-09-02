package io.github.rosariopfernandes.rollapass.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.github.rosariopfernandes.rollapass.model.User
import io.github.rosariopfernandes.rollapass.room.PassDatabase


class UserViewModel(app:Application) : AndroidViewModel(app) {

    private var database: PassDatabase? = null
    var liveData: LiveData<User>? = null

    init {
        database = PassDatabase.getInstance(app.applicationContext)
        liveData = database!!.userDao().getUser()
    }
}