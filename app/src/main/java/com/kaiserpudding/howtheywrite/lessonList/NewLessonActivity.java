package com.kaiserpudding.howtheywrite.lessonList;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import com.kaiserpudding.howtheywrite.R;
import com.kaiserpudding.howtheywrite.repositories.LessonRepository;

public class NewLessonActivity extends AppCompatActivity {

  public static final String EXTRA_REPLY = "howTheyWrite.ADD_LESSON";

  private EditText editLessonView;
  private LessonListViewModel lessonListViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_new_lesson);
    editLessonView = findViewById(R.id.edit_word);

    lessonListViewModel = ViewModelProviders.of(this).get(LessonListViewModel.class);

    final Button button = findViewById(R.id.button_save);
    button.setOnClickListener(view -> {
      if (checkEditText()) {
        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(editLessonView.getText())) {
          setResult(RESULT_CANCELED, replyIntent);
        } else {
          String lessonName = editLessonView.getText().toString();
          replyIntent.putExtra(EXTRA_REPLY, lessonName);
          setResult(RESULT_OK, replyIntent);
        }
        finish();
      }
    });
  }

  /**
   * Checks if the input is not empty and not no other lesson with the same name already exists
   * and displays an error message accordingly if not
   */
  private boolean checkEditText() {
    if (TextUtils.isEmpty(editLessonView.getText())) {
      editLessonView.setError(getResources().getString(R.string.error_empty_field));
      return false;
    } else if (lessonListViewModel.getLessonNames().contains(editLessonView.getText().toString())) {
      editLessonView.setError(getResources().getString(R.string.error_duplicate_lesson_name));
      return false;
    } else {
      return true;
    }
  }
}
