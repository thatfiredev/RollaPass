package io.github.rosariopfernandes.rollapass.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.rosariopfernandes.rollapass.model.Word

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createWords(words: List<Word>)

    @Query("SELECT * FROM Word")
    fun getWords(): LiveData<List<Word>>
}