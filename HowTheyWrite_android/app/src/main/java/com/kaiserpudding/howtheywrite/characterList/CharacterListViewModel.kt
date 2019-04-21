package com.kaiserpudding.howtheywrite.characterList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.kaiserpudding.howtheywrite.model.Character
import com.kaiserpudding.howtheywrite.repositories.CharacterRepository
import kotlinx.coroutines.launch

/**
 * ViewModel to load a list of [Character] from the lesson with the given id
 * or load all characters
 *
 * @param application
 * @param lessonId The id of the lesson, from which the characters will be loaded.
 *
 * Can be left empty and it will load all characters.
 */
class CharacterListViewModel(
        application: Application)
    : AndroidViewModel(application) {

    constructor(application: Application, lessonId: Int) : this(application) {
        this.lessonId = lessonId
    }

    private val characterRepository: CharacterRepository = CharacterRepository(application)
    private var lessonId: Int = -1

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
