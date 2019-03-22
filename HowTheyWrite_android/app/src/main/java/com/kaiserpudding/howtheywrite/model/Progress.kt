package com.kaiserpudding.howtheywrite.model

import android.arch.persistence.room.ColumnInfo

class Progress {

    @ColumnInfo(name = "value")
    var value: Int = 0

    init {
        this.value = 0
    }

    override fun toString(): String {
        return "Value: " + value.toString()

    }
}
