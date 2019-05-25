package com.kaiserpudding.howtheywrite.characterList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kaiserpudding.howtheywrite.model.Character
import com.kaiserpudding.howtheywrite.repositories.CharacterRepository
import kotlinx.coroutines.launch

class InsertionViewModel(application: Application) : AndroidViewModel(application) {

    private val characterRepository: CharacterRepository by lazy { CharacterRepository(application) }

    fun insertCharacter(character: Character) {
        viewModelScope.launch { characterRepository.insert(character) }
    }
}