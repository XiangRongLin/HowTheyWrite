package com.kaiserpudding.howtheywrite.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.kaiserpudding.howtheywrite.model.HiraganaWord;
import com.kaiserpudding.howtheywrite.model.KanjiWord;
import com.kaiserpudding.howtheywrite.model.KatakanaWord;
import com.kaiserpudding.howtheywrite.model.Lesson;
import com.kaiserpudding.howtheywrite.model.User;
import com.kaiserpudding.howtheywrite.model.Word;

@Database(entities = {Word.class,
    HiraganaWord.class,
    KatakanaWord.class,
    KanjiWord.class,
    Lesson.class,
    User.class},
    version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

  public abstract HiraganaWordDao hiraganaWordDao();
  public abstract KatakanaWordDao katakanaWordDao();
  public abstract KanjiWordDao kanjiWordDao();
  public abstract UserDao userDao();

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
