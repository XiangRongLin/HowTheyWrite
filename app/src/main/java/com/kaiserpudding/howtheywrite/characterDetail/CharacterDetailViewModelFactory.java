package com.kaiserpudding.howtheywrite.characterDetail;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class CharacterDetailViewModelFactory implements ViewModelProvider.Factory {

  private Application application;
  private int characterId;

  public CharacterDetailViewModelFactory(Application application, int characterId) {
    this.application = application;
    this.characterId = characterId;
  }

  @NonNull
  @Override
  public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
    return (T) new CharacterDetailViewModel(application, characterId);
  }
}
