package com.kaiserpudding.howtheywrite.repositories

import android.app.Application
import android.arch.lifecycle.LiveData
import com.kaiserpudding.howtheywrite.database.AppDatabase
import com.kaiserpudding.howtheywrite.database.CharacterDao
import com.kaiserpudding.howtheywrite.database.LessonCharacterJoinDao
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
        characterDao.insertCharacter(character)
    }

    fun deleteCharacter(character: Character) {
        characterDao.deleteCharacter(character)
    }

    fun getCharacterById(id: Int): Character {
        return characterDao.getCharacterById(id)
    }

    fun getLiveDataCharacterByLessonId(lessonId: Int): LiveData<List<Character>> {
        return lessonCharacterJoinDao.getLiveDataCharacterByLessonId(lessonId)
    }

    fun getCharacterByLessonId(lessonId: Int): Array<Character> {
        return lessonCharacterJoinDao.getCharacterByLessonId(lessonId)
    }

    fun getCharactersByIds(characterIds: IntArray): Array<Character> {
        return characterDao.getCharactersByIds(characterIds)
    }

}
