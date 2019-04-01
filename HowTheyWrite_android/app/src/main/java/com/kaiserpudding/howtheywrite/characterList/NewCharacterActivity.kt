package com.kaiserpudding.howtheywrite.characterList

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.kaiserpudding.howtheywrite.R

class NewCharacterActivity : AppCompatActivity() {

    private var newCharHanziView: EditText? = null
    private var newCharHiraganaView: EditText? = null
    private var newCharTranslationView: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_character)
        newCharHanziView = findViewById(R.id.editNewCharHanzi)
        newCharHiraganaView = findViewById(R.id.editNewCharPinyin)
        newCharTranslationView = findViewById(R.id.editNewCharTranslation)

        val button = findViewById<Button>(R.id.button_save_new_char)
        button.setOnClickListener { view ->
            if (checkEditText()) {
                val replyIntent = Intent()
                val hanzi = newCharHanziView!!.text.toString()
                val hiragana = newCharHiraganaView!!.text.toString()
                val translation = newCharTranslationView!!.text.toString()
                replyIntent.putExtra(REPLY_CHAR_HANZI, hanzi)
                replyIntent.putExtra(REPLY_CHAR_HIRAGANA, hiragana)
                replyIntent.putExtra(REPLY_CHAR_TRANSLATION, translation)
                setResult(Activity.RESULT_OK, replyIntent)
                finish()
            }
        }
    }

    /**
     * Checks if the input is correct and displays an error message accordingly if not
     */
    private fun checkEditText(): Boolean {
        return true
        /* if (TextUtils.isEmpty(newCharHanziView.getText())
        || !KanaConverter.isJapanese(newCharHanziView.getText().toString())) {
      newCharHanziView.setError(getResources().getString(R.string.error_kanji_syntax));
      return false;
    } else {
      return true;
    }*/
    }

    companion object {

        val REPLY_CHAR_HANZI = "howTheyWrite.ADD_CHAR_HANZI"
        val REPLY_CHAR_HIRAGANA = "howTheyWrite.ADD_CHAR_PINYIN"
        val REPLY_CHAR_TRANSLATION = "howTheyWrite.ADD_CHAR_TRANSLATION"
    }
}
