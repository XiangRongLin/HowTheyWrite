package com.kaiserpudding.howtheywrite.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;
import android.arch.persistence.room.Update;
import com.kaiserpudding.howtheywrite.model.CharacterWord;
import com.kaiserpudding.howtheywrite.model.LessonWordJoin;
import java.util.List;

@Dao
public interface LessonWordJoinDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertLessonWordJoin(LessonWordJoin lessonWordJoin);

  @Delete
  void deleteLessonWordJoin(LessonWordJoin lessonWordJoin);

  @Update
  void updateLessonWordJoin(LessonWordJoin lessonWordJoin);

  @Query("SELECT * FROM lesson_word_join")
  LiveData<LessonWordJoin> getAllLessonWordJoin();

  @Query("SELECT * FROM lesson_word_join\n"
      + "WHERE lessonId = :lessonId AND wordId = :wordId")
  LiveData<LessonWordJoin> getLessonWordJoin(int lessonId, int wordId);

  @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
  @Query("SELECT * FROM characters INNER JOIN lesson_word_join ON\n"
      + "wordId = lesson_word_join.wordId WHERE\n"
      + "lesson_word_join.lessonId = :id")
  LiveData<List<CharacterWord>> getWordsWithLessonId(int id);

}
