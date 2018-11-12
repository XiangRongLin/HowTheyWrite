package com.kaiserpudding.howtheywrite.quiz;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import com.kaiserpudding.howtheywrite.R;
import com.kaiserpudding.howtheywrite.characterDetail.CharacterDetailActivity;
import com.kaiserpudding.howtheywrite.characterList.CharacterListActivity;
import com.kaiserpudding.howtheywrite.lessonDetail.LessonDetailActivity;
import com.kaiserpudding.howtheywrite.model.Character;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

  private QuizViewModel quizViewModel;
  private TextView quizTranslation;
  private TextView quizPinyin;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quiz);

    //TODO handling for 0
    int lessonId = getIntent().getIntExtra(LessonDetailActivity.REPLY_LESSON_ID, 0);

    quizViewModel = ViewModelProviders.of(
        this, new QuizViewModelFactory(getApplication(), lessonId)).
        get(QuizViewModel.class);

    quizTranslation = findViewById(R.id.quiz_translation);
    quizPinyin = findViewById(R.id.quiz_pinyin);
    quizTranslation.setOnClickListener(v -> {
      Intent intent = new Intent(QuizActivity.this, CharacterDetailActivity.class);
      intent.putExtra(CharacterDetailActivity.REPLY_CHARACTER_ID, quizViewModel.getCurrentWord().getId());
      startActivity(intent);
    });

    //TODO fix while
    List<Character> a = null;
    while (a == null) {
      a = quizViewModel.getCharacters();
    }
    quizViewModel.randomizeList();
    setQuizWord(quizViewModel.getNextWord());

    EditText quizInput = findViewById(R.id.quiz_input_edit_text);
    //if user clicks enter check if input is correct and show next word if it is, otherwise display error
    quizInput.setOnEditorActionListener((textView, i, keyEvent) -> {
      if (quizInput.getText() != null
          && quizInput.getText().toString().equals(quizViewModel.getCurrentWord().getHanzi())) {
        Character nextCharacter = quizViewModel.getNextWord();
        if (nextCharacter != null) {
          setQuizWord(nextCharacter);
          quizInput.setText("");
          return true;
        } else {
          //TODO show result screen
          finish();
          return true;
        }
      } else {
        quizInput.setError(getResources().getString(R.string.error_wrong_hanzi));
        return true;
      }
    });
  }

  private void setQuizWord(Character character) {
    if (character.getTranslationKey() == null) {
      quizTranslation.setText(character.getTranslation());
    } else {
     quizTranslation.setText(
         getResources().getIdentifier(
             character.getTranslationKey(),
             "string",
             getApplicationContext().getPackageName()
         )
     );
    }
    quizPinyin.setText(character.getPinyin());
  }
}
