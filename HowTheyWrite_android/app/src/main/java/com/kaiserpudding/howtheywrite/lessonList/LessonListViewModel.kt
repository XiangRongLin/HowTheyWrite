package com.kaiserpudding.howtheywrite.lessonList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.kaiserpudding.howtheywrite.model.Lesson
import com.kaiserpudding.howtheywrite.repositories.LessonRepository
import kotlinx.coroutines.launch

class LessonListViewModel(application: Application) : AndroidViewModel(application) {

    private val lessonRepository: LessonRepository = LessonRepository(application)

    var lessons: LiveData<List<Lesson>>
        private set
    var lessonNames: List<String> = listOf()
        private set


    init {
        lessons = lessonRepository.allLiveDataLessons()
        Transformations.map(lessons) { list ->
            val tmp = mutableListOf<String>()
            list.forEach { lesson ->
                tmp.add(lesson.name)
            }
            lessonNames = tmp
        }
    }

    fun insertLesson(lesson: Lesson) {
        viewModelScope.launch {
            lessonRepository.insert(lesson)
        }
    }
}
