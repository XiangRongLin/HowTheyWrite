package com.kaiserpudding.howtheywrite.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.kaiserpudding.howtheywrite.model.Lesson;
import com.kaiserpudding.howtheywrite.model.Word;

@Dao
public interface LessonDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertLesson(Lesson lesson);

  @Delete
  void deleteLesson(Lesson lesson);

  @Update
  void updateLesson(Lesson lesson);

  @Query("SELECT * FROM lessons WHERE name = :name")
  Lesson getLesson(String name);

  @Query("SELECT words.* FROM words\n"
      + "JOIN lesson_word_join ON words.id=lesson_word_join.wordId\n"
      + "WHERE lesson_word_join.lessonId=:lessonId")
  Word[] getWordsOfLesson(int lessonId);
}
