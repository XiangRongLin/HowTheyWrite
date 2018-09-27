package com.kaiserpudding.howtheywrite;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.kaiserpudding.howtheywrite.database.AppDatabase;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    AppDatabase database = Room.databaseBuilder(getApplicationContext(),
        AppDatabase.class, "database-name").build();
  }
}
