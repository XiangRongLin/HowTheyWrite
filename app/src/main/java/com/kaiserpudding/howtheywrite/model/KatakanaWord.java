package com.kaiserpudding.howtheywrite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import com.kaiserpudding.howtheywrite.util.ReadingsConverter;
import java.lang.ProcessBuilder.Redirect;

@Entity(tableName = "katakanas")
public class KatakanaWord extends Word {

  @ColumnInfo(name = "katakana")
  private final String katakana;

  public KatakanaWord(String katakana) {
    this.katakana = katakana;
    setReading(ReadingsConverter.katakanaToReading(katakana));
  }

  public String getKatakana() {
    return katakana;
  }
}
