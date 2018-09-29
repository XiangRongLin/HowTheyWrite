package com.kaiserpudding.howtheywrite.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.kaiserpudding.howtheywrite.model.Character;
import com.kaiserpudding.howtheywrite.model.Lesson;
import com.kaiserpudding.howtheywrite.model.LessonCharacterJoin;
import com.kaiserpudding.howtheywrite.model.Progress;


@Database(entities = {/*Word.class,
    HiraganaWord.class,
    KatakanaWord.class,*/
    Character.class,
    Lesson.class,
    LessonCharacterJoin.class,
    Progress.class},
    version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

/*  public abstract HiraganaWordDao hiraganaWordDao();
  public abstract KatakanaWordDao katakanaWordDao();*/
  public abstract CharacterDao characterDao();
  public abstract LessonDao lessonDao();
  public abstract LessonCharacterJoinDao lessonCharacterJoinJoinDao();
  public abstract ProgressDao progressDao();

  //singleton
  private static AppDatabase INSTANCE;

  public static AppDatabase getDatabase(final Context context) {
    if (INSTANCE == null) {
      synchronized (AppDatabase.class) {
        if (INSTANCE == null) {
          // Create database here
          INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
              AppDatabase.class,   "howTheyLearn_database")
              .build();
        }
      }
    }
    return INSTANCE;
  }

}
