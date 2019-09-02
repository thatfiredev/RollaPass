package io.github.rosariopfernandes.rollapass.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Word(
        @PrimaryKey
        var id: Int?,
        var word: String
) {
    constructor(): this(null, "")
}