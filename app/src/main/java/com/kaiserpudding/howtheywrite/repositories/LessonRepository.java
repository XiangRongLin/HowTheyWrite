package com.kaiserpudding.howtheywrite.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import com.kaiserpudding.howtheywrite.database.AppDatabase;
import com.kaiserpudding.howtheywrite.database.LessonCharacterJoinDao;
import com.kaiserpudding.howtheywrite.database.LessonDao;
import com.kaiserpudding.howtheywrite.model.Character;
import com.kaiserpudding.howtheywrite.model.Lesson;
import com.kaiserpudding.howtheywrite.model.LessonCharacterJoin;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LessonRepository {

  //TODO check if necessary
  // private final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();
  private final int NUMBER_OF_THREADS = 2;

  private LessonDao lessonDao;
  private LessonCharacterJoinDao lessonCharacterJoinDao;
  private Executor executor;

  public LessonRepository(Application application) {
    AppDatabase db = AppDatabase.getDatabase(application);
    lessonDao = db.lessonDao();
    lessonCharacterJoinDao = db.lessonCharacterJoinJoinDao();
    executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
  }

  public void insertLesson(Lesson lesson) {
    executor.execute(() -> {
      lessonDao.insertLesson(lesson);
    });
  }

  public void insertLessonCharacterJoin(Lesson lesson) {
    List<Character> characters = lesson.getCharacters();
    int numberOfCharacters = characters.size();
    if (numberOfCharacters > 0) {
      int lessonId = lesson.getId();
      LessonCharacterJoin[] toInsert = new LessonCharacterJoin[numberOfCharacters];
      for (int i = 0; i < numberOfCharacters; i++) {
        toInsert[i] = new LessonCharacterJoin(lessonId, characters.get(i).getId());
      }
      executor.execute(() -> {
        lessonCharacterJoinDao.insertLessonCharacterJoin(toInsert);
      });
    }
  }

  public LiveData<List<Lesson>> getAllLiveLessons() {
    return lessonDao.getAllLiveLessons();
  }

  public List<Lesson> getAllLessons() {
    return lessonDao.getAllLessons();
  }

  public LiveData<Lesson> getLiveLessonWithRelationByName(String name) {
    int lessonId = lessonDao.getLessonByName(name).getId();
    return lessonCharacterJoinDao.getLiveDataLessonByLessonId(lessonId);
  }
}
