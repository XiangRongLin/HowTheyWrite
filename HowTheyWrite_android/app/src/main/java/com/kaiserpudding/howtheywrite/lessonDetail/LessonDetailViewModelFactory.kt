package com.kaiserpudding.howtheywrite.lessonDetail

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

class LessonDetailViewModelFactory(private val application: Application, private val lessonId: Int) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LessonDetailViewModel(application, lessonId) as T
    }
}
