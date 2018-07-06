package io.github.rosariopfernandes.rollapass.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import io.github.rosariopfernandes.rollapass.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createUser(user: User)

    @Update
    fun updateUser(user: User)

    @Query("SELECT * FROM User WHERE userId = 1")
    fun getUser(): LiveData<User>
}