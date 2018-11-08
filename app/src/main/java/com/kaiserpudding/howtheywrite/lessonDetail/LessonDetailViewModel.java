package com.kaiserpudding.howtheywrite.lessonDetail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.kaiserpudding.howtheywrite.model.Character;
import com.kaiserpudding.howtheywrite.repositories.CharacterRepository;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LessonDetailViewModel extends AndroidViewModel {

  private final CharacterRepository characterRepository;
  private final Executor executor;

  private LiveData<List<Character>> characters;

  public LessonDetailViewModel(@NonNull Application application, int lessonId) {
    super(application);
    this.characterRepository = new CharacterRepository(application);
    this.executor = Executors.newCachedThreadPool();
    characters = characterRepository.getLiveDataCharacterByLessonId(lessonId);

  }

  public LiveData<List<Character>> getCharacters() {
    return characters;
  }
}
