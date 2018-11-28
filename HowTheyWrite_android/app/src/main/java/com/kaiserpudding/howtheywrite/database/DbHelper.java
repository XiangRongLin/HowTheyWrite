/*
package com.kaiserpudding.howtheywrite.database;

import android.content.Context;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteDatabase.CursorFactory;
import net.sqlcipher.database.SQLiteOpenHelper;


public class DbHelper extends SQLiteOpenHelper {

  private final static String DB_PATH = "/data/data/com.kaiserpudding.howtheywrite/databases/";
  private final static String DB_NAME = "howTheyLearn_database_cn";

  private SQLiteDatabase db;
  private Context context;

  public DbHelper(Context context) {
    //TODO proper versioning of db.
    super(context, DB_NAME, null, 1);
    this.context = context;
  }

  public void createDatabase() {
    try {
      InputStream inputStream = context.getAssets().open("assets/databases/" + DB_NAME);

      File outputFile = new File(context.getDatabasePath(DB_NAME).getPath());
      FileOutputStream outputStream = new FileOutputStream(outputFile);

      inputStream.
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.out.print("\nError, outputfile does not exist");
    } catch (IOException e) {
      e.printStackTrace();
      System.out.print("\nError, inputfile in assets folder does not exist");
    }
  }

  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {

  }

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

  }
}
*/
