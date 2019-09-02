package io.github.rosariopfernandes.rollapass.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import io.github.rosariopfernandes.rollapass.model.Account

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createAccount(account: Account)

    @Query("SELECT * FROM Account")
    fun getAll():LiveData<List<Account>>

    @Query("SELECT * FROM Account WHERE accountId = :id")
    fun getAccount(id:Int):LiveData<Account>

    @Update
    fun updateAccount(account: Account)

    @Delete
    fun deleteAccount(account: Account)
}