package com.kaiserpudding.howtheywrite.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import java.util.LinkedList

@Entity(tableName = "characters")
class Character
/**
 * Constructor for a [Character].
 * translationKey can be null.
 * translation can be an empty list.
 * @param hanzi The hanzi of the word
 * @param pinyin The pinyin of the word
 * @param translationKey The translationKey of the word. Can be Null
 * @param translation The translations of the word
 * @param isCustom Specifies whether user created or modified this word
 */
(@field:ColumnInfo(name = "hanzi")
 var hanzi: String, @field:ColumnInfo(name = "pinyin")
 var pinyin: String, @field:ColumnInfo(name = "translationKey")
 var translationKey: String?, @field:ColumnInfo(name = "translation")
 var translation: String?, @field:ColumnInfo(name = "isCustom")
 var isCustom: Boolean) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    @Ignore
    private var lessons: MutableList<Lesson>
    @Embedded
    var progress: Progress? = null

    init {
        this.lessons = LinkedList()
        this.progress = Progress()
    }

    override fun toString(): String {
        //TODO include translation and translationKey
        return id.toString() + hanzi + pinyin
    }

    fun getLessons(): List<Lesson> {
        return lessons
    }

    fun setLessons(lessons: MutableList<Lesson>) {
        this.lessons = lessons
    }

    fun addLesson(lesson: Lesson) {
        this.lessons.add(lesson)
    }

    fun removeLesson(lesson: Lesson) {
        this.lessons.remove(lesson)
    }
}
