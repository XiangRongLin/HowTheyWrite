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

    internal val currentWord: Character
        get() = characters!![currentCharacterIndex]

    //TODO multiple calls return different values
    internal val nextWord: Character?
        get() {
            return if (currentCharacterIndex + 1 >= charactersSize) null
            else characters!![++currentCharacterIndex]
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
}
