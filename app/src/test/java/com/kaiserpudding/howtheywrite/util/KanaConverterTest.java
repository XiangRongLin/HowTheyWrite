package com.kaiserpudding.howtheywrite.util;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class ReadingsConverterTest {


  @Test
  public void hiraganaToReading() {
    String hiragana = "あ";

    String actualReading = ReadingsConverter.hiraganaToReading(hiragana);
    String expectedReading = "a";

    Assert.assertEquals(expectedReading, actualReading);
  }

  @Test
  public void katakanaToReading() {
  }
}