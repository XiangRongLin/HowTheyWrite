package com.kaiserpudding.howtheywrite.lessonList

import android.app.Activity
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.repositories.LessonRepository

class NewLessonActivity : AppCompatActivity() {

    private var editLessonView: EditText? = null
    private var lessonListViewModel: LessonListViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_lesson)
        editLessonView = findViewById(R.id.edit_word)

        lessonListViewModel = ViewModelProviders.of(this).get(LessonListViewModel::class.java)

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
        } else if (lessonListViewModel!!.lessonNames!!.contains(editLessonView!!.text.toString())) {
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
