package com.kaiserpudding.howtheywrite.lessonList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.kaiserpudding.howtheywrite.model.Lesson
import com.kaiserpudding.howtheywrite.repositories.CharacterRepository
import com.kaiserpudding.howtheywrite.repositories.LessonRepository
import kotlinx.coroutines.launch

/**
 * ViewModel to load a list of [Lesson]
 *
 * @param application
 */
class LessonListViewModel(application: Application) : AndroidViewModel(application) {

    private val lessonRepository = LessonRepository(application)
    private val characterRepository = CharacterRepository(application)

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

    fun deleteLessons(lessonIds: LongArray) {
        viewModelScope.launch {
            lessonRepository.deleteLessons(lessonIds)
        }
    }

    fun addCharactersOfLessonToLesson(lessonIds: LongArray, addToId: Long) {
        viewModelScope.launch {
            characterRepository.addCharactersOfLessonToLesson(lessonIds, addToId)
        }
    }


}
