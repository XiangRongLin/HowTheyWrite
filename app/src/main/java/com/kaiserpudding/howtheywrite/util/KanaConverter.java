package com.kaiserpudding.howtheywrite.util;

/**
 * Util class to convert hiragana and katakana into their readings.
 * TODO add License https://github.com/MasterKale/WanaKanaJava
 */
public class KanaConverter {

  //104 Kana each

  public static String hiraganaToReading(String hiragana) {
    WanaKana wkj = new WanaKana(false);
    return wkj._hiraganaToRomaji(hiragana);
  }

  public static String katakanaToReading(String katakana) {
    WanaKana wkj = new WanaKana(false);
    return wkj._hiraganaToRomaji(wkj.toHiragana(katakana));
  }

}
