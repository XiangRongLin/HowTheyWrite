package com.kaiserpudding.howtheywrite.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kaiserpudding.howtheywrite.model.Character
import com.kaiserpudding.howtheywrite.model.LessonCharacterJoin

@Dao
interface LessonCharacterJoinDao : BaseDao<LessonCharacterJoin> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLessonCharacterJoin(lessonCharacterJoin: LessonCharacterJoin)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLessonCharacterJoin(lessonCharacterJoin: Array<LessonCharacterJoin>)

    @Query("""SELECT * FROM characters INNER JOIN lesson_character_join ON
            characters.id = lesson_character_join.characterId WHERE
            lesson_character_join.lessonId = :id""")
    fun getLiveDataCharacterByLessonId(id: Long): LiveData<List<Character>>

    @Query("""SELECT * FROM characters INNER JOIN lesson_character_join ON
            characters.id = lesson_character_join.characterId WHERE
            lesson_character_join.lessonId = :id""")
    suspend fun getCharacterByLessonId(id: Long): List<Character>

    @Query("""SELECT * FROM characters INNER JOIN lesson_character_join ON
            characters.id = lesson_character_join.characterId WHERE
            lesson_character_join.lessonId = :id
            ORDER BY RANDOM()""")
    suspend fun getCharacterByLessonIdInRandomOrder(id: Long): List<Character>

    @Query("""DELETE FROM lesson_character_join
        WHERE lessonId = :lessonId AND characterId IN (:characterIds)""")
    suspend fun deleteLessonCharacterJoins(lessonId: Long, characterIds: LongArray)

    @Query("""DELETE FROM lesson_character_join
        WHERE characterId IN (:characterIds)""")
    suspend fun deleteAllLessonCharacterJoinsFromCharacter(characterIds: LongArray)

    @Query("""DELETE FROM lesson_character_join
        WHERE lessonId IN (:lessonIds)""")
    fun deleteAllLessonCharacterJoinsFromLesson(lessonIds: LongArray)

    @Query("""INSERT INTO lesson_character_join (lessonId, characterId)
            SELECT :addToId, characterId
            FROM lesson_character_join
            WHERE lessonId IN (:lessonIds)""")
    fun addCharactersOfLessonToLesson(lessonIds: LongArray, addToId: Long)

    @Query("""DELETE FROM characters WHERE id = :id
            AND NOT EXISTS (SELECT * FROM lesson_character_join WHERE characterId = :id)""")
    fun deleteCharacterIfNoJoin(id: Long)

    @Query("""SELECT DISTINCT characterId
        FROM lesson_character_join
        WHERE lessonId IN (:lessonIds)""")
    fun getCharacterIdsByLessonIds(lessonIds: LongArray): LongArray


}
