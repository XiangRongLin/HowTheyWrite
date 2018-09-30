package com.kaiserpudding.howtheywrite.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import com.kaiserpudding.howtheywrite.model.Lesson;
import com.kaiserpudding.howtheywrite.repositories.LessonRepository;
import java.util.List;

public class LessonViewModel extends AndroidViewModel {

  private LessonRepository lessonRepository;

  private MutableLiveData<List<Lesson>> lessons;

  public LessonViewModel(@NonNull Application application) {
    super(application);
    lessonRepository = new LessonRepository(application);
    lessons = new MutableLiveData<List<Lesson>>();
  }

  public LiveData<List<Lesson>> getAllLessons() {
    return lessons;
  }

  public void updateAllLessons() {
    getAsyncTask getAsyncTask = new getAsyncTask(this);
    getAsyncTask.execute();
  }

  private static class getAsyncTask extends AsyncTask<LessonViewModel, Void, List<Lesson>> {

    private LessonViewModel lessonViewModel;

    getAsyncTask(LessonViewModel lessonViewModel) {
      this.lessonViewModel = lessonViewModel;
    }

    @Override
    protected List<Lesson> doInBackground(LessonViewModel... lessonViewModels) {
      return null;
    }

    @Override
    protected void onPostExecute(List<Lesson>  updatedLessons) {
      lessonViewModel.getLessons().setValue(updatedLessons);
    }
  }

  public MutableLiveData<List<Lesson>> getLessons() {
    return lessons;
  }
}
