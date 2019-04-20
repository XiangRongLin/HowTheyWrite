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

    init {
        val db = AppDatabase.getDatabase(application)
        characterDao = db.characterDao()
        lessonCharacterJoinDao = db.lessonCharacterJoinJoinDao()
    }

    suspend fun insert(character: Character) {
        characterDao.insert(character)
    }

    suspend fun delete(character: Character) {
        characterDao.delete(character)
    }

    fun getLiveDataCharacterById(id: Int) :LiveData<Character> {
        return characterDao.getLiveDataCharacterById(id)
    }

    fun getLiveDataCharacterByLessonId(lessonId: Int): LiveData<List<Character>> {
        return lessonCharacterJoinDao.getLiveDataCharacterByLessonId(lessonId)
    }

    fun allLiveDataCharacters(): LiveData<List<Character>> {
        return characterDao.allCharacters()
    }
}
