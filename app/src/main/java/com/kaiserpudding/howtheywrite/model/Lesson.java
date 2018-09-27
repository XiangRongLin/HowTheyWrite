package com.kaiserpudding.howtheywrite.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "lessons")
public class  Lesson {

  @PrimaryKey(autoGenerate = true)
  private int id;
  private String name;

  public Lesson(int id, @NonNull String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


}
