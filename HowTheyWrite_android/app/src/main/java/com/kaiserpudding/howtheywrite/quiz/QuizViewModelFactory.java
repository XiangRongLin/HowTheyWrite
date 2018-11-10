package com.kaiserpudding.howtheywrite.quiz;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class QuizViewModelFactory implements ViewModelProvider.Factory {


  private Application application;
  private int lessonId;

  public QuizViewModelFactory(Application application, int lessonId) {
    this.application = application;
    this.lessonId = lessonId;
  }

  @NonNull
  @Override
  public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
    return (T) new QuizViewModel(application, lessonId);
  }
}
