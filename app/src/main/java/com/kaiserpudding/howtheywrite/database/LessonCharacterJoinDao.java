package com.kaiserpudding.howtheywrite.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;
import com.kaiserpudding.howtheywrite.model.Character;
import com.kaiserpudding.howtheywrite.model.Lesson;
import com.kaiserpudding.howtheywrite.model.LessonCharacterJoin;
import java.util.List;

@Dao
public interface LessonCharacterJoinDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertLessonCharacterJoin(LessonCharacterJoin lessonCharacterJoin);

  @Delete
  void deleteLessonCharacterJoin(LessonCharacterJoin lessonCharacterJoin);

  @Query("SELECT * FROM lesson_character_join")
  LiveData<LessonCharacterJoin> getAllLessonCharacterJoin();

  @Query("SELECT * FROM lesson_character_join\n"
      + "WHERE lessonId = :lessonId AND characterId = :characterId")
  LiveData<LessonCharacterJoin> getLessonCharacterJoin(int lessonId, int characterId);

  @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
  @Query("SELECT * FROM characters INNER JOIN lesson_character_join ON\n"
      + "characterId = lesson_character_join.characterId WHERE\n"
      + "lesson_character_join.lessonId = :id")
  LiveData<List<Character>> getLivaDataCharactersByLessonId(int id);

  @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
  @Query("SELECT * FROM characters INNER JOIN lesson_character_join ON\n"
      + "characterId = lesson_character_join.characterId WHERE\n"
      + "lesson_character_join.lessonId = :id")
  List<Character> getCharactersByLessonId(int id);

  @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
  @Query("SELECT * FROM lessons INNER JOIN lesson_character_join ON\n"
      + "lessonId = lesson_character_join.lessonId WHERE\n"
      + "lesson_character_join.characterId = :id")
  LiveData<List<Lesson>> getLiveDataLessonsByCharacterId(int id);

  @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
  @Query("SELECT * FROM lessons INNER JOIN lesson_character_join ON\n"
      + "lessonId = lesson_character_join.lessonId WHERE\n"
      + "lesson_character_join.characterId = :id")
  List<Lesson> getLessonsByCharacterId(int id);

}
