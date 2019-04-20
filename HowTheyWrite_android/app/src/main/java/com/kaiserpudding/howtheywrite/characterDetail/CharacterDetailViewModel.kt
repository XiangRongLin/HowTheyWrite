package com.kaiserpudding.howtheywrite.characterDetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.kaiserpudding.howtheywrite.model.Character
import com.kaiserpudding.howtheywrite.repositories.CharacterRepository
import kotlin.LazyThreadSafetyMode.NONE

class CharacterDetailViewModel(application: Application, characterId: Int)
    : AndroidViewModel(application) {

    private val characterRepository: CharacterRepository = CharacterRepository(application)

//    var character: Character? = null
//        private set

    val character: LiveData<Character> by lazy<LiveData<Character>>(NONE) {
        Transformations.map(characterRepository.getLiveDataCharacterById(characterId)) {
            it
        }
    }
}
