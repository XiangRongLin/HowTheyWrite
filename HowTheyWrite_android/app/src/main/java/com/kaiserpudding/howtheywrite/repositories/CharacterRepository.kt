package com.kaiserpudding.howtheywrite.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.kaiserpudding.howtheywrite.database.AppDatabase
import com.kaiserpudding.howtheywrite.database.dao.CharacterDao
import com.kaiserpudding.howtheywrite.database.dao.LessonCharacterJoinDao
import com.kaiserpudding.howtheywrite.model.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharacterRepository(application: Application) {

    private val characterDao: CharacterDao
    private val lessonCharacterJoinDao: LessonCharacterJoinDao

    init {
        val db = AppDatabase.getDatabase(application)
        characterDao = db.characterDao()
        lessonCharacterJoinDao = db.lessonCharacterJoinJoinDao()
    }

    suspend fun insert(character: Character) {
        withContext(Dispatchers.IO) {
            characterDao.insert(character)
        }

    }

    suspend fun delete(character: Character) {
        withContext(Dispatchers.IO) {
            characterDao.delete(character)
        }

    }

    fun getLiveDataCharacterByLessonId(lessonId: Int): LiveData<List<Character>> {
        return lessonCharacterJoinDao.getLiveDataCharacterByLessonId(lessonId)
    }

    fun allLiveDataCharacters(): LiveData<List<Character>> {
        return characterDao.allCharacters()
    }

    suspend fun getCharactersById(id: Int): Character {
        return characterDao.getCharacterById(id)
    }

    suspend fun getCharactersByLessonId(lessonId: Int): List<Character> {
        return lessonCharacterJoinDao.getCharacterByLessonId(lessonId)
    }

    suspend fun getCharactersByLessonIdInRandomOrder(lessonId: Int): List<Character> {
        return lessonCharacterJoinDao.getCharacterByLessonIdInRandomOrder(lessonId)
    }
}
