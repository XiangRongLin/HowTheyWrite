package com.kaiserpudding.howtheywrite.model;

import static android.arch.persistence.room.ForeignKey.CASCADE;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

@Entity(
    tableName="lesson_word_join",
    primaryKeys={"lessonId", "wordId"},
    foreignKeys={
        @ForeignKey(
            entity=Lesson.class,
            parentColumns="id",
            childColumns="lessonId",
            onDelete=CASCADE),
        @ForeignKey(
            entity=Word.class,
            parentColumns="id",
            childColumns="wordId",
            onDelete=CASCADE)},
    indices={
        @Index(value="lessonId"),
        @Index(value="wordId")
    }
)
public class LessonWordJoin {

  private final String lessonId;
  private final int wordId;

  public LessonWordJoin(String lessonId, int wordId) {
    this.lessonId = lessonId;
    this.wordId = wordId;
  }

  public String getLessonId() {
    return lessonId;
  }

  public int getWordId() {
    return wordId;
  }
}
