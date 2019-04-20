package com.kaiserpudding.howtheywrite.quiz

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.kaiserpudding.howtheywrite.model.Character
import com.kaiserpudding.howtheywrite.repositories.CharacterRepository
import kotlin.LazyThreadSafetyMode.NONE

class QuizViewModel(application: Application, lessonId: Int) : AndroidViewModel(application) {

    private val characterRepository: CharacterRepository = CharacterRepository(application)

    val characterLiveData: LiveData<List<Character>> by lazy<LiveData<List<Character>>>(NONE) {
        Transformations.map(
                characterRepository.getLiveDataCharacterByLessonIdInRandomOrder(lessonId)) {
            it
        }
    }

    lateinit var characters: MutableList<Character>
        private set
    private var currentCharacterIndex: Int = 0
    private var charactersSize: Int = 0

    internal val currentCharacter: Character
        get() = characters[currentCharacterIndex]

    internal val currentCharacterTranslation: String
        get() {
            return if (currentCharacter.translationKey != null) {
                val resources = getApplication<Application>().resources
                resources.getText(
                        resources.getIdentifier(
                                currentCharacter.translationKey,
                                "string",
                                getApplication<Application>().applicationContext.packageName
                        )
                ).toString()
            } else {
                currentCharacter.translation!!
            }
        }

    internal val currentCharacterPinyin: String
        get() = currentCharacter.pinyin

    internal fun initCharacters(list: List<Character>) {
        characters = list.toMutableList()
        charactersSize = characters.size
    }

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
