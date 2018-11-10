/*
package com.kaiserpudding.howtheywrite.model;

import static android.arch.persistence.room.ForeignKey.CASCADE;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;


*/
/**
 * https://stackoverflow.com/questions/44361824/how-can-i-represent-a-many-to-many-relation-with-android-room
 * https://stackoverflow.com/questions/50799324/many-to-many-relations-with-room-livedata
 * This POJO holds the n:m relation between lessons and characters
 *//*

@Entity(
    tableName = "lesson_character_join",
    primaryKeys = {"lessonId", "characterId"},
    foreignKeys = {
        @ForeignKey(
            entity=Lesson.class,
            parentColumns="id",
            childColumns="lessonId",
            onDelete=CASCADE),
        @ForeignKey(
            entity=Character.class,
            parentColumns="id",
            childColumns="characterId",
            onDelete=CASCADE)},
    indices = {
        @Index(value = {"lessonId", "characterId"}, unique = true),
        @Index(value = "characterId")
    }
)
public class LessonCharacterJoin {

  private final int lessonId;
  private final int characterId;

  public LessonCharacterJoin_jp(int lessonId, int characterId) {
    this.lessonId = lessonId;
    this.characterId= characterId;
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
*/
