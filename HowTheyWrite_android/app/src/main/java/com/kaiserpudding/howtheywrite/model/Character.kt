package com.kaiserpudding.howtheywrite.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

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

//    @Ignore
//    constructor(id: Int, hanzi: String, pinyin: String, translationKey: String?, translation: String?, isCustom: Boolean, progress: Progress = Progress())
//            : this(hanzi, pinyin, translationKey, translation, isCustom, progress) {
//        this.id = id
//    }

    override fun toString(): String {
        //TODO include translation and translationKey
        return id.toString() + hanzi + pinyin
    }
}
