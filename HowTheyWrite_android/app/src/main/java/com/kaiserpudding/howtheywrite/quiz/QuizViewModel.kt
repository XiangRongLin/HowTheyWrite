package com.kaiserpudding.howtheywrite.quiz

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kaiserpudding.howtheywrite.model.Character
import com.kaiserpudding.howtheywrite.repositories.CharacterRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class QuizViewModel(application: Application, lessonId: Int) : AndroidViewModel(application) {

    private val characterRepository: CharacterRepository
    private val executor: Executor

    var characters: MutableList<Character>? = null
        private set
    private var currentCharacterIndex: Int = 0
    private var charactersSize: Int = 0

    internal val currentCharacter: Character
        get() = characters!![currentCharacterIndex]

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

    /**
     * Increases the index, so currentCharacter is the next one
     *
     */
    internal fun nextCharacter() {
        currentCharacterIndex++
    }

    init {
        this.characterRepository = CharacterRepository(application)
        this.executor = Executors.newCachedThreadPool()
        executor.execute {
            characters = characterRepository.getCharacterByLessonId(lessonId)
            charactersSize = characters!!.size
            characters!!.shuffle()
        }
    }

    fun inputIsCorrect(input: String): Boolean {
        return input == currentCharacter.hanzi
    }

    fun hasNext(): Boolean {
        return currentCharacterIndex < charactersSize - 1
    }
}
