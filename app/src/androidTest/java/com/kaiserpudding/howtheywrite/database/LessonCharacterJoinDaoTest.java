package com.kaiserpudding.howtheywrite.database;

import static org.junit.Assert.*;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import com.kaiserpudding.howtheywrite.model.Character;
import com.kaiserpudding.howtheywrite.util.InstantTaskExecutorRule;
import com.kaiserpudding.howtheywrite.model.Lesson;
import com.kaiserpudding.howtheywrite.model.LessonCharacterJoin;
import java.util.List;
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
    Character character = new Character(null, "あ", "a", false);
    int characterId = (int) characterDao.insertCharacter(character);
    Lesson lesson = new Lesson("basics");
    int lessonId = (int) lessonDao.insertLesson(lesson);
    LessonCharacterJoin lessonCharacterJoin = new LessonCharacterJoin(lessonId, characterId);
    lessonCharacterJoinDao.insertLessonCharacterJoin(lessonCharacterJoin);

    List<LessonCharacterJoin> actualLessonCharacterJoin = lessonCharacterJoinDao.getAllLessonCharacterJoin();

    assertEquals(lessonCharacterJoin.toString(), actualLessonCharacterJoin.get(0).toString());
  }

  @Test
  public void insertAndDeleteLessonCharacterJoin() {
    Character character = new Character(null, "あ", "a", false);
    int characterId = (int) characterDao.insertCharacter(character);
    Lesson lesson = new Lesson("basics");
    int lessonId = (int) lessonDao.insertLesson(lesson);
    LessonCharacterJoin lessonCharacterJoin = new LessonCharacterJoin(lessonId, characterId);
    lessonCharacterJoinDao.insertLessonCharacterJoin(lessonCharacterJoin);
    lessonCharacterJoinDao.deleteLessonCharacterJoin(lessonCharacterJoin);

    List<LessonCharacterJoin> actualLessonCharacterJoin = lessonCharacterJoinDao.getAllLessonCharacterJoin();

    assertEquals(0, actualLessonCharacterJoin.size());
  }

  @Test
  public void getAllEmptyDatabase() {
    List<LessonCharacterJoin> actualLessonCharacterJoin = lessonCharacterJoinDao.getAllLessonCharacterJoin();

    assertEquals(0, actualLessonCharacterJoin.size());
  }

  @Test
  public void getLessonCharacterJoinByLessonIdAndCharacterId() {
    Character character = new Character(null, "あ", "a", false);
    int characterId = (int) characterDao.insertCharacter(character);
    Lesson lesson = new Lesson("basics");
    int lessonId = (int) lessonDao.insertLesson(lesson);
    LessonCharacterJoin lessonCharacterJoin = new LessonCharacterJoin(lessonId, characterId);
    lessonCharacterJoinDao.insertLessonCharacterJoin(lessonCharacterJoin);

    LessonCharacterJoin actualLessonCharacterJoin = lessonCharacterJoinDao.getLessonCharacterJoin(lessonId, characterId);

    assertEquals(lessonCharacterJoin.toString(), actualLessonCharacterJoin.toString());
  }
}
