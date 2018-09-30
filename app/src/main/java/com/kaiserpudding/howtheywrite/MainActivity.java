package com.kaiserpudding.howtheywrite;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import com.kaiserpudding.howtheywrite.Adapter.LessonsListAdapter;
import com.kaiserpudding.howtheywrite.database.AppDatabase;
import com.kaiserpudding.howtheywrite.model.Character;
import com.kaiserpudding.howtheywrite.model.Lesson;
import com.kaiserpudding.howtheywrite.viewmodel.CharacterViewModel;
import com.kaiserpudding.howtheywrite.viewmodel.LessonViewModel;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

  private LessonViewModel lessonViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    RecyclerView recyclerView = findViewById(R.id.recyclerview);
    final LessonsListAdapter adapter = new LessonsListAdapter(this);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    lessonViewModel = ViewModelProviders.of(this).get(LessonViewModel.class);

    lessonViewModel.getLessons().observe(this, new Observer<List<Lesson>>() {
      @Override
      public void onChanged(@Nullable List<Lesson> lessons) {
        adapter.setLessons(lessons);
      }
    });

    FloatingActionButton fab = findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, NewCharacterActivity.class);
        startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
      }
    });
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
      String a = data.getStringExtra(NewCharacterActivity.EXTRA_REPLY);
      Character character = new Character(null, a, "a", null, false);
    } else {
      Toast.makeText(getApplicationContext(),
          R.string.empty_not_saved,
          Toast.LENGTH_LONG).show();
    }
  }
}
