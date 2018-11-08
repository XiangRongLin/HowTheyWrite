package com.kaiserpudding.howtheywrite.quiz;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.kaiserpudding.howtheywrite.R;
import com.kaiserpudding.howtheywrite.lessonDetail.LessonDetailActivity;
import com.kaiserpudding.howtheywrite.model.Character;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

  private QuizViewModel quizViewModel;
  private TextView quizWord;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quiz);

    //TODO handling for 0
    int lessonId = getIntent().getIntExtra(LessonDetailActivity.REPLY_LESSON_ID, 0);

    quizViewModel = ViewModelProviders.of(
        this, new QuizViewModelFactory(getApplication(), lessonId)).
        get(QuizViewModel.class);

    List<Character> a = null;
    while (a == null) {
      a = quizViewModel.getCharacters();
    }
    quizViewModel.randomizeList();
    setQuizWord(quizViewModel.getNextWord());

    EditText quizInput = findViewById(R.id.quiz_input_edit_text);
    quizInput.setOnEditorActionListener((textView, i, keyEvent) -> {
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
    });
  }

  private void setQuizWord(Character character) {
    quizWord = findViewById(R.id.quiz_word);
    quizWord.setText(character.getHiragana());
  }
}
