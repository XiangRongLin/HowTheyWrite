package com.kaiserpudding.howtheywrite.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.kaiserpudding.howtheywrite.database.AppDatabase
import com.kaiserpudding.howtheywrite.database.dao.CharacterDao
import com.kaiserpudding.howtheywrite.database.dao.LessonCharacterJoinDao
import com.kaiserpudding.howtheywrite.model.Character

class CharacterRepository(application: Application) {

    private val characterDao: CharacterDao
    private val lessonCharacterJoinDao: LessonCharacterJoinDao

    val allLiveDataCharacters: LiveData<List<Character>>
        get() = characterDao.allCharacters

    init {
        val db = AppDatabase.getDatabase(application)
        characterDao = db.characterDao()
        lessonCharacterJoinDao = db.lessonCharacterJoinJoinDao()
    }

    fun insertCharacter(character: Character) {
        characterDao.insert(character)
    }

    fun deleteCharacter(character: Character) {
        characterDao.delete(character)
    }

    fun getCharacterById(id: Int): Character {
        return characterDao.getCharacterById(id)
    }

    fun getLiveDataCharacterByLessonId(lessonId: Int): LiveData<List<Character>> {
        return lessonCharacterJoinDao.getLiveDataCharacterByLessonId(lessonId)
    }

    fun getCharacterByLessonId(lessonId: Int): MutableList<Character> {
        return lessonCharacterJoinDao.getCharacterByLessonId(lessonId)
    }

}
