package com.kaiserpudding.howtheywrite.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.kaiserpudding.howtheywrite.database.AppDatabase
import com.kaiserpudding.howtheywrite.database.dao.CharacterDao
import com.kaiserpudding.howtheywrite.database.dao.LessonCharacterJoinDao
import com.kaiserpudding.howtheywrite.model.Character
import com.kaiserpudding.howtheywrite.model.LessonCharacterJoin
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

    suspend fun delete(characterIds: IntArray) {
        withContext(Dispatchers.IO) {
            characterDao.delete(characterIds)
            lessonCharacterJoinDao.deleteAllLessonCharacterJoinsFromCharacter(characterIds)
        }
    }

    fun getLiveDataCharacterByLessonId(lessonId: Int): LiveData<List<Character>> {
        return lessonCharacterJoinDao.getLiveDataCharacterByLessonId(lessonId)
    }

    fun allLiveDataCharacters(): LiveData<List<Character>> {
        return characterDao.allCharacters()
    }

    suspend fun getCharacterById(id: Int): Character {
        return characterDao.getCharacterById(id)
    }

    suspend fun getCharactersByIds(ids: IntArray): List<Character> {
        return characterDao.getCharactersByIdInRandomOrder(ids)
    }

    suspend fun getCharactersByLessonId(lessonId: Int): List<Character> {
        return lessonCharacterJoinDao.getCharacterByLessonId(lessonId)
    }

    suspend fun getCharactersByLessonIdInRandomOrder(lessonId: Int): List<Character> {
        return lessonCharacterJoinDao.getCharacterByLessonIdInRandomOrder(lessonId)
    }

    suspend fun addCharactersToLesson(lessonId: Int, characterIds: IntArray) {
        withContext(Dispatchers.IO) {
            val lessonCharacterJoins = Array(characterIds.size) { i ->
                LessonCharacterJoin(lessonId, characterIds[i])
            }
            lessonCharacterJoinDao.insertLessonCharacterJoin(lessonCharacterJoins)
        }
    }

    suspend fun deleteCharactersFromLesson(lessonId: Int, characterIds: IntArray) {
        withContext(Dispatchers.IO) {
            lessonCharacterJoinDao.deleteLessonCharacterJoins(lessonId, characterIds)
        }
    }
}
