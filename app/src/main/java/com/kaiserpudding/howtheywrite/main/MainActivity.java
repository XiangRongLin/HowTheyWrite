package com.kaiserpudding.howtheywrite.main;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.kaiserpudding.howtheywrite.characterList.CharacterListActivity;
import com.kaiserpudding.howtheywrite.R;
import com.kaiserpudding.howtheywrite.database.AppDatabase;
import com.kaiserpudding.howtheywrite.database.CharacterDao;
import com.kaiserpudding.howtheywrite.database.LessonCharacterJoinDao;
import com.kaiserpudding.howtheywrite.database.LessonDao;
import com.kaiserpudding.howtheywrite.lessonList.LessonListActivity;
import com.kaiserpudding.howtheywrite.lessonList.LessonListViewModel;
import com.kaiserpudding.howtheywrite.model.Character;
import com.kaiserpudding.howtheywrite.model.Lesson;
import com.kaiserpudding.howtheywrite.model.LessonCharacterJoin;
import com.kaiserpudding.howtheywrite.quiz.QuizActivity;

public class MainActivity extends AppCompatActivity {

  private LessonListViewModel lessonViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  public void toLessons(View view) {
    Intent intent = new Intent(this, LessonListActivity.class);
    startActivity(intent);
  }

  public void toCharacters(View view) {
    Intent intent = new Intent(this, CharacterListActivity.class);
    startActivity(intent);
  }


}
