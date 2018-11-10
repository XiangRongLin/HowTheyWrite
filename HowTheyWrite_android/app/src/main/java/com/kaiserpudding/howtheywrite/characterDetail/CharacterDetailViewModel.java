package com.kaiserpudding.howtheywrite.characterDetail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import com.kaiserpudding.howtheywrite.model.Character;
import com.kaiserpudding.howtheywrite.repositories.CharacterRepository;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CharacterDetailViewModel extends AndroidViewModel {

  private final CharacterRepository characterRepository;
  private final Executor executor;

  private Character character;

  public CharacterDetailViewModel(@NonNull Application application, int characterId) {
    super(application);
    this.characterRepository = new CharacterRepository(application);
    this.executor = Executors.newCachedThreadPool();
    executor.execute(() -> {
      character = characterRepository.getCharacterById(characterId);
    });
  }

  public Character getCharacter() {
    return character;
  }
}
