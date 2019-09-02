package io.github.rosariopfernandes.rollapass.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Account(
        @PrimaryKey(autoGenerate = true)
        var accountId:Int?,
        var userName:String,
        var userPassword:String,
        var userWebsite:String
) {
    constructor():this(null, "","","")
}