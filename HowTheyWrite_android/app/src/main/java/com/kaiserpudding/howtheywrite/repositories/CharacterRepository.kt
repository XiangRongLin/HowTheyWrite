package com.kaiserpudding.howtheywrite.repositories

import android.app.Application
import android.util.Log
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

    suspend fun delete(characterIds: LongArray) {
        withContext(Dispatchers.IO) {
            characterDao.delete(characterIds)
            lessonCharacterJoinDao.deleteAllLessonCharacterJoinsFromCharacter(characterIds)
        }
    }

    fun getLiveDataCharacterByLessonId(lessonId: Long): LiveData<List<Character>> {
        return lessonCharacterJoinDao.getLiveDataCharacterByLessonId(lessonId)
    }

    fun allLiveDataCharacters(): LiveData<List<Character>> {
        return characterDao.allCharacters()
    }

    suspend fun getCharacterById(id: Long): Character {
        return characterDao.getCharacterById(id)
    }

    suspend fun getCharactersByIds(characterIds: LongArray): List<Character> {
        return characterDao.getCharactersByIdInRandomOrder(characterIds)
    }

    suspend fun getCharactersByLessonId(lessonId: Long): List<Character> {
        return lessonCharacterJoinDao.getCharacterByLessonId(lessonId)
    }

    suspend fun getCharactersByLessonIdInRandomOrder(lessonId: Long): List<Character> {
        return lessonCharacterJoinDao.getCharacterByLessonIdInRandomOrder(lessonId)
    }

    suspend fun addNewCharacterToLesson(character: Character, lessonId: Long) {
        withContext(Dispatchers.IO) {
            val characterId = characterDao.insert(character)
            val lessonCharacterJoin = LessonCharacterJoin(lessonId, characterId)
            Log.v("MY_IO", "Adding $lessonCharacterJoin")
            lessonCharacterJoinDao.insertLessonCharacterJoin(lessonCharacterJoin)
        }
    }

    suspend fun addCharactersToLesson(lessonId: Long, characterIds: LongArray) {
        withContext(Dispatchers.IO) {
            val lessonCharacterJoins = Array(characterIds.size) { i ->
                LessonCharacterJoin(lessonId, characterIds[i])
            }
            lessonCharacterJoinDao.insertLessonCharacterJoin(lessonCharacterJoins)
        }
    }

    suspend fun deleteCharactersFromLesson(lessonId: Long, characterIds: LongArray) {
        withContext(Dispatchers.IO) {
            lessonCharacterJoinDao.deleteLessonCharacterJoins(lessonId, characterIds)
        }
    }

    suspend fun addCharactersOfLessonToLesson(lessonIds: LongArray, addToId: Long) {
        withContext(Dispatchers.IO) {
            lessonCharacterJoinDao.addCharactersOfLessonToLesson(lessonIds, addToId)
        }
    }
}
