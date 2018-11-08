//package com.kaiserpudding.howtheywrite.quiz;
//
//import android.content.Intent;
//import android.support.design.widget.FloatingActionButton;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.widget.TextView;
//import com.kaiserpudding.howtheywrite.CharacterList.NewCharacterActivity;
//import com.kaiserpudding.howtheywrite.R;
//import com.kaiserpudding.howtheywrite.lessonList.LessonListAdapter;
//import com.kaiserpudding.howtheywrite.model.Lesson;
//
//public class LessonActivity extends AppCompatActivity {
//
//  private static final int NEW_CHARACTER_ACTIVITY_REQUEST_CODE = 1;
//
//  private LessonViewModel lessonViewModel;
//
//  @Override
//  protected void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//    setContentView(R.layout.activity_lesson);
//
//    Intent intent = getIntent();
//    int lessonId = intent.getIntExtra(LessonListAdapter.SELECTED_LESSON_ID, -1);
//
//    if(lessonId == -1) {
//      //TODO show error screen and return to list
//    }
//
//    RecyclerView recyclerView = findViewById(R.id.character_recyclerview);
//    final CharacterListAdapter adapter = new CharacterListAdapter(this);
//    recyclerView.setAdapter(adapter);
//    recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
//
//    FloatingActionButton fab = findViewById(R.id.add_character_fab);
//    fab.setOnClickListener(view -> {
//      Intent intent = new Intent(LessonActivity.this, NewCharacterActivity.class);
//      startActivityForResult(intent, NEW_CHARACTER_ACTIVITY_REQUEST_CODE);
//    });
//  }
//
//  private void setTextView(Lesson lesson) {
//    if (lesson != null) {
//      TextView textView = findViewById(R.id.lessonId);
//      textView.setText(lesson.toString());
//    } else {
////      TextView textView = findViewById(R.id.lessonId);
////      textView.setText("failed");
//    }
//  }
//}
