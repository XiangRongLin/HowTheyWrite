package com.kaiserpudding.howtheywrite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;
import com.kaiserpudding.howtheywrite.util.KanaConverter;
import com.kaiserpudding.howtheywrite.util.RoomTypeConverter;
import java.util.LinkedList;
import java.util.List;

@Entity(tableName = "characters")
public class Character {

  @PrimaryKey(autoGenerate = true)
  private int id;
  @ColumnInfo(name = "kanji")
  private String kanji;
  @ColumnInfo(name = "hiragana")
  @NonNull
  private String hiragana;
  @ColumnInfo(name = "romanji")
  private String romanji;
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
   * @param kanji The kanji of the word
   * @param hiragana The hiragana of the word
   * @param translationKey The translationKey of the word. Can be Null
   * @param translation The translations of the word
   * @param isCustom Specifies whether user created or modified this word
   */
  public Character(String kanji, @NonNull String hiragana, String translationKey, String translation, boolean isCustom) {
    this.kanji = kanji;
    this.hiragana = hiragana;
    this.romanji = KanaConverter.hiraganaToReading(hiragana);
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
    return getId() + getKanji() + getHiragana();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getKanji() {
    if (kanji != null)
      return kanji;
    else return getHiragana();
  }

  public void setKanji(String kanji) {
    this.kanji = kanji;
  }

  @NonNull
  public String getHiragana() {
    return hiragana;
  }

  public void setHiragana(@NonNull String hiragana) {
    this.hiragana = hiragana;
  }

  public String getRomanji() {
    return romanji;
  }

  public void setRomanji(String romanji) {
    this.romanji = romanji;
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
