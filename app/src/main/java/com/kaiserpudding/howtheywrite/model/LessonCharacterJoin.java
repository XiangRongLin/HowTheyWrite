package com.kaiserpudding.howtheywrite.model;

import static android.arch.persistence.room.ForeignKey.CASCADE;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;


/**
 * https://stackoverflow.com/questions/44361824/how-can-i-represent-a-many-to-many-relation-with-android-room
 * https://stackoverflow.com/questions/50799324/many-to-many-relations-with-room-livedata
 */
@Entity(
    tableName="lesson_character_join",
    primaryKeys={"lessonId", "wordId"},
    foreignKeys={
        @ForeignKey(
            entity=Lesson.class,
            parentColumns="id",
            childColumns="lessonId",
            onDelete=CASCADE),
        @ForeignKey(
            entity=Character.class,
            parentColumns="id",
            childColumns="wordId",
            onDelete=CASCADE)},
    indices={
        @Index(value="lessonId"),
        @Index(value="wordId")
    }
)
public class LessonCharacterJoin {

  private final int lessonId;
  private final int characterId;

  public LessonCharacterJoin(int lessonId, int wordId) {
    this.lessonId = lessonId;
    this.characterId= wordId;
  }

  @Override
  @NonNull
  public String toString() {
    return "LessonId: " + String.valueOf(getLessonId()) + "CharacterId: " + String.valueOf(getCharacterId());
  }

  public int getLessonId() {
    return lessonId;
  }

  public int getCharacterId() {
    return characterId;
  }
}
