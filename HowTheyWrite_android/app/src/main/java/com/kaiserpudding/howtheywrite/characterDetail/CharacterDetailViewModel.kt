package com.kaiserpudding.howtheywrite.characterDetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kaiserpudding.howtheywrite.model.Character
import com.kaiserpudding.howtheywrite.repositories.CharacterRepository
import kotlinx.coroutines.launch

class CharacterDetailViewModel(application: Application, characterId: Int)
    : AndroidViewModel(application) {

    private val characterRepository: CharacterRepository = CharacterRepository(application)
    private val _finishedLoading: MutableLiveData<Boolean> = MutableLiveData()
    val finishedLoading: LiveData<Boolean>
        get() = _finishedLoading

    lateinit var character: Character
        private set

    init {
        viewModelScope.launch {
            character = characterRepository.getCharactersById(characterId)
            _finishedLoading.value = true
        }

    }
}
