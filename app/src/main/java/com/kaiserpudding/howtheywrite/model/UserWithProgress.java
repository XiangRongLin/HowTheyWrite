package com.kaiserpudding.howtheywrite.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;
import android.support.annotation.NonNull;
import java.util.List;

public class UserWithProgress {

  @Embedded
  private User user;

  @Relation(parentColumn = "id", entityColumn = "user_id", entity = Progress.class)
  private List<Progress> progress;

  @Override
  @NonNull
  public String toString() {
    return String.valueOf(user.getId());
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public List<Progress> getProgress() {
    return progress;
  }

  public void setProgress(List<Progress> progress) {
    this.progress = progress;
  }
}
