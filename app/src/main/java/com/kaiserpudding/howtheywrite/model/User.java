package com.kaiserpudding.howtheywrite.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import java.util.LinkedList;
import java.util.List;

@Entity
public class User {

  @PrimaryKey(autoGenerate = true)
  private int id;

  @Ignore
  @NonNull
  private List<Progress> progresses;

  public User() {
    this.progresses = new LinkedList<Progress>();
  }

  @Override
  @NonNull
  public String toString() {
    return String.valueOf(getId());
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public List<Progress> getProgresses() {
    return progresses;
  }

  public void setProgresses(List<Progress> progress) {
    this.progresses = progress;
  }

  public void addProgress(Progress progress) {
    this.progresses.add(progress);
  }

  public void removeProgress(Progress progress) {
    this.progresses.remove(progress);
  }
}
