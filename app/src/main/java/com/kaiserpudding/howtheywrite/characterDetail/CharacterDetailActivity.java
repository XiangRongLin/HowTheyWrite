package com.kaiserpudding.howtheywrite.characterDetail;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.kaiserpudding.howtheywrite.R;
import com.kaiserpudding.howtheywrite.model.Character;

public class CharacterDetailActivity extends AppCompatActivity {

  public static String REPLY_CHARACTER_ID = "howTheyWrite.CHARACTER_ID";

  private TextView kanjiTextView;
  private TextView hiraganaTextView;
  private TextView translationTextView;

  private CharacterDetailViewModel characterDetailViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_character_detail);

    //TODO proper handling for 0
    int characterId = getIntent().getIntExtra(REPLY_CHARACTER_ID, 0);

    //TODO proper fix for racing condition of initChar and getChar
    characterDetailViewModel = ViewModelProviders.of(
        this, new CharacterDetailViewModelFactory(getApplication(), characterId))
        .get(CharacterDetailViewModel.class);
    Character character = null ;

    while (character == null) {
      character = characterDetailViewModel.getCharacter();
    }

    kanjiTextView = findViewById(R.id.kanji);
    kanjiTextView.setText(character.getKanji());
    hiraganaTextView = findViewById(R.id.hiragana);
    hiraganaTextView.setText(character.getHiragana());
    translationTextView = findViewById(R.id.translation);
    if (character.getTranslationKey() != null) {
      translationTextView.setText(
          getResources().getIdentifier(
              character.getTranslationKey(),
              "string",
              getApplicationContext().getPackageName()));
    } else {
      translationTextView.setText(character.getTranslation());
    }
  }
}
