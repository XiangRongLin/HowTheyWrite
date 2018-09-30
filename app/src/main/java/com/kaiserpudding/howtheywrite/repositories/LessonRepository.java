package com.kaiserpudding.howtheywrite.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
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

  private LessonDao lessonDao;
  private LessonCharacterJoinDao lessonCharacterJoinDao;

  public LessonRepository(Application application) {
    AppDatabase db = AppDatabase.getDatabase(application);
    lessonDao = db.lessonDao();
    lessonCharacterJoinDao = db.lessonCharacterJoinJoinDao();
  }

  public void insertLesson(Lesson lesson) {
    lessonDao.insertLesson(lesson);
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

  public List<Lesson> getAllLessons() {
    return lessonDao.getAllLessons();
  }

  public List<Lesson> getAllLessonsWithRelation() {
    return lessonCharacterJoinDao.getAllLessons();
  }
}
