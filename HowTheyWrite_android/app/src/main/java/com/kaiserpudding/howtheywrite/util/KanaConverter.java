package com.kaiserpudding.howtheywrite.util;

import java.lang.Character.UnicodeBlock;

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


  /**
   * Return true if kanji is entirely Kanji
   */
  public static boolean isKanji(String kanji) {
    for (char ch: kanji.toCharArray()) {
      Character.UnicodeBlock unicode = UnicodeBlock.of(ch);
      if (unicode != UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
          && unicode != UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
          && unicode != UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
          && unicode != UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C
          && unicode != UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D
          ) {
        return false;
      }
    }
    return true;
  }

  public static boolean isKana(String kana) {
    WanaKana wk = new WanaKana(false);
    return wk.isKana(kana);
  }

  public static boolean isJapanese(String japanese) {
    for (char ch: japanese.toCharArray()) {
      String tmp = Character.toString(ch);
      if (isKanji(tmp) && isKana(tmp)) {
        return false;
      }
    }
    return true;
  }
}
