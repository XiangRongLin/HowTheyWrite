package com.kaiserpudding.howtheywrite.database;

import static org.junit.Assert.*;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.kaiserpudding.howtheywrite.util.LiveDataTestUtil;
import java.util.LinkedList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.kaiserpudding.howtheywrite.model.Lesson;

@RunWith(AndroidJUnit4.class)
public class LessonDaoTest {

  private AppDatabase db;
  private LessonDao lessonDao;

  /**
   * Setup
   */
  @Before
  public void setUp() {
    Context context = InstrumentationRegistry.getTargetContext();
    db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
        .allowMainThreadQueries()
        .build();
    lessonDao = db.lessonDao();
  }

  @After
  public void tearDown() {
    db.close();
  }

  @Test
  public void insertAndGetLesson() {
    Lesson lesson = new Lesson("beginner");
    int id = (int) lessonDao.insertLesson(lesson);
    lesson.setId(id);

    Lesson actualLesson = lessonDao.getLessonByName("beginner");

    assertEquals(lesson.toString(), actualLesson.toString());
  }

  @Test
  public void getLessonNames() {
    Lesson lesson1 = new Lesson("a");
    Lesson lesson2 = new Lesson("b");
    Lesson lesson3 = new Lesson("c");
    Lesson lesson4 = new Lesson("d");
    Lesson lesson5 = new Lesson("e");
    lessonDao.insertLesson(lesson1);
    lessonDao.insertLesson(lesson2);
    lessonDao.insertLesson(lesson3);
    lessonDao.insertLesson(lesson4);
    lessonDao.insertLesson(lesson5);

    List<String> expectedLessonNames = new LinkedList<>();
    expectedLessonNames.add("a");
    expectedLessonNames.add("b");
    expectedLessonNames.add("c");
    expectedLessonNames.add("d");
    expectedLessonNames.add("e");
    List<String> actualLessonNames = lessonDao.getAllLessonNames();

    assertEquals(expectedLessonNames, actualLessonNames);

  }
}
