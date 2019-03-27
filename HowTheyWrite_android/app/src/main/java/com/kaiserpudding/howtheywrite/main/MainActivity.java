package com.kaiserpudding.howtheywrite.main;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.kaiserpudding.howtheywrite.characterList.CharacterListActivity;
import com.kaiserpudding.howtheywrite.R;
import com.kaiserpudding.howtheywrite.database.ChineseDbHelper;
import com.kaiserpudding.howtheywrite.lessonList.LessonListActivity;
import com.kaiserpudding.howtheywrite.lessonList.LessonListViewModel;

public class MainActivity extends AppCompatActivity {

  private LessonListViewModel lessonViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    DatabaseInitializer databaseInitializer = new DatabaseInitializer();
    databaseInitializer.execute(this);
  }

  public void toLessons(View view) {
    Intent intent = new Intent(this, LessonListActivity.class);
    startActivity(intent);
  }

  public void toCharacters(View view) {
    Intent intent = new Intent(this, CharacterListActivity.class);
    startActivity(intent);
  }

  private class DatabaseInitializer extends AsyncTask<Context, Void, Void> {

    @Override
    protected Void doInBackground(Context... contexts) {
      ChineseDbHelper a = new ChineseDbHelper(contexts[0]);
      a.getReadableDatabase();
      return null;
    }
  }

}
