package com.kaiserpudding.howtheywrite.lesson

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.kaiserpudding.howtheywrite.R

class NewLessonActivity : AppCompatActivity() {

    private var editLessonView: EditText? = null
    private var lessonViewModel: LessonViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_lesson)
        editLessonView = findViewById(R.id.edit_word)

        lessonViewModel = ViewModelProviders.of(this).get(LessonViewModel::class.java)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener { view ->
            if (checkEditText()) {
                val replyIntent = Intent()
                if (TextUtils.isEmpty(editLessonView!!.text)) {
                    setResult(Activity.RESULT_CANCELED, replyIntent)
                } else {
                    val lessonName = editLessonView!!.text.toString()
                    replyIntent.putExtra(EXTRA_REPLY, lessonName)
                    setResult(Activity.RESULT_OK, replyIntent)
                }
                finish()
            }
        }
    }

    /**
     * Checks if the input is not empty and not no other lesson with the same name already exists
     * and displays an error message accordingly if not
     */
    private fun checkEditText(): Boolean {
        if (TextUtils.isEmpty(editLessonView!!.text)) {
            editLessonView!!.error = resources.getString(R.string.error_empty_field)
            return false
        } else if (lessonViewModel!!.lessonNames!!.contains(editLessonView!!.text.toString())) {
            editLessonView!!.error = resources.getString(R.string.error_duplicate_lesson_name)
            return false
        } else {
            return true
        }
    }

    companion object {

        val EXTRA_REPLY = "howTheyWrite.ADD_LESSON"
    }
}
