package com.kaiserpudding.howtheywrite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import com.kaiserpudding.howtheywrite.util.KanaConverter;
import java.util.LinkedList;
import java.util.List;

@Entity(tableName = "characters")
public class Character {

  @PrimaryKey(autoGenerate = true)
  private int id;
  @ColumnInfo(name = "hanzi")
  @NonNull
  private String hanzi;
  @ColumnInfo(name = "pinyin")
  @NonNull
  private String pinyin;
  @ColumnInfo(name = "translationKey")
  private String translationKey;
  @ColumnInfo(name = "translation")
  private String translation;
  @ColumnInfo(name = "isCustom")
  private boolean isCustom;
  @Ignore
  @NonNull
  private List<Lesson> lessons;
  @Embedded
  private Progress progress;

  /**
   * Constructor for a {@link Character}.
   * translationKey can be null.
   * translation can be an empty list.
   * @param hanzi The hanzi of the word
   * @param pinyin The pinyin of the word
   * @param translationKey The translationKey of the word. Can be Null
   * @param translation The translations of the word
   * @param isCustom Specifies whether user created or modified this word
   */
  public Character(@NonNull String hanzi, @NonNull String pinyin, String translationKey, String translation, boolean isCustom) {
    this.hanzi = hanzi;
    this.pinyin = pinyin;
    this.translationKey = translationKey;
    this.translation = translation;
    this.isCustom = isCustom;
    this.lessons = new LinkedList<>();
    this.progress = new Progress();
  }

  @Override
  @NonNull
  public String toString() {
    //TODO include translation and translationKey
    return getId() + getHanzi() + getPinyin();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @NonNull
  public String getHanzi() {
    return hanzi;
  }

  public void setHanzi(@NonNull String hanzi) {
    this.hanzi = hanzi;
  }

  @NonNull
  public String getPinyin() {
    return pinyin;
  }

  public void setPinyin(@NonNull String pinyin) {
    this.pinyin = pinyin;
  }

  public String getTranslationKey() {
    return translationKey;
  }

  public void setTranslationKey(String translationKey) {
    this.translationKey = translationKey;
  }

  public String getTranslation() {
    return translation;
  }

  public void setTranslation(String translation) {
    this.translation = translation;
  }

  public boolean isCustom() {
    return isCustom;
  }

  public void setCustom(boolean custom) {
    isCustom = custom;
  }

  @NonNull
  public List<Lesson> getLessons() {
    return lessons;
  }

  public void setLessons(@NonNull List<Lesson> lessons) {
    this.lessons = lessons;
  }

  public void addLesson(Lesson lesson) {
    this.lessons.add(lesson);
  }

  public void removeLesson(Lesson lesson) {
    this.lessons.remove(lesson);
  }

  public Progress getProgress() {
    return progress;
  }

  public void setProgress(Progress progress) {
    this.progress = progress;
  }
}
