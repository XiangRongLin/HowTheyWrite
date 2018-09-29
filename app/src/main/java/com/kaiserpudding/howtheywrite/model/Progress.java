package com.kaiserpudding.howtheywrite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Progress {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "id")
  private int id;

  @ColumnInfo(name = "character_id")
  @NonNull
  private int characterId;

  @ColumnInfo(name = "value")
  @NonNull
  private int value;

  @Ignore
  private Character character;

  public Progress(@NonNull int characterId) {
    this.characterId = characterId;
    this.value = 0;
  }

  @Override
  @NonNull
  public String toString() {
    return "Id: " + String.valueOf(getId())
        + "_CharacterId: " + String.valueOf(getCharacterId())
        + "_Value: " + String.valueOf(getValue());

  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setCharacterId(@NonNull int characterId) {
    this.characterId = characterId;
  }

  @NonNull
  public int getCharacterId() {
    return characterId;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public Character getCharacter() {
    return character;
  }

  public void setCharacter(Character character) {
    this.character = character;
  }
}
