package com.kaiserpudding.howtheywrite.util;

import android.widget.EditText;

/**
 * Util class to convert hiragana and katakana into their readings.
 * TODO add License https://github.com/MasterKale/WanaKanaJava
 */
public class ReadingsConverter {

  public static String hiraganaToReading(String hiragana) {
 /*   WanaKanaJava wkj = new WanaKanaJava(false);
    return wkj._hiraganaToRomaji(hiragana);*/
    return "a";
  }

  public static String katakanaToReading(String katakana) {
/*    WanaKanaJava wkj = new WanaKanaJava(false);
    return wkj._hiraganaToRomaji(wkj.toHiragana(katakana));*/
    return "a";
  }

}
