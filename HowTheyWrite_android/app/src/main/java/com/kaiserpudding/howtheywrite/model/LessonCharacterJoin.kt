package com.kaiserpudding.howtheywrite.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index


/**
 * https://stackoverflow.com/questions/44361824/how-can-i-represent-a-many-to-many-relation-with-android-room
 * https://stackoverflow.com/questions/50799324/many-to-many-relations-with-room-livedata
 * This POJO holds the n:m relation between lessons and characters
 */
@Entity(tableName = "lesson_character_join",
        primaryKeys = ["lessonId", "characterId"],
        foreignKeys = [ForeignKey(entity = Lesson::class, parentColumns = ["id"], childColumns = ["lessonId"], onDelete = CASCADE),
            ForeignKey(entity = Character::class, parentColumns = ["id"], childColumns = ["characterId"], onDelete = CASCADE)],
        indices = [Index(value = ["lessonId", "characterId"], unique = true), Index(value = ["characterId"])])
class LessonCharacterJoin(val lessonId: Long, val characterId: Long) {

    override fun toString(): String {
        return "LessonId: " + lessonId.toString() + "CharacterId: " + characterId.toString()
    }
}
