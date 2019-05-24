package com.kaiserpudding.howtheywrite.quiz

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Factory to create a [QuizViewModel] with an additional parameter
 *
 * @property application
 * @property lessonId
 */
class QuizViewModelFactory : ViewModelProvider.Factory {

    private val application: Application
    private val lessonId: Int
    private val characterIds: IntArray?

    constructor(application: Application, lessonId: Int) {
        this.application = application
        this.lessonId = lessonId
        this.characterIds = null
    }

    constructor(application: Application, characterIds: IntArray) {
        this.application = application
        this.lessonId = -1
        this.characterIds = characterIds
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (lessonId != -1) QuizViewModel(application, lessonId) as T
        else QuizViewModel(application, characterIds!!) as T
    }
}
