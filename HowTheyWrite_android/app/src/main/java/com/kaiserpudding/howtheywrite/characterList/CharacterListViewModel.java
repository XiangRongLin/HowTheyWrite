package com.kaiserpudding.howtheywrite.characterList;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.kaiserpudding.howtheywrite.model.Character;
import com.kaiserpudding.howtheywrite.repositories.CharacterRepository;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class CharacterListViewModel extends AndroidViewModel {

  private final CharacterRepository characterRepository;
  private final Executor executor;

  private LiveData<List<Character>> characters;

  public CharacterListViewModel(@NonNull Application application) {
    super(application);
    this.characterRepository = new CharacterRepository(application);
    this.executor = Executors.newCachedThreadPool();
    this.characters = characterRepository.getAllLiveDataCharacters();
  }

  public void insertCharacter(Character character) {
    executor.execute(() -> {
      characterRepository.insertCharacter(character);
    });
  }

  public LiveData<List<Character>> getCharacters() {
    return characters;
  }
}
