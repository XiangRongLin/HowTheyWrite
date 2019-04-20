package com.kaiserpudding.howtheywrite.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.kaiserpudding.howtheywrite.database.AppDatabase
import com.kaiserpudding.howtheywrite.database.dao.LessonCharacterJoinDao
import com.kaiserpudding.howtheywrite.database.dao.LessonDao
import com.kaiserpudding.howtheywrite.model.Lesson
import com.kaiserpudding.howtheywrite.model.LessonCharacterJoin

class LessonRepository(application: Application) {

    private val lessonDao: LessonDao
    private val lessonCharacterJoinDao: LessonCharacterJoinDao


    init {
        val db = AppDatabase.getDatabase(application)
        lessonDao = db.lessonDao()
        lessonCharacterJoinDao = db.lessonCharacterJoinJoinDao()
    }

    suspend fun insert(lesson: Lesson) {
        lessonDao.insert(lesson)
        insertLessonCharacterJoin(lesson)
    }

    suspend fun delete(lesson: Lesson) {
        lessonDao.delete(lesson)
        //TODO delete lessonCharacterJoin
    }

    fun allLiveDataLessons(): LiveData<List<Lesson>> {
        return lessonDao.allLessons()
    }

    private suspend fun insertLessonCharacterJoin(lesson: Lesson) {
        val characters = lesson.characters
        val numberOfCharacters = characters.size
        if (numberOfCharacters > 0) {
            val lessonId = lesson.id
            val toInsert = arrayOf<LessonCharacterJoin>()
            for (i in 0 until numberOfCharacters) {
                toInsert[i] = LessonCharacterJoin(lessonId, characters[i].id)
            }
            lessonCharacterJoinDao.insertLessonCharacterJoin(toInsert)
        }
    }
}
