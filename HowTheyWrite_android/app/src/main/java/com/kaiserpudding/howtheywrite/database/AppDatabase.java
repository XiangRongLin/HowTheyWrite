package com.kaiserpudding.howtheywrite.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import com.kaiserpudding.howtheywrite.database.dao.CharacterDao;
import com.kaiserpudding.howtheywrite.database.dao.LessonCharacterJoinDao;
import com.kaiserpudding.howtheywrite.database.dao.LessonDao;
import com.kaiserpudding.howtheywrite.model.Character;
import com.kaiserpudding.howtheywrite.model.Lesson;
import com.kaiserpudding.howtheywrite.model.LessonCharacterJoin;


@Database(entities = {
    Character.class,
    Lesson.class,
    LessonCharacterJoin.class,},
    version = 2, exportSchema = false)
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
              //.addCallback(callback)
              .fallbackToDestructiveMigration()
              .build();
        }
      }
    }
    return INSTANCE;
  }

}

