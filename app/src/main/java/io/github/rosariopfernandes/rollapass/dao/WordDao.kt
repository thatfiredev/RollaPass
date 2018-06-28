package io.github.rosariopfernandes.rollapass.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.github.rosariopfernandes.rollapass.model.Word

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createWords(words: List<Word>)

    /*@Query("SELECT * FROM Word WHERE id = :id")
    fun getWord(id: Int): LiveData<Word>*/

    @Query("SELECT * FROM Word")
    fun getWords(): LiveData<List<Word>>
}