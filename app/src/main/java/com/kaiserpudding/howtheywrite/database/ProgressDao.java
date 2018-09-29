package com.kaiserpudding.howtheywrite.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Update;
import com.kaiserpudding.howtheywrite.model.Progress;

@Dao
public interface ProgressDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  long insertProgress(Progress progress);

  @Delete
  void deleteProgress(Progress progress);

  @Update
  void updateProgress(Progress progress);
}
