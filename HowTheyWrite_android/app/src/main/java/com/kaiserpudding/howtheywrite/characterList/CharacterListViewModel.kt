package com.kaiserpudding.howtheywrite.characterList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.kaiserpudding.howtheywrite.model.Character
import com.kaiserpudding.howtheywrite.repositories.CharacterRepository
import kotlinx.coroutines.launch

class CharacterListViewModel(
        application: Application,
        lessonId: Int = -1)
    : AndroidViewModel(application) {

    private val characterRepository: CharacterRepository = CharacterRepository(application)

    val characters: LiveData<List<Character>>

    init {
        characters =
                if (lessonId == -1) characterRepository.allLiveDataCharacters()
                else characterRepository.getLiveDataCharacterByLessonId(lessonId)
    }

    fun insertCharacter(character: Character) {
        viewModelScope.launch {
            characterRepository.insert(character)
        }

    }
}
