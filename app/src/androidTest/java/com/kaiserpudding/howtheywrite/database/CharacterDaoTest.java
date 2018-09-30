package com.kaiserpudding.howtheywrite.database;

import static org.junit.Assert.*;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import com.kaiserpudding.howtheywrite.model.Character;
import com.kaiserpudding.howtheywrite.util.LiveDataTestUtil;
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

  @Test
  public void insertAndGetCharacter() {
    Character character = new Character(null, "あ", "a", null, false);
    int characterId = (int) characterDao.insertCharacter(character);
    character.setId(characterId);

    Character actualCharacter = characterDao.getCharacterById(characterId);

    assertEquals(character.toString(), actualCharacter.toString());
  }

  @Test
  public void insertAndDeleteCharacter() {
    Character character = new Character(null, "あ", "a", null, false);
    int characterId = (int) characterDao.insertCharacter(character);
    character.setId(characterId);
    characterDao.deleteCharacter(character);

    Character actualCharacter = characterDao.getCharacterById(characterId);

    assertNull(actualCharacter);
  }

  @Test
  public void insertAndUpdateCharacter() {
    Character character = new Character(null, "あ", "a", null, false);
    int characterId = (int) characterDao.insertCharacter(character);
    character.setId(characterId);
    character.setHiragana("う");
    characterDao.updateCharacter(character);

    Character actualCharacter = characterDao.getCharacterById(characterId);

    assertEquals(character.toString(), actualCharacter.toString());
  }

  @Test
  public void getCharactersByIds() {
    Character characterA = new Character(null, "あ", "a", null, false);
    int characterAId = (int) characterDao.insertCharacter(characterA);
    characterA.setId(characterAId);
    Character characterB = new Character(null, "う", "u", null, false);
    int characterBId = (int) characterDao.insertCharacter(characterB);
    characterB.setId(characterBId);
    List<Character> characters = new LinkedList<Character>(Arrays.asList(characterA, characterB));

    int[] ids = {characterAId, characterBId};
    List<Character> actualCharacters = characterDao.getCharactersByIds(ids);

    assertEquals(characters.toString(), actualCharacters.toString());
  }
}