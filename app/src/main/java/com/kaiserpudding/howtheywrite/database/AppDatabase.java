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
              AppDatabase.class,   "howTheyLearn_database_jp")
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
      new PopulateDbAsyncTask(INSTANCE).execute();
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
    protected Void doInBackground(Void... voids) {
      String[][][] characters = {
          {
            {"我", "wo", "w_Me"},
            {"你", "ni", "w_You"},
            {"他", "ta1", "w_He"},
            {"她", "ta2", "w_She"},
            {"它", "ta3", "w_It"},
            }//, {
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            }
          };

      String[] lessonNames = {
          "1"
//          "", "", "", "", "",
      };
      for (int i = 0; i < lessonNames.length; i++) {
        Lesson lesson = new Lesson(lessonNames[i]);
        int lessonId = (int) lessonDao.insertLesson(lesson);
        for (int j = 0; j < characters[i].length; j++) {
          Character character = new Character(
              characters[i][j][0],
              characters[i][j][1],
              characters[i][j][2],
              null,
              false
          );
          int characterId = (int) characterDao.insertCharacter(character);
          LessonCharacterJoin lessonCharacterJoin = new LessonCharacterJoin(lessonId, characterId);
          lessonCharacterJoinDao.insertLessonCharacterJoin(lessonCharacterJoin);
        }
      }
      return null;
    }


    /*@Override
    protected Void doInBackground(final Void... params) {
        String[][][] characters = {
            {
                {"私", "わたし", "w_me"},
                {"人", "じん", "w_nationality_suffix"},
                {"先生", "せんせい", "w_teacher_not_yourself"},
                {"教師", "きょうし", "w_teacher"},
                {"学生", "がくせい", "w_student"},
                {"会社員", "かいしゃいん", "w_employee"},
                {"社員", "しゃいん", "w_employee_of"},
                {"銀行員", "ぎんこういん", "w_banker"},
                {"医者", "いしゃ", "w_doctor"},
                {"研究者", "けんきゅうしゃ", "w_researcher"},
                {"大学", "だいがく", "w_university"},
                {"病院", "びょういん", "w_hospital"},
                {"歳", "さい", "w_years_age"},
            }, {
            {"本", "ほん", "w_book"},
            {" 辞書", "じしょ", "w_dictionary"},}
//            {"", "", "w_magizine"},
//            {"", "", "w_newspaper"},
//            {"", "", "w_notebook"},
//            {"", "", "w_business_card"},
//            {"", "", "w_pencil"},
//            {"", "", "w_clock"},
//            {"", "", "w_umbrella"},
//            {"", "", "w_car"},
//            {"", "", "w_table"},
//            {"", "", "w_souvenir"},
//            {"", "", "w_english"},
//            {"", "", "w_japanese"},
//            {"", "", "w_language_suffix"},
//            {"", "", "w_what"},
//            }, {
//            {"", "", "w_classroom"},
//            {"", "", "w_canteen"},
//            {"", "", "w_office"},
//            {"", "", "w_conference_room"},
//            {"", "", "w_reception"},
//            {"", "", "w_room"},
//            {"", "", "w_toilet"},
//            {"", "", "w_staris"},
//            {"", "", "w_vending_maschine"},
//            {"", "", "w_phone"},
//            {"", "", "w_country"},
//            {"", "", "w_company"},
//            {"", "", "w_shoe"},
//            {"", "", "w_department_mall"},
//            {"", "", "w_basement"},
//            {"", "", "w_floor_level"},
//            {"", "", "w_yen"},
//            {"", "", "w_hundred"},
//            {"", "", "w_thousand"},
//            {"", "", "w_ten_thousand"},
//            }, {
//            {"", "", "w_wake_up"},
//            {"", "", "w_sleep"},
//            {"", "", "w_work"},
//            {"", "", "w_break"},
//            {"", "", "w_study"},
//            {"", "", "w_end_verb"},
//            {"", "", "w_bank"},
//            {"", "", "w_post_office"},
//            {"", "", "w_library"},
//            {"", "", "w_art_gallery"},
//            {"", "", "w_now"},
//            {"", "", "w_hour_time_suffix"},
//            {"", "", "w_minute_time_suffix"},
//            {"", "", "w_half"},
//            {"", "", "w_a_m"},
//            {"", "", "w_p_m"},
//            {"", "", "w_morning"},
//            {"", "", "w_noon"},
//            {"", "", "w_night"},
//            {"", "", "w_exam"},
//            {"", "", "w_conference"},
//            {"", "", "w_movie"},
//            {"", "", "w_every_prefix"},
//            {"", "", "w_monday"},
//            {"", "", "w_tuesday"},
//            {"", "", "w_wednesday"},
//            {"", "", "w_thursday"},
//            {"", "", "w_friday"},
//            {"", "", "w_saturday"},
//            {"", "", "w_sunday"},
//            }//, {
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            {"", "", "w_"},
//            }
        };

        String[] lectureName =
            {
                "1", "2"
//                "", "", "", "", "",
//                "", "", "", "", "",
//                "", "", "", "", "",
//                "", "", "", "", "",
            };
        for (int i = 0; i < lectureName.length; i++) {
          Lesson lesson = new Lesson(lectureName[i]);
          int lessonId = (int) lessonDao.insertLesson(lesson);
          for (int j = 0; j < characters[i].length; j++) {
            Character character = new Character(
                characters[i][j][0],
                characters[i][j][1],
                characters[i][j][2],
                null,
                false
             );
            int characterId = (int) characterDao.insertCharacter(character);
            LessonCharacterJoin lessonCharacterJoin = new LessonCharacterJoin(lessonId, characterId);
            lessonCharacterJoinDao.insertLessonCharacterJoin(lessonCharacterJoin);
          }
        }
      return null;
    }*/
  }

}

