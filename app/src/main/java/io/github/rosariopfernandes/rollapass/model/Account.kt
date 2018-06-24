package io.github.rosariopfernandes.rollapass.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Account(
        @PrimaryKey(autoGenerate = true)
        val accountId:Int?,
        val userName:String,
        val userPassword:String,
        val userWebsite:String
) {
    constructor():this(null, "","","")
}