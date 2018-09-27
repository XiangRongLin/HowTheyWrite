package com.kaiserpudding.howtheywrite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "words")
public abstract class Word {

  @PrimaryKey(autoGenerate = true)
  private int id;
  @ColumnInfo(name =  "reading")
  private String reading;
  @ColumnInfo(name = "lesson_id")
  private int lessonId;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getReading() {
    return reading;
  }

  public void setReading(String Reading) {
    this.reading = reading;
  }

  public int getLessonId() {
    return lessonId;
  }

  public void setLessonId(int lessonId) {
    this.lessonId = lessonId;
  }
}
