package com.kaiserpudding.howtheywrite.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import com.kaiserpudding.howtheywrite.model.Character;
import com.kaiserpudding.howtheywrite.repositories.CharacterRepository;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CharacterViewModel extends AndroidViewModel {

  private final CharacterRepository characterRepository;
  private final MutableLiveData<List<Character>> characters;
  private final Executor executor;

  public CharacterViewModel(@NonNull Application application) {
    super(application);
    characterRepository = new CharacterRepository(application);
    characters = new MutableLiveData<>();
    executor = Executors.newCachedThreadPool();
    updateCharacters();
  }

  public void insertCharacter(Character character) {
    executor.execute(() -> {
      characterRepository.insertCharacter(character);
    });
  }

  public void updateCharacters() {
    executor.execute(() -> {
      characters.setValue(characterRepository.getAllCharacters());
    });
  }

  public MutableLiveData<List<Character>> getCharacters() {
    return characters;
  }
}
