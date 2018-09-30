package com.kaiserpudding.howtheywrite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import java.util.LinkedList;
import java.util.List;

@Entity(
    tableName = "lessons",
    indices = @Index(value = "name", unique = true))
public class  Lesson {

  @PrimaryKey(autoGenerate = true)
  private int id;
  @ColumnInfo(name = "name")
  @NonNull
  private String name;
  @Ignore
  @NonNull
  private List<Character> characters;

  public Lesson(@NonNull String name) {
    this.name = name;
    this.characters = new LinkedList<Character>();
  }

  @Override
  @NonNull
  public String toString() {
    return getId() + getName();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @NonNull
  public String getName() {
    return name;
  }

  public void setName(@NonNull String name) {
    this.name = name;
  }

  @NonNull
  public List<Character> getCharacters() {
    return characters;
  }

  public void setCharacters(@NonNull List<Character> characters) {
    this.characters = characters;
  }

  public void addCharacter(Character character) {
    this.characters.add(character);
  }

  public void removeCharacter(Character character) {
    this.characters.remove(character);
  }
}
