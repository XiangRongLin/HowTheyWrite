package com.kaiserpudding.howtheywrite.main

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.characterList.CharacterListActivity
import com.kaiserpudding.howtheywrite.database.ChineseDbHelper
import com.kaiserpudding.howtheywrite.lesson.LessonActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val databaseInitializer = DatabaseInitializer()
        databaseInitializer.execute(this)
    }

    fun toLessons(view: View) {
        val intent = Intent(this, LessonActivity::class.java)
        startActivity(intent)
    }

    fun toCharacters(view: View) {
        val intent = Intent(this, CharacterListActivity::class.java)
        startActivity(intent)
    }

    private class DatabaseInitializer : AsyncTask<Context, Void, Void>() {

        override fun doInBackground(vararg contexts: Context): Void? {
            val a = ChineseDbHelper(contexts[0])
            a.readableDatabase
            return null
        }
    }

}
