package com.kaiserpudding.howtheywrite.lessonDetail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import com.kaiserpudding.howtheywrite.R;
import com.kaiserpudding.howtheywrite.quiz.QuizActivity;

public class LessonDetailActivity extends AppCompatActivity {

  public static String REPLY_LESSON_ID = "howTheyWrite.LESSON_ID";

  private LessonDetailViewModel lessonDetailViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_lesson_detail);

    RecyclerView recyclerView = findViewById(R.id.character_recyclerview);
    final LessonDetailAdapter adapter = new LessonDetailAdapter(this);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new GridLayoutManager(this, 5));

    //TODO proper handling for 0
    int lessonId = getIntent().getIntExtra(REPLY_LESSON_ID, 0);

    //TODO proper fix for racing condition of initChar and getChar
    lessonDetailViewModel = ViewModelProviders.of(
        this, new LessonDetailViewModelFactory(getApplication(), lessonId))
        .get(LessonDetailViewModel.class);
    lessonDetailViewModel.getCharacters().observe(this, adapter::setCharacters);

    Button toQuiz = findViewById(R.id.to_quiz_button);
    toQuiz.setOnClickListener(view -> {
      Intent intent = new Intent(view.getContext(), QuizActivity.class);
      intent.putExtra(REPLY_LESSON_ID, lessonId);
      startActivity(intent);
    });

  }
}
