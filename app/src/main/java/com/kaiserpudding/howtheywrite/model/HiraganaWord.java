package com.kaiserpudding.howtheywrite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import com.kaiserpudding.howtheywrite.util.ReadingsConverter;

@Entity(tableName = "hiraganas")
public class HiraganaWord extends Word {

  @ColumnInfo(name = "hiragana")
  private final String hiragana;

  public HiraganaWord(String hiragana) {
    this.hiragana = hiragana;
    setReading(ReadingsConverter.hiraganaToReading(hiragana));
  }

  public String getHiragana() {
    return hiragana;
  }
}
