package com.kaiserpudding.howtheywrite.lessonList;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.kaiserpudding.howtheywrite.R;
import com.kaiserpudding.howtheywrite.model.Lesson;

public class LessonListActivity extends AppCompatActivity {

  private static final int NEW_LESSON_ACTIVITY_REQUEST_CODE = 1;

  private LessonListViewModel lessonListViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_lesson_list);

    RecyclerView recyclerView = findViewById(R.id.lesson_recyclerview);
    final LessonListAdapter adapter = new LessonListAdapter(this);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    lessonListViewModel = ViewModelProviders.of(this).get(LessonListViewModel.class);
    lessonListViewModel.getLessons().observe(this, adapter::setLessons);

    FloatingActionButton fab = findViewById(R.id.add_lessen_fab);
    fab.setOnClickListener(v -> {
      Intent intent = new Intent(LessonListActivity.this, NewLessonActivity.class);
      startActivityForResult(intent, NEW_LESSON_ACTIVITY_REQUEST_CODE);
    });
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == NEW_LESSON_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
      Lesson lesson = new Lesson(data.getStringExtra(NewLessonActivity.EXTRA_REPLY));
      lessonListViewModel.insertLesson(lesson);
    }
  }

}
