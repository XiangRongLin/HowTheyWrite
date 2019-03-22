package com.kaiserpudding.howtheywrite.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.kaiserpudding.howtheywrite.model.Character
import com.kaiserpudding.howtheywrite.model.Lesson
import com.kaiserpudding.howtheywrite.model.LessonCharacterJoin

@Dao
interface LessonCharacterJoinDao {

    @get:Query("SELECT * FROM lesson_character_join")
    val allLessonCharacterJoin: List<LessonCharacterJoin>

    @get:Query("SELECT * FROM lessons INNER JOIN lesson_character_join ON\n"
            + "lessonId = lesson_character_join.lessonId WHERE\n"
            + "characterId = lesson_character_join.characterId ")
    val allLessons: List<Lesson>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLessonCharacterJoin(lessonCharacterJoin: LessonCharacterJoin)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLessonCharacterJoin(lessonCharacterJoin: List<LessonCharacterJoin>)

    @Delete
    fun deleteLessonCharacterJoin(lessonCharacterJoin: LessonCharacterJoin)

    @Query("SELECT * FROM lesson_character_join\n" + "WHERE lessonId = :lessonId AND characterId = :characterId")
    fun getLessonCharacterJoin(lessonId: Int, characterId: Int): LessonCharacterJoin

    @Query("SELECT * FROM lessons INNER JOIN lesson_character_join ON\n"
            + "characterId = lesson_character_join.characterId WHERE\n"
            + "lesson_character_join.lessonId = :id")
    fun getLiveDataLessonByLessonId(id: Int): LiveData<Lesson>

    @Query("SELECT * FROM lessons INNER JOIN lesson_character_join ON\n"
            + "characterId = lesson_character_join.characterId WHERE\n"
            + "lesson_character_join.lessonId = :id")
    fun getLessonByLessonId(id: Int): Lesson

    @Query("SELECT * FROM characters INNER JOIN lesson_character_join ON\n"
            + "characters.id = lesson_character_join.characterId WHERE\n"
            + "lesson_character_join.lessonId = :id")
    fun getLiveDataCharacterByLessonId(id: Int): LiveData<List<Character>>

    @Query("SELECT * FROM characters INNER JOIN lesson_character_join ON\n"
            + "characters.id = lesson_character_join.characterId WHERE\n"
            + "lesson_character_join.lessonId = :id")
    fun getCharacterByLessonId(id: Int): Array<Character>

}
