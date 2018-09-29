package com.kaiserpudding.howtheywrite.util;

import android.arch.persistence.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

/**
 * TypeConverter class for room. Needed for saving not compatible data types in database.
 */
public class RoomTypeConverter {

  @TypeConverter
  public static String stringListToJson(List<String> list) {
    return new Gson().toJson(list);
  }

  @TypeConverter
  public static List<String> jsonToStringList(String json) {
    Type type = new TypeToken<List<String>>() {}.getType();
    return new Gson().fromJson(json, type);
  }
}
