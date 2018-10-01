package com.kaiserpudding.howtheywrite.main;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.kaiserpudding.howtheywrite.R;
import com.kaiserpudding.howtheywrite.database.AppDatabase;
import com.kaiserpudding.howtheywrite.database.CharacterDao;
import com.kaiserpudding.howtheywrite.database.LessonCharacterJoinDao;
import com.kaiserpudding.howtheywrite.database.LessonDao;
import com.kaiserpudding.howtheywrite.lesson.LessonActivity;
import com.kaiserpudding.howtheywrite.lesson.LessonViewModel;
import com.kaiserpudding.howtheywrite.model.Character;
import com.kaiserpudding.howtheywrite.model.Lesson;
import com.kaiserpudding.howtheywrite.model.LessonCharacterJoin;

public class MainActivity extends AppCompatActivity {

  private LessonViewModel lessonViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  public void toLessons(View view) {
    Intent intent = new Intent(this, LessonActivity.class);
    startActivity(intent);
  }

  public void fillDatabase(View view) {
    new FillDbAsync(AppDatabase.getDatabase(this)).execute();
  }

  private static class FillDbAsync extends AsyncTask<Void, Void, Void> {

    private final LessonDao lessonDao;
    private final CharacterDao characterDao;
    private final LessonCharacterJoinDao lessonCharacterJoinDao;

    FillDbAsync(AppDatabase db) {
      lessonDao = db.lessonDao();
      characterDao = db.characterDao();
      lessonCharacterJoinDao = db.lessonCharacterJoinJoinDao();
    }

    @Override
    protected Void doInBackground(Void... voids) {
      lessonDao.insertLesson(new Lesson("abc"));
      lessonDao.insertLesson(new Lesson("def"));
      lessonDao.insertLesson(new Lesson("ghi"));
      characterDao.insertCharacter(new Character(null, "あ", "a", false));
      characterDao.insertCharacter(new Character(null, "お", "o", false));
      lessonCharacterJoinDao.insertLessonCharacterJoin(new LessonCharacterJoin(1,1));
      lessonCharacterJoinDao.insertLessonCharacterJoin(new LessonCharacterJoin(1, 2));
      return null;
    }
  }
}
