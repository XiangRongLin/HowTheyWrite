package com.kaiserpudding.howtheywrite.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.kaiserpudding.howtheywrite.model.Character

@Dao
interface CharacterDao {

    @get:Query("SELECT * FROM characters")
    val allCharacters: LiveData<List<Character>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(characterWord: Character): Long

    @Delete
    fun deleteCharacter(characterWord: Character)

    @Update
    fun updateCharacter(characterWord: Character)

    @Query("SELECT * FROM characters WHERE id = :id LIMIT 1")
    fun getCharacterById(id: Int): Character

    @Query("SELECT * FROM characters WHERE id IN (:ids)")
    fun getCharactersByIds(ids: IntArray): Array<Character>
}
