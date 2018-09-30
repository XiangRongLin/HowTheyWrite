package com.kaiserpudding.howtheywrite.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
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

  public static AppDatabase getDatabase(final Context context) {
    if (INSTANCE == null) {
      synchronized (AppDatabase.class) {
        if (INSTANCE == null) {
          // Create database here
          INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
              AppDatabase.class,   "howTheyLearn_database")
              .addCallback(callback)
              .build();
        }
      }
    }
    return INSTANCE;
  }

  /**
   * Override the onOpen method to populate the database.
   * For this sample, we clear the database every time it is created or opened.
   */
  private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {

    @Override
    public void onOpen(@NonNull SupportSQLiteDatabase db) {
      super.onOpen(db);
    }

    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
      super.onCreate(db);

    }
  };

  private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

    private final LessonDao lessonDao;
    private final CharacterDao characterDao;
    private final LessonCharacterJoinDao lessonCharacterJoinDao;

    PopulateDbAsyncTask(AppDatabase db) {
      lessonDao = db.lessonDao();
      characterDao = db.characterDao();
      lessonCharacterJoinDao = db.lessonCharacterJoinJoinDao();
    }

    @Override
    protected Void doInBackground(final Void... params) {
      Character character = new Character(null, "あ", "a", null, false);
      int characterId = (int) characterDao.insertCharacter(character);
      Lesson lesson = new Lesson("basics");
      int lessonId = (int) lessonDao.insertLesson(lesson);
      LessonCharacterJoin lessonCharacterJoin = new LessonCharacterJoin(lessonId, characterId);
      lessonCharacterJoinDao.insertLessonCharacterJoin(lessonCharacterJoin);
      Character character2 = new Character(null, "お", "o", null, false);
      int characterId2 = (int) characterDao.insertCharacter(character2);
      LessonCharacterJoin lessonCharacterJoin2 = new LessonCharacterJoin(lessonId, characterId2);
      lessonCharacterJoinDao.insertLessonCharacterJoin(lessonCharacterJoin2);
      return null;
    }
  }

}

