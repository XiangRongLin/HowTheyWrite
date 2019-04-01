package com.kaiserpudding.howtheywrite.characterDetail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CharacterDetailViewModelFactory(private val application: Application, private val characterId: Int) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharacterDetailViewModel(application, characterId) as T
    }
}
