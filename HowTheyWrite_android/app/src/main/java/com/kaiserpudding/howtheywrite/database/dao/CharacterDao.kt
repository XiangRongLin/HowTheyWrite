package com.kaiserpudding.howtheywrite.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kaiserpudding.howtheywrite.model.Character

@Dao
interface CharacterDao : BaseDao<Character> {

    @get:Query("SELECT * FROM characters")
    val allCharacters: LiveData<List<Character>>

    @Query("SELECT * FROM characters WHERE id = :id LIMIT 1")
    fun getCharacterById(id: Int): Character

    @Query("SELECT * FROM characters WHERE id IN (:ids)")
    fun getCharactersByIds(ids: IntArray): List<Character>

}
