package com.kaiserpudding.howtheywrite.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

public class WordWithProgress {

  @Embedded
  private KanjiWord word;

  @Relation(parentColumn = "id", entityColumn = "wordId", entity = Progress.class)
  private Progress progress;
}
