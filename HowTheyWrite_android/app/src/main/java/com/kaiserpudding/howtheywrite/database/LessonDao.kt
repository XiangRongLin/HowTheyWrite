package com.kaiserpudding.howtheywrite.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.kaiserpudding.howtheywrite.model.Lesson

@Dao
interface LessonDao {

    @get:Query("SELECT * FROM lessons")
    val allLessons: LiveData<List<Lesson>>

    @get:Query("SELECT name FROM lessons")
    val allLessonNames: List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLesson(lesson: Lesson): Long

    @Delete
    fun deleteLesson(lesson: Lesson)

    @Update
    fun updateLesson(lesson: Lesson)

    @Query("SELECT * FROM lessons WHERE name = :name")
    fun getLessonByName(name: String): Lesson
}
