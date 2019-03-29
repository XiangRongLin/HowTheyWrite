package com.kaiserpudding.howtheywrite.lessonDetail

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.kaiserpudding.howtheywrite.model.Character
import com.kaiserpudding.howtheywrite.repositories.CharacterRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class LessonDetailViewModel(application: Application, lessonId: Int) : AndroidViewModel(application) {

    private val characterRepository: CharacterRepository
    private val executor: Executor

    val characters: LiveData<List<Character>>

    init {
        this.characterRepository = CharacterRepository(application)
        this.executor = Executors.newCachedThreadPool()
        characters = characterRepository.getLiveDataCharacterByLessonId(lessonId)

    }
}
