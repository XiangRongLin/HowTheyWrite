package com.kaiserpudding.howtheywrite.characterDetail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Factory to create a [CharacterDetailViewModel] with an additional parameter
 *
 * @property application
 * @property characterId
 */
@Suppress("UNCHECKED_CAST")
class CharacterDetailViewModelFactory(private val application: Application, private val characterId: Int) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharacterDetailViewModel(application, characterId) as T
    }
}
