package com.kaiserpudding.howtheywrite.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.kaiserpudding.howtheywrite.model.Character;
import com.kaiserpudding.howtheywrite.model.Lesson;
import java.util.List;

@Dao
public interface CharacterDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  long insertCharacter(Character characterWord);

  @Delete
  void deleteCharacter(Character characterWord);

  @Update
  void updateCharacter(Character characterWord);

  @Query("SELECT * FROM characters WHERE id = :id LIMIT 1")
  Character getCharacterById(int id);

  @Query("SELECT * FROM characters WHERE id IN (:ids)")
  List<Character> getCharactersByIds(int[] ids);

  @Query("SELECT * FROM characters")
  LiveData<List<Character>> getAllCharacters();

}
