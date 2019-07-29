package com.kaiserpudding.howtheywrite.quiz

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kaiserpudding.howtheywrite.model.Character
import com.kaiserpudding.howtheywrite.repositories.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel to load the [Character] from the lesson with the given id
 *
 * Before accessing it check if [finishedLoading].value is true, or add a listenerBase to it to be
 * notified when it finishes
 *
 * @param application
 * @param lessonId
 */
class QuizViewModel : AndroidViewModel {

    constructor(application: Application, lessonId: Long) : super(application) {
        this.characterRepository = CharacterRepository(application)
        this._finishedLoading = MutableLiveData()
        viewModelScope.launch {
            characters = characterRepository.getCharactersByLessonIdInRandomOrder(lessonId).toMutableList()
            charactersSize = characters.size
            _finishedLoading.value = true
        }
    }

    constructor(application: Application, characterIds: LongArray): super(application) {
        this.characterRepository = CharacterRepository(application)
        this._finishedLoading = MutableLiveData()
        viewModelScope.launch {
            characters = characterRepository.getCharactersByIds(characterIds).toMutableList()
            charactersSize = characters.size
            _finishedLoading.value = true
        }
    }

    private val characterRepository: CharacterRepository

    private val _finishedLoading: MutableLiveData<Boolean>
    val finishedLoading: LiveData<Boolean>
        get() = _finishedLoading

    lateinit var characters: MutableList<Character>
        private set
    private var currentCharacterIndex: Int = 0
    private var charactersSize: Int = 0

    internal val currentCharacter: Character
        get() = characters[currentCharacterIndex]

    internal val currentCharacterTranslation: String
        get() {
            return if (currentCharacter.translationKey != null) {
                val application = getApplication<Application>()
                val resources = application.resources
                resources.getText(
                        resources.getIdentifier(
                                currentCharacter.translationKey,
                                "string",
                                application.applicationContext.packageName
                        )
                ).toString()
            } else {
                currentCharacter.translation.toString()
            }
        }

    internal val currentCharacterPinyin: String
        get() = currentCharacter.pinyin

    /**
     * Increases the index, so currentCharacter is the next one
     *
     */
    internal fun nextCharacter() {
        currentCharacterIndex++
    }

    fun inputIsCorrect(input: String): Boolean {
        return input == currentCharacter.hanzi
    }

    fun hasNext(): Boolean {
        return currentCharacterIndex < charactersSize - 1
    }
}
