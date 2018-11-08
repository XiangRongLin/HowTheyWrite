package com.kaiserpudding.howtheywrite.characterList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.kaiserpudding.howtheywrite.R;

public class NewCharacterActivity extends AppCompatActivity {

  public static final String REPLY_CHAR_HANZI = "howTheyWrite.ADD_CHAR_HANZI";
  public static final String REPLY_CHAR_HIRAGANA = "howTheyWrite.ADD_CHAR_PINYIN";
  public static final String REPLY_CHAR_TRANSLATION = "howTheyWrite.ADD_CHAR_TRANSLATION";

  private EditText newCharHanziView;
  private EditText newCharHiraganaView;
  private EditText newCharTranslationView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_new_character);
    newCharHanziView = findViewById(R.id.editNewCharHanzi);
    newCharHiraganaView = findViewById(R.id.editNewCharPinyin);
    newCharTranslationView = findViewById(R.id.editNewCharTranslation);

    final Button button = findViewById(R.id.button_save_new_char);
    button.setOnClickListener(view -> {
      if (checkEditText()) {
        Intent replyIntent = new Intent();
        String hanzi = newCharHanziView.getText().toString();
        String hiragana = newCharHiraganaView.getText().toString();
        String translation = newCharTranslationView.getText().toString();
        replyIntent.putExtra(REPLY_CHAR_HANZI, hanzi);
        replyIntent.putExtra(REPLY_CHAR_HIRAGANA, hiragana);
        replyIntent.putExtra(REPLY_CHAR_TRANSLATION, translation);
        setResult(RESULT_OK, replyIntent);
        finish();
      }
    } );
  }

  /**
   * Checks if the input is correct and displays an error message accordingly if not
   */
  private boolean checkEditText() {
    return true;
   /* if (TextUtils.isEmpty(newCharHanziView.getText())
        || !KanaConverter.isJapanese(newCharHanziView.getText().toString())) {
      newCharHanziView.setError(getResources().getString(R.string.error_kanji_syntax));
      return false;
    } else if (TextUtils.isEmpty(newCharHiraganaView.getText())
        || !KanaConverter.isKana(newCharHiraganaView.getText().toString())) {
      newCharHiraganaView.setError(getResources().getString(R.string.error_kana_syntanx));
      return false;
    } else {
      return true;
    }*/
  }
}
