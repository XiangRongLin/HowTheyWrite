package com.kaiserpudding.howtheywrite.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import android.arch.persistence.room.Update;
import com.kaiserpudding.howtheywrite.model.HiraganaWord;

@Dao
public interface HiraganaWordDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertHiragana(HiraganaWord hiraganaWord);

  @Delete
  void deleteHiragana(HiraganaWord hiraganaWord);

  @Update
  void updateHiragana(HiraganaWord hiraganaWord);
}
