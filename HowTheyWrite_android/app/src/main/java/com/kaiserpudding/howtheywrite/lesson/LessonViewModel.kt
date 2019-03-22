package com.kaiserpudding.howtheywrite.lesson

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.kaiserpudding.howtheywrite.model.Character
import com.kaiserpudding.howtheywrite.model.Lesson
import com.kaiserpudding.howtheywrite.repositories.CharacterRepository
import com.kaiserpudding.howtheywrite.repositories.LessonRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class LessonViewModel(application: Application) : AndroidViewModel(application) {

    private val lessonRepository: LessonRepository = LessonRepository(application)
    private val executor: Executor = Executors.newCachedThreadPool()

    var lessons: LiveData<List<Lesson>> = lessonRepository.allLiveDataLessons
    var lessonNames: List<String>? = null


    init {
        executor.execute{lessonNames = lessonRepository.allLessonNames}
    }

    fun insertLesson(lesson: Lesson) {
        executor.execute { lessonRepository.insertLesson(lesson) }
    }
}
