package io.github.rosariopfernandes.rollapass.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import io.github.rosariopfernandes.rollapass.dao.AccountDao
import io.github.rosariopfernandes.rollapass.model.Account
import io.github.rosariopfernandes.rollapass.model.User

@Database(entities = arrayOf(Account::class, User::class), version = 1)
abstract class PassDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao

    companion object {
        private var INSTANCE: PassDatabase? = null

        fun getInstance(context: Context): PassDatabase? {
            if (INSTANCE == null) {
                synchronized(PassDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            PassDatabase::class.java, "RollAPass.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}