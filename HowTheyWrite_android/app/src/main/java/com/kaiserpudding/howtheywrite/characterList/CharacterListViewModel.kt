package com.kaiserpudding.howtheywrite.characterList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.kaiserpudding.howtheywrite.model.Character
import com.kaiserpudding.howtheywrite.repositories.CharacterRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class CharacterListViewModel(
        application: Application,
        lessonId: Int = -1)
    : AndroidViewModel(application) {

    private val characterRepository: CharacterRepository = CharacterRepository(application)
    private val executor: Executor = Executors.newCachedThreadPool()!!

    val characters: LiveData<List<Character>>

    init {
        this.characters =
                if (lessonId == -1) characterRepository.allLiveDataCharacters
                else characterRepository.getLiveDataCharacterByLessonId(lessonId)
    }

    //This method is used
    constructor(application: Application): this(application, -1)

    fun insertCharacter(character: Character) {
        executor.execute { characterRepository.insertCharacter(character) }
    }
}
