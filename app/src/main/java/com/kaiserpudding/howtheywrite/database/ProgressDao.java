package com.kaiserpudding.howtheywrite.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.kaiserpudding.howtheywrite.model.Progress;
import java.security.Policy;
import java.util.List;

@Dao
public interface ProgressDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  long insertProgress(Progress progress);

  @Delete
  void deleteProgress(Progress progress);

  @Update
  void updateProgress(Progress progress);

  @Query("SELECT * FROM progress WHERE id = :id")
  LiveData<Progress> getProgressById(int id);

  @Query("SELECT * FROM progress WHERE character_id IN (:characterIds)")
  LiveData<List<Progress>> getProgressesByCharacterIds(int[] characterIds);
}
