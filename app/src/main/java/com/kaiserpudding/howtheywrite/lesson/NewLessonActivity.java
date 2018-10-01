package com.kaiserpudding.howtheywrite.lesson;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.kaiserpudding.howtheywrite.R;

public class NewLessonActivity extends AppCompatActivity {

  public static final String EXTRA_REPLY = "howTheyWrite.ADD_LESSON";

  private EditText editLessonView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_new_lesson);
    editLessonView = findViewById(R.id.edit_word);

    final Button button = findViewById(R.id.button_save);
    button.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
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
}
