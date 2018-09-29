package com.kaiserpudding.howtheywrite.database;

import static org.junit.Assert.*;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import com.kaiserpudding.howtheywrite.model.Character;
import com.kaiserpudding.howtheywrite.util.InstantTaskExecutorRule;
import com.kaiserpudding.howtheywrite.model.Character;
import com.kaiserpudding.howtheywrite.model.Lesson;
import com.kaiserpudding.howtheywrite.model.LessonCharacterJoin;
import com.kaiserpudding.howtheywrite.util.LiveDataTestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class LessonCharacterJoinDaoTest {

  @Rule
  public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

  private AppDatabase db;
  private CharacterDao characterDao;
  private LessonDao lessonDao;
  private LessonCharacterJoinDao lessonCharacterJoinDao;

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
    lessonDao = db.lessonDao();
    lessonCharacterJoinDao = db.lessonCharacterJoinJoinDao();
  }

  @After
  public void tearDown() {
    db.close();
  }

  @Test
  public void insertAndGetLessonCharacterJoin() {
    Character character = new Character(null, "あ", "a", null, false);
    int characterId = (int) characterDao.insertCharacter(character);
    Lesson lesson = new Lesson("basics");
    int lessonId = (int) lessonDao.insertLesson(lesson);
    LessonCharacterJoin lessonCharacterJoin = new LessonCharacterJoin(lessonId, characterId);
    lessonCharacterJoinDao.insertLessonCharacterJoin(lessonCharacterJoin);

    LessonCharacterJoin actualLessonCharacterJoin = LiveDataTestUtil.getValue(
        lessonCharacterJoinDao.getAllLessonCharacterJoin());

    assertEquals(lessonCharacterJoin.toString(), actualLessonCharacterJoin.toString());
  }

  @Test
  public void insertAndDeleteLessonCharacterJoin() {
    Character character = new Character(null, "あ", "a", null, false);
    int characterId = (int) characterDao.insertCharacter(character);
    Lesson lesson = new Lesson("basics");
    int lessonId = (int) lessonDao.insertLesson(lesson);
    LessonCharacterJoin lessonCharacterJoin = new LessonCharacterJoin(lessonId, characterId);
    lessonCharacterJoinDao.insertLessonCharacterJoin(lessonCharacterJoin);
    lessonCharacterJoinDao.deleteLessonCharacterJoin(lessonCharacterJoin);

    LessonCharacterJoin actualLessonCharacterJoin = LiveDataTestUtil.getValue(
        lessonCharacterJoinDao.getAllLessonCharacterJoin());

    assertNull(actualLessonCharacterJoin);
  }

  @Test
  public void getAllEmptyDatabase() {
    LessonCharacterJoin actualLessonCharacterJoin = LiveDataTestUtil.getValue(
        lessonCharacterJoinDao.getAllLessonCharacterJoin());

    assertNull(actualLessonCharacterJoin);
  }

  @Test
  public void getLessonCharacterJoinByLessonIdAndCharacterId() {
    Character character = new Character(null, "あ", "a", null, false);
    int characterId = (int) characterDao.insertCharacter(character);
    Lesson lesson = new Lesson("basics");
    int lessonId = (int) lessonDao.insertLesson(lesson);
    LessonCharacterJoin lessonCharacterJoin = new LessonCharacterJoin(lessonId, characterId);
    lessonCharacterJoinDao.insertLessonCharacterJoin(lessonCharacterJoin);

    LessonCharacterJoin actualLessonCharacterJoin = LiveDataTestUtil.getValue(
        lessonCharacterJoinDao.getLessonCharacterJoin(lessonId, characterId));

    assertEquals(lessonCharacterJoin.toString(), actualLessonCharacterJoin.toString());
  }

  @Test
  public void getLivaDataCharacterByLessonId() {
    Character character = new Character(null, "あ", "a", null, false);
    int characterId = (int) characterDao.insertCharacter(character);
    character.setId(characterId);
    Lesson lesson = new Lesson("basics");
    int lessonId = (int) lessonDao.insertLesson(lesson);
    lesson.setId(lessonId);
    LessonCharacterJoin lessonCharacterJoin = new LessonCharacterJoin(lessonId, characterId);
    lessonCharacterJoinDao.insertLessonCharacterJoin(lessonCharacterJoin);

    Character actualCharacter = LiveDataTestUtil.getValue(
        lessonCharacterJoinDao.getLivaDataCharactersByLessonId(lessonId)).get(0);

    assertEquals(character.toString(), actualCharacter.toString());
  }

  @Test
  public void getCharacterByLessonId() {
    Character character = new Character(null, "あ", "a", null, false);
    int characterId = (int) characterDao.insertCharacter(character);
    character.setId(characterId);
    Lesson lesson = new Lesson("basics");
    int lessonId = (int) lessonDao.insertLesson(lesson);
    lesson.setId(lessonId);
    LessonCharacterJoin lessonCharacterJoin = new LessonCharacterJoin(lessonId, characterId);
    lessonCharacterJoinDao.insertLessonCharacterJoin(lessonCharacterJoin);

    Character actualCharacter = lessonCharacterJoinDao.getCharactersByLessonId(lessonId).get(0);

    assertEquals(character.toString(), actualCharacter.toString());
  }

  @Test
  public void getLiveDataLessonByCharacterId() {
    Character character = new Character(null, "あ", "a", null, false);
    int characterId = (int) characterDao.insertCharacter(character);
    Lesson lesson = new Lesson("basics");
    int lessonId = (int) lessonDao.insertLesson(lesson);
    lesson.setId(lessonId);
    LessonCharacterJoin lessonCharacterJoin = new LessonCharacterJoin(lessonId, characterId);
    lessonCharacterJoinDao.insertLessonCharacterJoin(lessonCharacterJoin);

    Lesson actualLesson = LiveDataTestUtil.getValue(
        lessonCharacterJoinDao.getLiveDataLessonsByCharacterId(characterId)).get(0);

    assertEquals(lesson.toString(), actualLesson.toString());
  }

  @Test
  public void getLessonByCharacterId() {
    Character character = new Character(null, "あ", "a", null, false);
    int characterId = (int) characterDao.insertCharacter(character);
    Lesson lesson = new Lesson("basics");
    int lessonId = (int) lessonDao.insertLesson(lesson);
    lesson.setId(lessonId);
    LessonCharacterJoin lessonCharacterJoin = new LessonCharacterJoin(lessonId, characterId);
    lessonCharacterJoinDao.insertLessonCharacterJoin(lessonCharacterJoin);

    Lesson actualLesson = lessonCharacterJoinDao.getLessonsByCharacterId(characterId).get(0);

    assertEquals(lesson.toString(), actualLesson.toString());
  }
}