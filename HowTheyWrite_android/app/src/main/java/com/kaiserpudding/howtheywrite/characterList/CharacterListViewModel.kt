package com.kaiserpudding.howtheywrite.characterList

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.kaiserpudding.howtheywrite.model.Character
import com.kaiserpudding.howtheywrite.repositories.CharacterRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class CharacterListViewModel(application: Application, lessonId: Int) : AndroidViewModel(application) {

    private var characterRepository: CharacterRepository = CharacterRepository(application)
    private var executor: Executor = Executors.newCachedThreadPool()
    var characters: LiveData<List<Character>>
        private set

    init {
        characters = if (lessonId == 0) characterRepository.allLiveDataCharacters
        else characterRepository.getLiveDataCharacterByLessonId(lessonId)
    }

    fun insertCharacter(character: Character) {
        executor.execute { characterRepository.insertCharacter(character) }
    }
}
