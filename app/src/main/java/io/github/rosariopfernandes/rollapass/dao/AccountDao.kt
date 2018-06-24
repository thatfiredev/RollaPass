package io.github.rosariopfernandes.rollapass.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
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