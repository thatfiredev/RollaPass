package io.github.rosariopfernandes.rollapass.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
        @PrimaryKey(autoGenerate = true)
        var userId: Int?,
        var masterPassword: String
) {
    constructor(): this(null, "")
}