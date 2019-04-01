package com.kaiserpudding.howtheywrite.characterDetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kaiserpudding.howtheywrite.model.Character
import com.kaiserpudding.howtheywrite.repositories.CharacterRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class CharacterDetailViewModel(application: Application, characterId: Int) : AndroidViewModel(application) {

    private val characterRepository: CharacterRepository
    private val executor: Executor

    var character: Character? = null
        private set

    init {
        this.characterRepository = CharacterRepository(application)
        this.executor = Executors.newCachedThreadPool()
        executor.execute { character = characterRepository.getCharacterById(characterId) }
    }
}
