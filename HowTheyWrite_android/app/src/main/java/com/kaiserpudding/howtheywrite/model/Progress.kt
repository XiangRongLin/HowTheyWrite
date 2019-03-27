package com.kaiserpudding.howtheywrite.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

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
