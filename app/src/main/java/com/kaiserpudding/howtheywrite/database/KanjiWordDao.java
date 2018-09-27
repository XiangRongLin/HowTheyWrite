package com.kaiserpudding.howtheywrite.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import android.arch.persistence.room.Update;
import com.kaiserpudding.howtheywrite.model.KanjiWord;

@Dao
public interface KanjiWordDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertKanji(KanjiWord kanjiWord);

  @Delete
  void deleteKanji(KanjiWord kanjiWord);

  @Update
  void updateKanji(KanjiWord kanjiWord);
}
