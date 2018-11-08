package com.kaiserpudding.howtheywrite.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import com.kaiserpudding.howtheywrite.database.AppDatabase;
import com.kaiserpudding.howtheywrite.database.CharacterDao;
import com.kaiserpudding.howtheywrite.database.LessonCharacterJoinDao;
import com.kaiserpudding.howtheywrite.model.Character;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CharacterRepository {

  private final CharacterDao characterDao;
  private final LessonCharacterJoinDao lessonCharacterJoinDao;

  public CharacterRepository(Application application) {
    AppDatabase db = AppDatabase.getDatabase(application);
    characterDao = db.characterDao();
    lessonCharacterJoinDao = db.lessonCharacterJoinJoinDao();
  }

  public void insertCharacter(Character character) {
    characterDao.insertCharacter(character);
  }

  public void deleteCharacter(Character character) {
    characterDao.deleteCharacter(character);
  }

  public Character getCharacterById(int id)  {
    return characterDao.getCharacterById(id);
  }

  public LiveData<List<Character>> getAllLiveDataCharacters() {
    return characterDao.getAllCharacters();
  }

  public LiveData<List<Character>> getLiveDataCharacterByLessonId(int lessonId) {
    return lessonCharacterJoinDao.getLiveDataCharacterByLessonId(lessonId);
  }

}
