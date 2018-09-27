package com.kaiserpudding.howtheywrite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(indices = {@Index(value = {"user_id", "word_id"}, unique = true)})
public class Progress {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "id")
  private int id;

  @ColumnInfo(name = "user_id")
  @NonNull
  private final String userId;

  @ColumnInfo(name = "word_id")
  @NonNull
  private final String wordId;

  public Progress(@NonNull String userId, @NonNull String wordId) {
    this.userId = userId;
    this.wordId = wordId;
  }

  public int getId() {
    return id;
  }

  @NonNull
  public String getUserId() {
    return userId;
  }

  @NonNull
  public String getWordId() {
    return wordId;
  }
}
