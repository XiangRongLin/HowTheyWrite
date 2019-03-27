package com.kaiserpudding.howtheywrite.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import com.kaiserpudding.howtheywrite.database.AppDatabase;
import com.kaiserpudding.howtheywrite.database.dao.LessonCharacterJoinDao;
import com.kaiserpudding.howtheywrite.database.dao.LessonDao;
import com.kaiserpudding.howtheywrite.model.Character;
import com.kaiserpudding.howtheywrite.model.Lesson;
import com.kaiserpudding.howtheywrite.model.LessonCharacterJoin;
import java.util.List;

public class LessonRepository {

  private LessonDao lessonDao;
  private LessonCharacterJoinDao lessonCharacterJoinDao;

  public LessonRepository(Application application) {
    AppDatabase db = AppDatabase.getDatabase(application);
    lessonDao = db.lessonDao();
    lessonCharacterJoinDao = db.lessonCharacterJoinJoinDao();
  }

  public void insert(Lesson lesson) {
    lessonDao.insert(lesson);
    lessonDao.insert(lesson);
    insertLessonCharacterJoin(lesson);
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
      lessonCharacterJoinDao.insertLessonCharacterJoin(toInsert);
    }
  }

  public LiveData<List<Lesson>> getAllLiveDataLessons() {
    return lessonDao.getAllLessons();
  }

  public Lesson getLessonWithRelationById(int id) {
    return lessonCharacterJoinDao.getLessonByLessonId(id);
  }

  public LiveData<Lesson> getLiveDataLessonWithRelationById(int id) {
    return lessonCharacterJoinDao.getLiveDataLessonByLessonId(id);
  }

  public List<String> getAllLessonNames() {
    return lessonDao.getAllLessonNames();
  }
}
