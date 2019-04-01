package com.kaiserpudding.howtheywrite.lessonList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.kaiserpudding.howtheywrite.model.Lesson
import com.kaiserpudding.howtheywrite.repositories.LessonRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class LessonListViewModel(application: Application) : AndroidViewModel(application) {

    private val lessonRepository: LessonRepository = LessonRepository(application)
    private val executor: Executor

    lateinit var lessons: LiveData<List<Lesson>>
        private set
    var lessonNames: List<String>? = null
        private set


    init {
        executor = Executors.newCachedThreadPool()
        initLessons()
    }

    fun insertLesson(lesson: Lesson) {
        executor.execute { lessonRepository.insert(lesson) }
    }

    private fun initLessons() {
        executor.execute { lessonNames = lessonRepository.allLessonNames }
        lessons = lessonRepository.allLiveDataLessons
    }
}
