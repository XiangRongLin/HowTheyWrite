package com.kaiserpudding.howtheywrite.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import com.kaiserpudding.howtheywrite.database.AppDatabase;
import com.kaiserpudding.howtheywrite.database.CharacterDao;
import com.kaiserpudding.howtheywrite.model.Character;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class CharacterRepository {

  //TODO check if neccessary
  // private final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();
  private final int NUMBER_OF_THREADS = 2;

  private final CharacterDao characterDao;
  private final Executor executor;

  public CharacterRepository(Application application) {
    AppDatabase db = AppDatabase.getDatabase(application);
    characterDao = db.characterDao();
    executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
  }

  public void insertCharacter(Character character) {
    executor.execute(() -> {
      characterDao.insertCharacter(character);
    });
  }

  public LiveData<Character> getCharacterById(int id) {
    return characterDao.getCharacterById(id);
  }

  public LiveData<List<Character>> getCharactersByIds(int[] ids) {
    return characterDao.getCharactersByIds(ids);
  }
}
