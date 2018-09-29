package com.kaiserpudding.howtheywrite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import com.kaiserpudding.howtheywrite.util.ReadingsConverter;
import com.kaiserpudding.howtheywrite.util.RoomTypeConverter;
import java.util.List;

@Entity(tableName = "kanjis")
public class Kanji {

  @PrimaryKey(autoGenerate = true)
  private int id;
  @ColumnInfo(name = "kanji")
  private String kanji;
  @ColumnInfo(name = "hiragana")
  private String hiragana;
  @ColumnInfo(name = "romanji")
  private String romanji;
  @ColumnInfo(name = "translationKey")
  private String translationKey;
  @ColumnInfo(name = "translation")
  @TypeConverters(RoomTypeConverter.class)
  private List<String> translation;
  @ColumnInfo(name = "isCustom")
  private boolean isCustom;

  /**
   * Contructor for a {@link Kanji}.
   * Either translationKey or translation can be null.
   * @param kanji The kanji of the word
   * @param hiragana The hiragana of the word
   * @param translationKey The translationKey of the word. Can be Null
   * @param translation The translations of the word
   * @param isCustom Specifies whether user created or modified this word
   */
  public Kanji(String kanji, String hiragana, String translationKey, List<String> translation, boolean isCustom) {
    this.kanji = kanji;
    this.hiragana = hiragana;
    this.translationKey = translationKey;
    setRomanji(ReadingsConverter.hiraganaToReading(hiragana));
    this.translation = translation;
    this.isCustom = false;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getKanji() {
    return kanji;
  }

  public void setKanji(String kanji) {
    this.kanji = kanji;
  }

  public String getHiragana() {
    return hiragana;
  }

  public void setHiragana(String hiragana) {
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

  public List<String> getTranslation() {
    return translation;
  }

  public void setTranslation(List<String> translation) {
    this.translation = translation;
  }

  public boolean isCustom() {
    return isCustom;
  }

  public void setCustom(boolean custom) {
    isCustom = custom;
  }
}
