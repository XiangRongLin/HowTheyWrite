package com.kaiserpudding.howtheywrite.quiz

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.characterDetail.CharacterDetailActivity
import com.kaiserpudding.howtheywrite.characterList.CharacterListActivity
import com.kaiserpudding.howtheywrite.lessonDetail.LessonDetailActivity
import com.kaiserpudding.howtheywrite.model.Character

class QuizActivity : AppCompatActivity() {

    private var quizViewModel: QuizViewModel? = null
    private var quizTranslation: TextView? = null
    private var quizPinyin: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        //TODO handling for 0
        val lessonId = intent.getIntExtra(LessonDetailActivity.REPLY_LESSON_ID, 0)

        quizViewModel = ViewModelProviders.of(
                this, QuizViewModelFactory(application, lessonId)).get(QuizViewModel::class.java)

        quizTranslation = findViewById(R.id.quiz_translation)
        quizPinyin = findViewById(R.id.quiz_pinyin)
        quizTranslation!!.setOnClickListener { v ->
            val intent = Intent(this@QuizActivity, CharacterDetailActivity::class.java)
            intent.putExtra(CharacterDetailActivity.REPLY_CHARACTER_ID, quizViewModel!!.currentWord.id)
            startActivity(intent)
        }

        //TODO fix while
        var a: List<Character>? = null
        while (a == null) {
            a = quizViewModel!!.characters
        }
        quizViewModel!!.randomizeList()
        setQuizWord(quizViewModel!!.nextWord!!)

        val quizInput = findViewById<EditText>(R.id.quiz_input_edit_text)
        //if user clicks enter check if input is correct and show next word if it is, otherwise display error
        quizInput.setOnEditorActionListener { textView, i, keyEvent ->
            val b = quizInput.text != null
            if (quizInput.text != null && quizInput.text.toString() == quizViewModel!!.currentWord.hanzi) {
                val nextCharacter = quizViewModel!!.nextWord
                if (nextCharacter != null) {
                    setQuizWord(nextCharacter)
                    quizInput.setText("")

                } else {
                    //TODO show result screen
                    finish()
                }
            } else {
                quizInput.setText("")
            }
            true
        }
    }

    private fun setQuizWord(character: Character) {
        if (character.translationKey == null) {
            quizTranslation!!.text = character.translation
        } else {
            quizTranslation!!.setText(
                    resources.getIdentifier(
                            character.translationKey,
                            "string",
                            applicationContext.packageName
                    )
            )
        }
        quizPinyin!!.text = character.pinyin
    }
}
