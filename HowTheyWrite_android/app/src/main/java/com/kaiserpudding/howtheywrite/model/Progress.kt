package com.kaiserpudding.howtheywrite.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey

class Progress {

    @ColumnInfo(name = "value")
    var value: Int = 0

    init {
        this.value = 0
    }

    override fun toString(): String {
        return "Value: $value"

    }
}
