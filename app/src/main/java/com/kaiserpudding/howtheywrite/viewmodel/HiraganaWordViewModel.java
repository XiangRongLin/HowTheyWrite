package com.kaiserpudding.howtheywrite.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import com.kaiserpudding.howtheywrite.model.HiraganaWord;
import com.kaiserpudding.howtheywrite.repositories.HiraganaWordRepository;

public class HiraganaWordViewModel extends AndroidViewModel {

  private HiraganaWordRepository mRepository;

  public HiraganaWordViewModel(Application application) {
    super(application);
    mRepository = new HiraganaWordRepository(application);
  }

  public void insert(HiraganaWord hiraganaWord) {
    mRepository.insert(hiraganaWord);
  }
}
