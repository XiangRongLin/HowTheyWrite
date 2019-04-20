package com.kaiserpudding.howtheywrite.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomWarnings
import com.kaiserpudding.howtheywrite.model.Character
import com.kaiserpudding.howtheywrite.model.Lesson
import com.kaiserpudding.howtheywrite.model.LessonCharacterJoin

@Dao
interface LessonCharacterJoinDao : BaseDao<LessonCharacterJoin>{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLessonCharacterJoin(lessonCharacterJoin: Array<LessonCharacterJoin>)

//    @Query("SELECT * FROM lessons INNER JOIN lesson_character_join ON\n"
//            + "characterId = lesson_character_join.characterId WHERE\n"
//            + "lesson_character_join.lessonId = :id")
//    suspend fun getLiveDataLessonByLessonId(id: Int): LiveData<Lesson>
//
//    @Query("SELECT * FROM lessons INNER JOIN lesson_character_join ON\n"
//            + "characterId = lesson_character_join.characterId WHERE\n"
//            + "lesson_character_join.lessonId = :id")
//    suspend fun getLessonByLessonId(id: Int): Lesson

    @Query("SELECT * FROM characters INNER JOIN lesson_character_join ON\n"
            + "characters.id = lesson_character_join.characterId WHERE\n"
            + "lesson_character_join.lessonId = :id")
    fun getLiveDataCharacterByLessonId(id: Int): LiveData<List<Character>>

    @Query("SELECT * FROM characters INNER JOIN lesson_character_join ON\n"
            + "characters.id = lesson_character_join.characterId WHERE\n"
            + "lesson_character_join.lessonId = :id")
    suspend fun getCharacterByLessonId(id: Int): MutableList<Character>

}
