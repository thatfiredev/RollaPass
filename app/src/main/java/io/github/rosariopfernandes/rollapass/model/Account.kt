package io.github.rosariopfernandes.rollapass.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

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