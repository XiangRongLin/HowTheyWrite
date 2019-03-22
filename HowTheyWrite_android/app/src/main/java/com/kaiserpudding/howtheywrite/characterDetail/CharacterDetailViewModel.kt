package com.kaiserpudding.howtheywrite.characterDetail

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.kaiserpudding.howtheywrite.model.Character
import com.kaiserpudding.howtheywrite.repositories.CharacterRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class CharacterDetailViewModel(application: Application, characterId: Int) : AndroidViewModel(application) {

    private val characterRepository: CharacterRepository = CharacterRepository(application)
    private val executor: Executor = Executors.newCachedThreadPool()

    lateinit var character: Character
        private set

    init {
        executor.execute { character = characterRepository.getCharacterById(characterId) }
    }
}
