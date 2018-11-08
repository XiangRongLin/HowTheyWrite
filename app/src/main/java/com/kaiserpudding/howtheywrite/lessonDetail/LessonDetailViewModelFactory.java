package com.kaiserpudding.howtheywrite.lessonDetail;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class LessonDetailViewModelFactory implements ViewModelProvider.Factory {

  private Application application;
  private int lessonId;

  public LessonDetailViewModelFactory(Application application, int lessonId) {
    this.application = application;
    this.lessonId = lessonId;
  }

  @NonNull
  @Override
  public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
    return (T) new LessonDetailViewModel(application, lessonId);
  }
}
