package com.kaiserpudding.howtheywrite.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import com.kaiserpudding.howtheywrite.model.Character;
import com.kaiserpudding.howtheywrite.repositories.CharacterRepository;

public class CharacterViewModel extends AndroidViewModel {

  private CharacterRepository characterRepository;

  public CharacterViewModel(@NonNull Application application) {
    super(application);
    characterRepository = new CharacterRepository(application);
  }

  public void insertCharacter(Character character) {
    characterRepository.insertCharacter(character);
  }



}
