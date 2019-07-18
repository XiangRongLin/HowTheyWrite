package com.kaiserpudding.howtheywrite.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.kaiserpudding.howtheywrite.model.Lesson

@Dao
interface LessonDao : BaseDao<Lesson>{

    @Query("SELECT * FROM lessons")
    fun allLessons(): LiveData<List<Lesson>>

    @Query("SELECT name FROM lessons")
    suspend fun allLessonNames(): List<String>

    @Query("SELECT name FROM  lessons WHERE id = :lessonId")
    fun getLiveDataLessonNameById(lessonId: Int): LiveData<String>

    @Query("DELETE FROM lessons WHERE id IN (:lessonIds)")
    fun deleteLessons(lessonIds: IntArray)
}
