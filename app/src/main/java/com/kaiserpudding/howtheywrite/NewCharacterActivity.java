package com.kaiserpudding.howtheywrite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NewCharacterActivity extends AppCompatActivity {

  public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

  private EditText editCharacterView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_new_character);
    editCharacterView = findViewById(R.id.edit_word);

    final Button button = findViewById(R.id.button_save);
    button.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent replyIntent = new Intent();
        if(TextUtils.isEmpty((editCharacterView.getText()))) {
          setResult(RESULT_CANCELED, replyIntent);
        } else {
          String word = editCharacterView.getText().toString();
          replyIntent.putExtra(EXTRA_REPLY, word);
          setResult(RESULT_OK, replyIntent);
        }
        finish();
      }
    });
  }
}
