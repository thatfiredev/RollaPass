package io.github.rosariopfernandes.rollapass.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Word(
        @PrimaryKey
        var id: Int?,
        var word: String
) {
    constructor(): this(null, "")
}