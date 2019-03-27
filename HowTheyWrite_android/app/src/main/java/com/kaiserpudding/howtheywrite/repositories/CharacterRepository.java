package com.kaiserpudding.howtheywrite.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import com.kaiserpudding.howtheywrite.database.AppDatabase;
import com.kaiserpudding.howtheywrite.database.dao.CharacterDao;
import com.kaiserpudding.howtheywrite.database.dao.LessonCharacterJoinDao;
import com.kaiserpudding.howtheywrite.model.Character;
import java.util.List;

public class CharacterRepository {

  private final CharacterDao characterDao;
  private final LessonCharacterJoinDao lessonCharacterJoinDao;

  public CharacterRepository(Application application) {
    AppDatabase db = AppDatabase.getDatabase(application);
    characterDao = db.characterDao();
    lessonCharacterJoinDao = db.lessonCharacterJoinJoinDao();
  }

  public void insertCharacter(Character character) {
    characterDao.insert(character);
  }

  public void deleteCharacter(Character character) {
    characterDao.delete(character);
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

  public List<Character> getCharacterByLessonId(int lessonId) {
    return lessonCharacterJoinDao.getCharacterByLessonId(lessonId);
  }

}
