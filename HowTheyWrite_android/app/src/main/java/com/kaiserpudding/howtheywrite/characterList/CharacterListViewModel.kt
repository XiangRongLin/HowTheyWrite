package com.kaiserpudding.howtheywrite.characterList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kaiserpudding.howtheywrite.model.Character
import com.kaiserpudding.howtheywrite.repositories.CharacterRepository
import com.kaiserpudding.howtheywrite.repositories.LessonRepository
import kotlinx.coroutines.launch

/**
 * ViewModel to load a list of [Character] from the lesson with the given id
 * or load all characters
 *
 * @param application
 * @param lessonId The id of the lesson, from which the characters will be loaded.
 *
 * Can be left empty and it will load all characters.
 */
class CharacterListViewModel(
        application: Application,
        val lessonId: Int,
        loadAll: Boolean)
    : AndroidViewModel(application) {

    private val characterRepository: CharacterRepository = CharacterRepository(application)
    private val lessonRepository: LessonRepository = LessonRepository(application)

    val characters: LiveData<List<Character>>
    internal val lessonName: LiveData<String>

    init {
        characters =
                if (loadAll) {
                    val tmp = MutableLiveData<String>()
                    lessonName = tmp
                    tmp.postValue("All")
                    characterRepository.allLiveDataCharacters()
                } else {
                    lessonName = lessonRepository.getLiveDataLessonNameById(lessonId)
                    characterRepository.getLiveDataCharacterByLessonId(lessonId)
                }
    }

    fun addCharactersToLesson(characterIds: IntArray) {
        viewModelScope.launch {
            characterRepository.addCharactersToLesson(lessonId, characterIds)
        }
    }

    fun deleteCharactersFromLesson(characterIds: IntArray) {
        viewModelScope.launch {
            characterRepository.deleteCharactersFromLesson(lessonId, characterIds)
        }
    }

    fun deleteCharacters(characterIds: IntArray) {
        viewModelScope.launch {
            characterRepository.delete(characterIds)
        }
    }
}
