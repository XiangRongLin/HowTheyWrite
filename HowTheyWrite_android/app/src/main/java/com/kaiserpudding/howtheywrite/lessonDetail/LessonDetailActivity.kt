package com.kaiserpudding.howtheywrite.lessonDetail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.model.Character
import com.kaiserpudding.howtheywrite.quiz.QuizActivity

class LessonDetailActivity : AppCompatActivity() {

    private var lessonDetailViewModel: LessonDetailViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson_detail)

        val recyclerView = findViewById<RecyclerView>(R.id.character_recyclerview)
        val adapter = LessonDetailAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 5)

        //TODO proper handling for 0
        val lessonId = intent.getIntExtra(REPLY_LESSON_ID, 0)

        //TODO proper fix for racing condition of initChar and getChar
        lessonDetailViewModel = ViewModelProviders.of(
                this, LessonDetailViewModelFactory(application, lessonId))
                .get(LessonDetailViewModel::class.java)
        lessonDetailViewModel!!.characters.observe(this, Observer<List<Character>> { adapter.setCharacters(it!!) })

        val toQuiz = findViewById<Button>(R.id.to_quiz_button)
        toQuiz.setOnClickListener { view ->
            val intent = Intent(view.context, QuizActivity::class.java)
            intent.putExtra(REPLY_LESSON_ID, lessonId)
            startActivity(intent)
        }

    }

    companion object {

        var REPLY_LESSON_ID = "howTheyWrite.LESSON_ID"
    }
}
