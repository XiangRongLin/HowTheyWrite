package com.kaiserpudding.howtheywrite.characterList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import com.kaiserpudding.howtheywrite.R;
import com.kaiserpudding.howtheywrite.util.KanaConverter;

public class NewCharacterActivity extends AppCompatActivity {

  public static final String REPLY_CHAR_KANJI = "howTheyWrite.ADD_CHAR_KANJI";
  public static final String REPLY_CHAR_HIRAGANA = "howTheyWrite.ADD_CHAR_HIRAGANA";
  public static final String REPLY_CHAR_TRANSLATION = "howTheyWrite.ADD_CHAR_TRANSLATION";

  private EditText newCharKanjiView;
  private EditText newCharHiraganaView;
  private EditText newCharTranslationView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_new_character);
    newCharKanjiView = findViewById(R.id.editNewCharKanji);
    newCharHiraganaView = findViewById(R.id.editNewCharHiragana);
    newCharTranslationView = findViewById(R.id.editNewCharTranslation);

    final Button button = findViewById(R.id.button_save_new_char);
    button.setOnClickListener(view -> {
      if (checkEditText()) {
        Intent replyIntent = new Intent();
        String kanji = newCharKanjiView.getText().toString();
        String hiragana = newCharHiraganaView.getText().toString();
        String translation = newCharTranslationView.getText().toString();
        replyIntent.putExtra(REPLY_CHAR_KANJI, kanji);
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
    if (TextUtils.isEmpty(newCharKanjiView.getText())
        || !KanaConverter.isJapanese(newCharKanjiView.getText().toString())) {
      newCharKanjiView.setError(getResources().getString(R.string.error_kanji_syntax));
      return false;
    } else if (TextUtils.isEmpty(newCharHiraganaView.getText())
        || !KanaConverter.isKana(newCharHiraganaView.getText().toString())) {
      newCharHiraganaView.setError(getResources().getString(R.string.error_kana_sytanx));
      return false;
    } else {
      return true;
    }
  }
}
