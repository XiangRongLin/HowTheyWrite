package com.kaiserpudding.howtheywrite.characterList

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.kaiserpudding.howtheywrite.model.Character
import com.kaiserpudding.howtheywrite.repositories.CharacterRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors

internal class CharacterListViewModel(application: Application) : AndroidViewModel(application) {

    private val characterRepository: CharacterRepository
    private val executor: Executor

    val characters: LiveData<List<Character>>

    init {
        this.characterRepository = CharacterRepository(application)
        this.executor = Executors.newCachedThreadPool()
        this.characters = characterRepository.allLiveDataCharacters
    }

    fun insertCharacter(character: Character) {
        executor.execute { characterRepository.insertCharacter(character) }
    }
}
