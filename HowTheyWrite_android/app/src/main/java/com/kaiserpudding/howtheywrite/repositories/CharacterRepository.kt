package com.kaiserpudding.howtheywrite.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.kaiserpudding.howtheywrite.database.AppDatabase
import com.kaiserpudding.howtheywrite.database.dao.CharacterDao
import com.kaiserpudding.howtheywrite.database.dao.LessonCharacterJoinDao
import com.kaiserpudding.howtheywrite.model.Character
import kotlin.LazyThreadSafetyMode.NONE

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

    suspend fun getCharacterById(id: Int): Character {
        return characterDao.getCharacterById(id)
    }

    fun getLiveDataCharacterById(id: Int) :LiveData<Character> {
        return characterDao.getLiveDataCharacterById(id)
    }

    fun getLiveDataCharacterByLessonId(lessonId: Int): LiveData<List<Character>> {
        return lessonCharacterJoinDao.getLiveDataCharacterByLessonId(lessonId)
    }

    suspend fun getCharacterByLessonId(lessonId: Int): MutableList<Character> {
        return lessonCharacterJoinDao.getCharacterByLessonId(lessonId)
    }

    fun allLiveDataCharacters(): LiveData<List<Character>> {
        return characterDao.allCharacters()
    }
}
