package com.kaiserpudding.howtheywrite.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.kaiserpudding.howtheywrite.model.User;
import com.kaiserpudding.howtheywrite.model.UserWithProgress;

@Dao
public interface UserDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  long insertUser(User user);

  @Delete
  void deleteUser(User user);

  @Update
  void updateUser(User user);

  @Query("SELECT * FROM User WHERE id = :id")
  User getUserById(int id);

  @Query("SELECT * FROM User WHERE id = :id")
  UserWithProgress getUserWithProgressById(int id);

}
