package com.kaiserpudding.howtheywrite.characterDetail

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

class CharacterDetailViewModelFactory(private val application: Application, private val characterId: Int) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharacterDetailViewModel(application, characterId) as T
    }
}
