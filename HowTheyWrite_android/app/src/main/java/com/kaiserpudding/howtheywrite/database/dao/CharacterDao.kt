package com.kaiserpudding.howtheywrite.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kaiserpudding.howtheywrite.model.Character

@Dao
interface CharacterDao : BaseDao<Character> {

    @Query("SELECT * FROM characters")
    fun allCharacters(): LiveData<List<Character>>

    @Query("SELECT * FROM characters WHERE id = :id LIMIT 1")
    suspend fun getCharacterById(id: Int): Character

    @Query("SELECT * FROM characters WHERE id = :id LIMIT 1")
    fun getLiveDataCharacterById(id: Int): LiveData<Character>

    @Query("SELECT * FROM characters WHERE id IN (:ids) ORDER BY RANDOM()")
    suspend fun getCharactersByIdInRandomOrder(ids: IntArray): List<Character>

}
