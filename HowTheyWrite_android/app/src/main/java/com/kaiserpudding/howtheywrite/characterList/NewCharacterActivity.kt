package com.kaiserpudding.howtheywrite.characterList

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import com.kaiserpudding.howtheywrite.R

class NewCharacterActivity : AppCompatActivity() {

    private var newCharHanziView: EditText? = null
    private var newCharPinyinView: EditText? = null
    private var newCharTranslationView: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_character)
        newCharHanziView = findViewById(R.id.editNewCharHanzi)
        newCharPinyinView = findViewById(R.id.editNewCharPinyin)
        newCharTranslationView = findViewById(R.id.editNewCharTranslation)

        val button = findViewById<Button>(R.id.button_save_new_char)
        button.setOnClickListener { view ->
            if (checkEditText()) {
                val replyIntent = Intent()
                val hanzi = newCharHanziView!!.text.toString()
                val pinyin = newCharPinyinView!!.text.toString()
                val translation = newCharTranslationView!!.text.toString()
                replyIntent.putExtra(REPLY_CHAR_HANZI, hanzi)
                replyIntent.putExtra(REPLY_CHAR_PINYIN, pinyin)
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
        val REPLY_CHAR_PINYIN = "howTheyWrite.ADD_CHAR_PINYIN"
        val REPLY_CHAR_TRANSLATION = "howTheyWrite.ADD_CHAR_TRANSLATION"
    }
}
