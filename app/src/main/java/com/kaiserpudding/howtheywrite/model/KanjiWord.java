package com.kaiserpudding.howtheywrite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import com.kaiserpudding.howtheywrite.util.ReadingsConverter;

@Entity(tableName = "kanjis")
public class KanjiWord extends Word {

  @ColumnInfo(name = "kanji")
  private String kanji;
  @ColumnInfo(name = "hiragana")
  private String hiragana;
  @ColumnInfo(name = "translationKey")
  private String translationKey;
  @ColumnInfo(name = "translation")
  private String translation;
  @ColumnInfo(name = "isCustom")
  private boolean isCustom;

  public KanjiWord(String kanji, String hiragana, String translationKey) {
    this.kanji = kanji;
    this.hiragana = hiragana;
    this.translationKey = translationKey;
    setReading(ReadingsConverter.hiraganaToReading(hiragana));
    this.translation = "";
    this.isCustom = false;
  }

  @Ignore
  public KanjiWord(String kanji, String hiragana, String translation, boolean isCustom) {
    this.kanji = kanji;
    this.hiragana = hiragana;
    this.translationKey = "";
    setReading(ReadingsConverter.hiraganaToReading(hiragana));
    this.translation = translation;
    this.isCustom = isCustom;
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
}
