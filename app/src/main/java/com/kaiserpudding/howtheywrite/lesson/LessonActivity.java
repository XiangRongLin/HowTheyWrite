package com.kaiserpudding.howtheywrite.lesson;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import com.kaiserpudding.howtheywrite.R;
import com.kaiserpudding.howtheywrite.model.Lesson;
import java.util.List;

public class LessonActivity extends AppCompatActivity {

  private static final int NEW_LESSON_ACTIVITY_REQUEST_CODE = 1;

  private LessonViewModel lessonViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_lesson);

    RecyclerView recyclerView = findViewById(R.id.recyclerview);
    final LessonListAdapter adapter = new LessonListAdapter(this);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    lessonViewModel = ViewModelProviders.of(this).get(LessonViewModel.class);
    LiveData<List<Lesson>> a = lessonViewModel.getLessons();
    a.observe(this, adapter::setLessons);

    FloatingActionButton fab = findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(LessonActivity.this, NewLessonActivity.class);
        startActivityForResult(intent, NEW_LESSON_ACTIVITY_REQUEST_CODE);
      }
    });
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == NEW_LESSON_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
      Lesson lesson = new Lesson(data.getStringExtra(NewLessonActivity.EXTRA_REPLY));
      lessonViewModel.insertLesson(lesson);
    } else {
      Toast.makeText(
          getApplicationContext(),
          R.string.empty_not_saved,
          Toast.LENGTH_LONG).show();
    }
  }

  public void a () {
    Toast.makeText(this, "aaa", Toast.LENGTH_LONG);
  }
  public void b () {
    Toast.makeText(this, "bbb", Toast.LENGTH_LONG);
  }

}
