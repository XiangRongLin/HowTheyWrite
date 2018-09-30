package com.kaiserpudding.howtheywrite.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.kaiserpudding.howtheywrite.model.Lesson;
import java.util.List;

@Dao
public interface LessonDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  long insertLesson(Lesson lesson);

  @Delete
  void deleteLesson(Lesson lesson);

  @Update
  void updateLesson(Lesson lesson);

  @Query("SELECT * FROM lessons WHERE name = :name")
  Lesson getLessonByName(String name);

  @Query("SELECT * FROM lessons")
  LiveData<List<Lesson>> getAllLiveLessons();

  @Query("SELECT * FROM lessons")
  List<Lesson> getAllLessons();
}
