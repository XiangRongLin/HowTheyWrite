package com.kaiserpudding.howtheywrite.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import com.kaiserpudding.howtheywrite.R;
import com.kaiserpudding.howtheywrite.model.Character;
import com.kaiserpudding.howtheywrite.model.Lesson;
import com.kaiserpudding.howtheywrite.model.LessonCharacterJoin;
import com.kaiserpudding.howtheywrite.model.Progress;


@Database(entities = {
    Character.class,
    Lesson.class,
    LessonCharacterJoin.class,},
    version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

  public abstract CharacterDao characterDao();
  public abstract LessonDao lessonDao();
  public abstract LessonCharacterJoinDao lessonCharacterJoinJoinDao();

  //singleton
  private static AppDatabase INSTANCE;

  private Context context;

  public static AppDatabase getDatabase(final Context context) {
    if (INSTANCE == null) {
      synchronized (AppDatabase.class) {
        if (INSTANCE == null) {
          // Create database here
          INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
              AppDatabase.class, "howTheyLearn_database_cn")
              .build();
        }
      }
    }
    return INSTANCE;
  }


}

