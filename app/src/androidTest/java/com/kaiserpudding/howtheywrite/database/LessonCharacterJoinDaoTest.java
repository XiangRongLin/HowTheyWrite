package com.kaiserpudding.howtheywrite.database;

import static org.junit.Assert.*;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import com.kaiserpudding.howtheywrite.util.InstantTaskExecutorRule;
import com.kaiserpudding.howtheywrite.model.CharacterWord;
import com.kaiserpudding.howtheywrite.model.Lesson;
import com.kaiserpudding.howtheywrite.model.LessonWordJoin;
import com.kaiserpudding.howtheywrite.util.LiveDataTestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class LessonWordJoinDaoTest {

  @Rule
  public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

  private AppDatabase db;
  private CharacterWordDao hiraganaWordDao;
  private LessonDao lessonDao;
  private LessonWordJoinDao lessonWordJoinDao;

  /**
   * Setup
   */
  @Before
  public void setUp() {
    Context context = InstrumentationRegistry.getTargetContext();
    db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
        .allowMainThreadQueries()
        .build();
    hiraganaWordDao = db.characterWordDao();
    lessonDao = db.lessonDao();
    lessonWordJoinDao = db.lessonWordJoinDao();
  }

  @After
  public void tearDown() {
    db.close();
  }

  @Test
  public void insertAndGetLessonWordJoin() {
    CharacterWord hiraganaWord = new CharacterWord(null, "あ", "a", null, false);
    int hiraganaId = (int) hiraganaWordDao.insertCharacter(hiraganaWord);
    Lesson lesson = new Lesson("basics");
    int lessonId = (int) lessonDao.insertLesson(lesson);
    LessonWordJoin lessonWordJoin = new LessonWordJoin(lessonId, hiraganaId);
    lessonWordJoinDao.insertLessonWordJoin(lessonWordJoin);

    LiveData<LessonWordJoin> actualLessonWordJoin = lessonWordJoinDao.getAllLessonWordJoin();

    assertEquals(lessonWordJoin.toString(), LiveDataTestUtil.getValue(actualLessonWordJoin).toString());
  }

  @Test
  public void deleteLessonWordJoin() {
  }

  @Test
  public void updateLessonWordJoin() {
  }

  @Test
  public void getWordsWithLessonId() {
  }
}