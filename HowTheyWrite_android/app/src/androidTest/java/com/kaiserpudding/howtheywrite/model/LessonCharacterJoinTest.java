/*
package com.kaiserpudding.howtheywrite.model;

import static org.junit.Assert.assertEquals;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import com.kaiserpudding.howtheywrite.database.AppDatabase;
import com.kaiserpudding.howtheywrite.database.CharacterDao;
import com.kaiserpudding.howtheywrite.database.LessonCharacterJoinDao;
import com.kaiserpudding.howtheywrite.database.LessonDao;
import com.kaiserpudding.howtheywrite.util.LiveDataTestUtil;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LessonCharacterJoinTest {

  private AppDatabase db;
  private CharacterDao characterDao;
  private LessonDao lessonDao;
  private LessonCharacterJoinDao lessonCharacterJoinDao;

  */
/**
   * Setup
   *//*

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
  public void relationIsUnique() {
    Character character = new Character(null, "あ", "a", null, false);
    int characterId = (int) characterDao.insertCharacter(character);
    Lesson lesson = new Lesson("basics");
    int lessonId = (int) lessonDao.insertLesson(lesson);
    LessonCharacterJoin lessonCharacterJoinA = new LessonCharacterJoin(lessonId, characterId);
    lessonCharacterJoinDao.insertLessonCharacterJoin(lessonCharacterJoinA);
    LessonCharacterJoin lessonCharacterJoinB = new LessonCharacterJoin(lessonId, characterId);
    lessonCharacterJoinDao.insertLessonCharacterJoin(lessonCharacterJoinB);

    List<LessonCharacterJoin> actualLessonCharacterJoin = LiveDataTestUtil.getValue(
        lessonCharacterJoinDao.getAllLessonCharacterJoin());

    assertEquals(1, ((List) actualLessonCharacterJoin).size());
  }
}
*/
