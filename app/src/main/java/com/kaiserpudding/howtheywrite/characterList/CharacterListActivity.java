package com.kaiserpudding.howtheywrite.characterList;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.kaiserpudding.howtheywrite.R;
import com.kaiserpudding.howtheywrite.model.Character;
import java.util.LinkedList;
import java.util.List;

public class CharacterListActivity extends AppCompatActivity {

  private static final int NEW_CHAR_ACTIVITY_REQUEST_CODE = 2;

  private CharacterListViewModel characterListViewModel;

  @Override
  protected void onCreate(Bundle savedInstance) {
    super.onCreate(savedInstance);
    setContentView(R.layout.actvity_character_list);

    RecyclerView recyclerView = findViewById(R.id.character_recyclerview);
    final CharacterListAdapter adapter = new CharacterListAdapter(this);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new GridLayoutManager(this, 5));

    characterListViewModel = ViewModelProviders.of(this).get(CharacterListViewModel.class);
    characterListViewModel.getCharacters().observe(this, adapter::setCharacters);

    FloatingActionButton fab = findViewById(R.id.add_character_fab);
    fab.setOnClickListener(view -> {
      Intent intent = new Intent(CharacterListActivity.this, NewCharacterActivity.class);
      startActivityForResult(intent, NEW_CHAR_ACTIVITY_REQUEST_CODE);
    });
}

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == NEW_CHAR_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
      Character character = new Character(
          data.getStringExtra(NewCharacterActivity.REPLY_CHAR_HANZI),
          data.getStringExtra(NewCharacterActivity.REPLY_CHAR_HIRAGANA),
          null,
          data.getStringExtra(NewCharacterActivity.REPLY_CHAR_TRANSLATION),
          true
      );
      characterListViewModel.insertCharacter(character);
    }
  }
}
