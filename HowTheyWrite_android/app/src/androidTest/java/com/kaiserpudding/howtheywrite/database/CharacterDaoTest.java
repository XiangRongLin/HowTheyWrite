package com.kaiserpudding.howtheywrite.database;

import static org.junit.Assert.*;

import androidx.room.Room;
import android.content.Context;
import androidx.test.InstrumentationRegistry;
import com.kaiserpudding.howtheywrite.database.dao.CharacterDao;
import com.kaiserpudding.howtheywrite.model.Character;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CharacterDaoTest {

  private AppDatabase db;
  private CharacterDao characterDao;

  /**
   * Setup
   */
  @Before
  public void setUp() {
    Context context = InstrumentationRegistry.getTargetContext();
    db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
        .allowMainThreadQueries()
        .build();
    characterDao = db.characterDao();
  }

  @After
  public void tearDown() {
    db.close();
  }

//  @Test
//  public void insertAndGetCharacter() {
//    Character character = new Character(null, "あ", "a", null, false);
//    int characterId = (int) characterDao.insert(character);
//    character.setId(characterId);
//
//    Character actualCharacter = characterDao.getCharacterById(characterId);
//
//    assertEquals(character.toString(), actualCharacter.toString());
//  }
//
//  @Test
//  public void insertAndDeleteCharacter() {
//    Character character = new Character(null, "あ", "a", null, false);
//    int characterId = (int) characterDao.insert(character);
//    character.setId(characterId);
//    characterDao.delete(character);
//
//    Character actualCharacter = characterDao.getCharacterById(characterId);
//
//    assertNull(actualCharacter);
//  }
//
//  @Test
//  public void insertAndUpdateCharacter() {
//    Character character = new Character(null, "あ", "a", null, false);
//    int characterId = (int) characterDao.insert(character);
//    character.setId(characterId);
//    character.setPinyin("う");
//    characterDao.updateCharacter(character);
//
//    Character actualCharacter = characterDao.getCharacterById(characterId);
//
//    assertEquals(character.toString(), actualCharacter.toString());
//  }
//
//  @Test
//  public void getCharactersByIds() {
//    Character characterA = new Character(null, "あ", "a", null, false);
//    int characterAId = (int) characterDao.insert(characterA);
//    characterA.setId(characterAId);
//    Character characterB = new Character(null, "う", "u", null, false);
//    int characterBId = (int) characterDao.insert(characterB);
//    characterB.setId(characterBId);
//    List<Character> characters = new LinkedList<Character>(Arrays.asList(characterA, characterB));
//
//    int[] ids = {characterAId, characterBId};
//    List<Character> actualCharacters = characterDao.getCharactersByIds(ids);
//
//    assertEquals(characters.toString(), actualCharacters.toString());
//  }
}