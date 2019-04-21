package com.kaiserpudding.howtheywrite.characterList

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Factory to create a [CharacterListViewModel] with an additional parameter
 *
 * @property application
 * @property lessonId
 */
class CharacterListViewModelFactory(private val application: Application, private val lessonId: Int)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CharacterListViewModel(application, lessonId) as T
    }


}