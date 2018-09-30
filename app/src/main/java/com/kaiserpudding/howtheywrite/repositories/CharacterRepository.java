package com.kaiserpudding.howtheywrite.repositories;

import android.app.Application;
import com.kaiserpudding.howtheywrite.database.AppDatabase;
import com.kaiserpudding.howtheywrite.database.CharacterDao;
import com.kaiserpudding.howtheywrite.model.Character;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CharacterRepository {

  private final CharacterDao characterDao;

  public CharacterRepository(Application application) {
    AppDatabase db = AppDatabase.getDatabase(application);
    characterDao = db.characterDao();
  }

  public void insertCharacter(Character character) {
    characterDao.insertCharacter(character);
  }

  public List<Character> getAllCharacters() {
    return characterDao.getAllCharacters();
  }
}
