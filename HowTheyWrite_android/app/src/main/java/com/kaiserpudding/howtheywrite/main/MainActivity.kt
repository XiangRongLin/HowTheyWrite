package com.kaiserpudding.howtheywrite.main

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.kaiserpudding.howtheywrite.characterList.CharacterListActivity
import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.database.ChineseDbHelper
import com.kaiserpudding.howtheywrite.lessonList.LessonListActivity
import com.kaiserpudding.howtheywrite.lessonList.LessonListViewModel
import com.kaiserpudding.howtheywrite.quiz.QuizActivity

class MainActivity : AppCompatActivity() {

    private val lessonViewModel: LessonListViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val databaseInitializer = DatabaseInitializer()
        databaseInitializer.execute(this)
    }

    fun toLessons(view: View) {
        val intent = Intent(this, QuizActivity::class.java)
        startActivity(intent)
    }

    fun toCharacters(view: View) {
        val intent = Intent(this, CharacterListActivity::class.java)
        startActivity(intent)
    }

    private inner class DatabaseInitializer : AsyncTask<Context, Void, Void>() {

        override fun doInBackground(vararg contexts: Context): Void? {
            val a = ChineseDbHelper(contexts[0])
            a.readableDatabase
            return null
        }
    }

}
