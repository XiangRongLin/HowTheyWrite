package com.kaiserpudding.howtheywrite.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Update;
import com.kaiserpudding.howtheywrite.model.User;

@Dao
public interface UserDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertUser(User user);

  @Delete
  void deleteUser(User user);

  @Update
  void updateUser(User user);

}
