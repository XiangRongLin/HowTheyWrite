package com.kaiserpudding.howtheywrite.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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
