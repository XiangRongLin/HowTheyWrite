package com.kaiserpudding.howtheywrite.lessonList;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.kaiserpudding.howtheywrite.model.Lesson;
import com.kaiserpudding.howtheywrite.repositories.LessonRepository;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LessonListViewModel extends AndroidViewModel {

  private final LessonRepository lessonRepository;
  private final Executor executor;

  private LiveData<List<Lesson>> lessons;
  private List<String> lessonNames;


  public LessonListViewModel(@NonNull Application application) {
    super(application);
    lessonRepository = new LessonRepository(application);
    executor = Executors.newCachedThreadPool();
    initLessons();
  }

  public void insertLesson(Lesson lesson) {
    executor.execute(() -> {
      lessonRepository.insert(lesson);
    });
  }

  private void initLessons() {
    executor.execute(() -> {
      lessonNames = lessonRepository.getAllLessonNames();
    });
      lessons = lessonRepository.getAllLiveDataLessons();
  }

  public LiveData<List<Lesson>> getLessons() {
    return lessons;
  }

  public List<String> getLessonNames() {
    return lessonNames;
  }
}
