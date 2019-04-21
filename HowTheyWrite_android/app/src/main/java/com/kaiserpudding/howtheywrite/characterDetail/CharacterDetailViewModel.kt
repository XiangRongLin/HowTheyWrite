package com.kaiserpudding.howtheywrite.characterDetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kaiserpudding.howtheywrite.model.Character
import com.kaiserpudding.howtheywrite.repositories.CharacterRepository
import kotlinx.coroutines.launch

/**
 * ViewModel to load a [Character] with given id from the db
 *
 * Before accessing it check if [finishedLoading].value is true, of add a listener to it to be
 * notified when it finished
 *
 *
 * @param application
 * @param characterId The id of the character which should be loaded
 */
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
