package com.kaiserpudding.howtheywrite.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
class Character(
        var hanzi: String,
        var pinyin: String,
        var translationKey: String?,
        var translation: String?,
        var isCustom: Boolean
) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    @Embedded
    var progress: Progress = Progress()

    @Ignore
    constructor(hanzi: String, pinyin: String, translation: String?)
            : this(hanzi, pinyin, null, translation, true)

    override fun toString(): String {
        //TODO include translation and translationKey
        return id.toString() + hanzi + pinyin
    }
}
