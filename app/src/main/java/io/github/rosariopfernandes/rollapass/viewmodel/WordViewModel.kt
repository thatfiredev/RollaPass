package io.github.rosariopfernandes.rollapass.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.github.rosariopfernandes.rollapass.model.Word
import io.github.rosariopfernandes.rollapass.room.PassDatabase


class WordViewModel(app:Application) : AndroidViewModel(app) {

    var database: PassDatabase? = null
    var wordLiveData: LiveData<List<Word>>? = null

    init {
        database = PassDatabase.getInstance(app.applicationContext)
        wordLiveData = database!!.wordDao().getWords()
    }
}