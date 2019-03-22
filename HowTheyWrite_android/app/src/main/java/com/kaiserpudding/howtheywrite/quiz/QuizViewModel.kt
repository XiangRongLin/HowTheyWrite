package com.kaiserpudding.howtheywrite.quiz

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.kaiserpudding.howtheywrite.model.Character
import com.kaiserpudding.howtheywrite.repositories.CharacterRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.random.Random

class QuizViewModel(application: Application,
                    lessonId: Int = 0,
                    characterIds: IntArray? = null) : AndroidViewModel(application) {

    private val characterRepository: CharacterRepository = CharacterRepository(application)
    private val executor: Executor = Executors.newCachedThreadPool()

    lateinit var characters: Array<Character>
    private var characterIndex: Int = 0
    private var charactersSize: Int = 0

    val currentCharacter: Character
        get() = characters[characterIndex]

    /**
     * Returns the next character.
     * If there are no more characters, just return the last one.
     * Recommended to call [hasNext] beforehand.
     */
    val nextCharacter: Character
        get() {
            if (characterIndex + 1 < charactersSize) characterIndex++
            return characters[characterIndex]
        }

    init {
        if (characterIds != null) {
            executor.execute {
                characters = characterRepository.getCharactersByIds(characterIds)
                charactersSize = characters.size
                characters = shuffle(characters);
            }
        } else {
            executor.execute{
                characters = characterRepository.getCharacterByLessonId(lessonId)
                charactersSize = characters.size
                characters = shuffle(characters);
            }
        }
    }

    fun hasNext(): Boolean{
        return characterIndex >=  charactersSize
    }

    //randomly shuffles an array with the fisher and yates method
    private fun shuffle (array: Array<Character>): Array<Character> {
        for (i in 0 until array.size - 1) {
            val tmp = array[i]
            val j = Random.nextInt(0, array.size - 1)
            array[i] = array[j]
            array[j] = tmp
        }
        return array
    }
}
