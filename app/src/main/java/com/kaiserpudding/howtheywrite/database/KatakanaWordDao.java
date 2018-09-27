package com.kaiserpudding.howtheywrite.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Update;
import com.kaiserpudding.howtheywrite.model.KatakanaWord;

@Dao
public interface KatakanaWordDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertKatakana(KatakanaWord katakanaWord);

  @Delete
  void deleteKatakana(KatakanaWord katakanaWord);

  @Update
  void updateKatakana(KatakanaWord katakanaWord);

}
