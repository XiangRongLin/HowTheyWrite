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
 * Before accessing it check if [finishedLoading].value is true, or add a listenerBase to it to be
 * notified when it finished
 *
 *
 * @param application
 * @param characterId The id of the character which should be loaded
 */
class CharacterDetailViewModel(application: Application, characterId: Long)
    : AndroidViewModel(application) {

    private val characterRepository: CharacterRepository = CharacterRepository(application)
    private val _finishedLoading: MutableLiveData<Boolean> = MutableLiveData()
    val finishedLoading: LiveData<Boolean>
        get() = _finishedLoading

    private lateinit var character: Character

    init {
        viewModelScope.launch {
            character = characterRepository.getCharacterById(characterId)
            _finishedLoading.value = true
        }

    }

    val hanzi: String
        get() = character.hanzi
    val pinyin: String
        get() = character.pinyin
    val translation: String
        get() {
            return if (character.translationKey != null) {
                val application = getApplication<Application>()
                val resources = application.resources
                resources.getText(
                        resources.getIdentifier(
                                character.translationKey,
                                "string",
                                application.applicationContext.packageName
                        )
                ).toString()
            } else {
                character.translation.toString()
            }
        }
}
