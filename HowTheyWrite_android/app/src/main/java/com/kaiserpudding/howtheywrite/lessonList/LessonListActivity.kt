package com.kaiserpudding.howtheywrite.lessonList

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.model.Lesson

class LessonListActivity : AppCompatActivity() {

    private var lessonListViewModel: LessonListViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson_list)

        val recyclerView = findViewById<RecyclerView>(R.id.lesson_recyclerview)
        val adapter = LessonListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        lessonListViewModel = ViewModelProviders.of(this).get(LessonListViewModel::class.java)
        lessonListViewModel!!.lessons!!.observe(this, Observer<List<Lesson>> { adapter.setLessons(it!!) })

        val fab = findViewById<FloatingActionButton>(R.id.add_lessen_fab)
        fab.setOnClickListener {
            val intent = Intent(this@LessonListActivity, NewLessonActivity::class.java)
            startActivityForResult(intent, NEW_LESSON_ACTIVITY_REQUEST_CODE)
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NEW_LESSON_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val lesson = Lesson(data!!.getStringExtra(NewLessonActivity.EXTRA_REPLY))
            lessonListViewModel!!.insertLesson(lesson)
        }
    }

    companion object {

        private val NEW_LESSON_ACTIVITY_REQUEST_CODE = 1
    }

}
