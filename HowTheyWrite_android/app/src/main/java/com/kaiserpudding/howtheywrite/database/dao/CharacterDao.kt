package com.kaiserpudding.howtheywrite.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.kaiserpudding.howtheywrite.model.Character

@Dao
interface CharacterDao : BaseDao<Character> {

    @Query("""SELECT * FROM characters""")
    fun allCharacters(): LiveData<List<Character>>

    @Query("""SELECT * FROM characters
        WHERE id = :id LIMIT 1""")
    suspend fun getCharacterById(id: Long): Character

    @Query("""SELECT * FROM characters
        WHERE id = :id
        LIMIT 1""")
    fun getLiveDataCharacterById(id: Long): LiveData<Character>

    @Query("""SELECT * FROM characters
        WHERE id IN (:characterIds)
        ORDER BY RANDOM()""")
    suspend fun getCharactersByIdInRandomOrder(characterIds: LongArray): List<Character>

    @Query("""DELETE FROM characters
        WHERE id IN (:characterIds)""")
    suspend fun delete(characterIds: LongArray)

}
