package com.kaiserpudding.howtheywrite.util;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class KanaConverterTest {


  @Test
  public void hiraganaToReading1() {
    String hiragana = "あ";

    String actualReading = KanaConverter.hiraganaToReading(hiragana);
    String expectedReading = "a";

    Assert.assertEquals(expectedReading, actualReading);
  }

  @Test
  public void hiraganaToReading2() {
    String hiragana = "なっと";

    String actualReading = KanaConverter.hiraganaToReading(hiragana);
    String expectedReading = "natto";

    Assert.assertEquals(expectedReading, actualReading);
  }

  @Test
  public void hiraganaToReading3() {
    String hiragana = "りょ";

    String actualReading = KanaConverter.hiraganaToReading(hiragana);
    String expectedReading = "ryo";

    Assert.assertEquals(expectedReading, actualReading);
  }

  @Test
public void hiraganaToReading4() {
    String hiragana = "が";

    String actualReading = KanaConverter.hiraganaToReading(hiragana);
    String expectedReading = "ga";

    assertEquals(expectedReading, actualReading);
  }

  @Test
  public void katakanaToReading1() {
    String katakana = "ア";

    String actualReading = KanaConverter.katakanaToReading(katakana);
    String expectedReading = "a";

    Assert.assertEquals(expectedReading, actualReading);
  }

  @Test
  public void katakanaToReading2() {
    String katakana = "ナット";

    String actualReading = KanaConverter.katakanaToReading(katakana);
    String expectedReading = "natto";

    Assert.assertEquals(expectedReading, actualReading);
  }

  @Test
  public void katakanaToReading3() {
    String katakana = "リョ";

    String actualReading = KanaConverter.katakanaToReading(katakana);
    String expectedReading = "ryo";

    Assert.assertEquals(expectedReading, actualReading);
  }

  @Test
  public void katakanaToReading4() {
    String katakana = "ガ";

    String actualReading = KanaConverter.katakanaToReading(katakana);
    String expectedReading = "ga";

    Assert.assertEquals(expectedReading, actualReading);
  }

  @Test
  public void isKanji1() {
    String kanji = "人";

    Assert.assertTrue(KanaConverter.isKanji(kanji));
  }

  @Test
  public void isKanji2() {
    String kanji = "画稿";

    Assert.assertTrue(KanaConverter.isKanji(kanji));
  }

  @Test
  public void isKanji3() {
    String kanji = "会社員";

    Assert.assertTrue(KanaConverter.isKanji(kanji));
  }

  @Test
  public void isKanji4() {
    String kanji = "私";

    Assert.assertTrue(KanaConverter.isKanji(kanji));
  }
}