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

    @Query("SELECT * FROM lessons")
    fun allLessons(): LiveData<List<Lesson>>

    @Query("SELECT name FROM lessons")
    suspend fun allLessonNames(): List<String>
}
