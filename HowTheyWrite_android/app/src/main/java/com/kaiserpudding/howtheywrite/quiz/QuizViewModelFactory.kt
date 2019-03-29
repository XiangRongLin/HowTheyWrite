package com.kaiserpudding.howtheywrite.quiz

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

class QuizViewModelFactory(private val application: Application, private val lessonId: Int) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QuizViewModel(application, lessonId) as T
    }
}
