package io.github.rosariopfernandes.rollapass.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class User(
        @PrimaryKey(autoGenerate = true)
        var userId: Int?,
        val masterPassword: String
) {
    constructor(): this(null, "")
}