package com.kaiserpudding.howtheywrite.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import com.kaiserpudding.howtheywrite.model.Lesson;
import com.kaiserpudding.howtheywrite.repositories.LessonRepository;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LessonViewModel extends AndroidViewModel {

  private final LessonRepository lessonRepository;
  private final MutableLiveData<List<Lesson>> lessons;
  private final Executor executor;

  public LessonViewModel(@NonNull Application application) {
    super(application);
    lessonRepository = new LessonRepository(application);
    lessons = new MutableLiveData<List<Lesson>>();
    executor = Executors.newCachedThreadPool();
    updateLessons();
  }

  public void insertLesson(Lesson lesson) {
    executor.execute(() -> {
      lessonRepository.insertLesson(lesson);
    });
  }

  public void updateLessons() {
    executor.execute(() -> {
      lessons.setValue(lessonRepository.getAllLessonsWithRelation());
    });
  }

  public MutableLiveData<List<Lesson>> getLessons() {
    return lessons;
  }
}
