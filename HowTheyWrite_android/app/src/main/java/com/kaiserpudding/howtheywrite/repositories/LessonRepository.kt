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

    val allLiveDataLessons: LiveData<List<Lesson>>
        get() = lessonDao.allLessons

    val allLessonNames: List<String>
        get() = lessonDao.allLessonNames

    init {
        val db = AppDatabase.getDatabase(application)
        lessonDao = db.lessonDao()
        lessonCharacterJoinDao = db.lessonCharacterJoinJoinDao()
    }

    fun insert(lesson: Lesson) {
        lessonDao.insert(lesson)
        lessonDao.insert(lesson)
        insertLessonCharacterJoin(lesson)
    }

    fun insertLessonCharacterJoin(lesson: Lesson) {
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

    fun getLessonWithRelationById(id: Int): Lesson {
        return lessonCharacterJoinDao.getLessonByLessonId(id)
    }

    fun getLiveDataLessonWithRelationById(id: Int): LiveData<Lesson> {
        return lessonCharacterJoinDao.getLiveDataLessonByLessonId(id)
    }
}