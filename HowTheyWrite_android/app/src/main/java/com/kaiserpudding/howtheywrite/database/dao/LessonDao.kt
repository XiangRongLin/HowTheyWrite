package com.kaiserpudding.howtheywrite.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.kaiserpudding.howtheywrite.model.Lesson

@Dao
interface LessonDao : BaseDao<Lesson>{

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
