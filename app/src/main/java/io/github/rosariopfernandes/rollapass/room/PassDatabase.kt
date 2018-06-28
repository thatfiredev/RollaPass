package io.github.rosariopfernandes.rollapass.room

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.util.Log
import io.github.rosariopfernandes.rollapass.R
import io.github.rosariopfernandes.rollapass.dao.AccountDao
import io.github.rosariopfernandes.rollapass.dao.WordDao
import io.github.rosariopfernandes.rollapass.model.Account
import io.github.rosariopfernandes.rollapass.model.User
import io.github.rosariopfernandes.rollapass.model.Word
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.concurrent.Executors

@Database(entities = arrayOf(Account::class, User::class, Word::class), version = 1)
abstract class PassDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao
    abstract fun wordDao(): WordDao

    companion object {
        private var INSTANCE: PassDatabase? = null

        fun getInstance(context: Context): PassDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        /*fun getInstance(context: Context): PassDatabase? {
            if (INSTANCE == null) {
                synchronized(PassDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            PassDatabase::class.java, "RollAPass.db")
                            .build()
                }
            }
            return INSTANCE
        }*/
        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        PassDatabase::class.java, "RollAPass.db")
                        .addCallback(object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                // insert the data on the IO Thread
                                Executors.newSingleThreadExecutor().execute {
                                    getInstance(context)
                                            .wordDao().createWords(readWordsFromFile(context))
                                }
                            }
                        })
                        .build()

        private fun readWordsFromFile(context: Context): List<Word>
        {
            val wordList = ArrayList<Word>()
            val inputStream = context.resources.openRawResource(R.raw.words)
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)

            var line: String?
            var elementsArray: List<String>?
            try {
                line = bufferedReader.readLine()
                while(line != null && line != ""){
                    elementsArray = line.split(" ")
                    wordList.add(Word(elementsArray[0].toInt(), elementsArray[1]))
                    Log.e("Read:", "$line")
                    line = bufferedReader.readLine()
                }
                bufferedReader.close()
                inputStreamReader.close()
                inputStream.close()
            }catch (exception: IOException){
                exception.printStackTrace()
            }
            return wordList
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}