package com.kaiserpudding.howtheywrite.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;
import java.util.List;

public class CharacterWithProgress {

  @Embedded
  private Character character;

  @Relation(parentColumn = "id", entityColumn = "character_id", entity = Progress.class)
  private List<Progress> progress;

  public Character getCharacter() {
    return character;
  }

  public void setCharacter(Character character) {
    this.character = character;
  }

  public List<Progress> getProgress() {
    return progress;
  }

  public void setProgress(List<Progress> progress) {
    this.progress = progress;
  }
}
