package com.kaiserpudding.howtheywrite.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kaiserpudding.howtheywrite.model.Character
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
            +   "characters.id = lesson_character_join.characterId WHERE\n"
            + "lesson_character_join.lessonId = :id")
    fun getLiveDataCharacterByLessonId(id: Long): LiveData<List<Character>>

    @Query("SELECT * FROM characters INNER JOIN lesson_character_join ON\n"
            + "characters.id = lesson_character_join.characterId WHERE\n"
            + "lesson_character_join.lessonId = :id")
    suspend fun getCharacterByLessonId(id: Long): List<Character>

    @Query("SELECT * FROM characters INNER JOIN lesson_character_join ON\n"
            + "characters.id = lesson_character_join.characterId WHERE\n"
            + "lesson_character_join.lessonId = :id\n"
            + "ORDER BY RANDOM()")
    suspend fun getCharacterByLessonIdInRandomOrder(id: Long): List<Character>

    @Query("DELETE FROM lesson_character_join WHERE lessonId = :lessonId AND characterId IN (:characterIds)")
    suspend fun deleteLessonCharacterJoins(lessonId: Long, characterIds: LongArray)

    @Query("DELETE FROM lesson_character_join WHERE characterId IN (:characterIds)")
    suspend fun deleteAllLessonCharacterJoinsFromCharacter(characterIds: LongArray)

    @Query("DELETE FROM lesson_character_join WHERE lessonId IN (:lessonIds)")
    fun deleteAllLessonCharacterJoinsFromLesson(lessonIds: LongArray)


}
