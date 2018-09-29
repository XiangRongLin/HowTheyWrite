/*
package com.kaiserpudding.howtheywrite.repositories;

import android.app.Application;
import android.os.AsyncTask;
import com.kaiserpudding.howtheywrite.database.AppDatabase;
import com.kaiserpudding.howtheywrite.database.HiraganaWordDao;
import com.kaiserpudding.howtheywrite.model.HiraganaWord;

public class HiraganaWordRepository {

  private HiraganaWordDao HiraganaDao;

  public HiraganaWordRepository(Application application) {
    AppDatabase db = AppDatabase.getDatabase(application);
  }

  public void insert(HiraganaWord hiraganaWord) {
    new insertAsyncTask(HiraganaDao).execute(hiraganaWord);
  }

  private static class insertAsyncTask extends AsyncTask<HiraganaWord, Void, Void> {

    private HiraganaWordDao mAsyncTaskDao;

    insertAsyncTask(HiraganaWordDao dao) {
      mAsyncTaskDao = dao;
    }

    @Override
    protected Void doInBackground(final HiraganaWord... params) {
      mAsyncTaskDao.insertHiragana(params[0]);
      return null;
    }
  }
}
*/
