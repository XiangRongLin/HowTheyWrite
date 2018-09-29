package com.kaiserpudding.howtheywrite.database;

import static org.junit.Assert.*;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.kaiserpudding.howtheywrite.model.Progress;
import com.kaiserpudding.howtheywrite.model.User;
import com.kaiserpudding.howtheywrite.model.UserWithProgress;
import java.util.LinkedList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class UserDaoTest {

  private AppDatabase db;
  private UserDao userDao;
  private ProgressDao progressDao;

  /**
   * Setup
   */
  @Before
  public void setUp() {
    Context context = InstrumentationRegistry.getTargetContext();
    db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
        .allowMainThreadQueries()
        .build();
    userDao = db.userDao();
    progressDao = db.progressDao();
  }

  @After
  public void tearDown() {
    db.close();
  }

  @Test
  public void insertAndGetUser() {
    User user = new User();
    int userId = (int) userDao.insertUser(user);
    user.setId(userId);

    User actualUser = userDao.getUserById(userId);

    assertEquals(user.toString(), actualUser.toString());
  }

  @Test
  public void getUserWithSingleProgress() {
    User user = new User();
    int userId = (int) userDao.insertUser(user);
    user.setId(userId);
    Progress progress = new Progress(userId, 1);
    progressDao.insertProgress(progress);
    user.addProgress(progress);

    UserWithProgress actualUser = userDao.getUserWithProgressById(userId);

    assertEquals(user.toString(), actualUser.toString());
  }

  @Test
  public void getUserWithMultipleProgress() {
    User user = new User();
    int userId = (int) userDao.insertUser(user);
    user.setId(userId);
    Progress progressA = new Progress(userId, 1);
    progressDao.insertProgress(progressA);
    Progress progressB = new Progress(userId, 2);
    progressDao.insertProgress(progressB);

    UserWithProgress actualUser = userDao.getUserWithProgressById(userId);

    assertEquals(user.toString(), actualUser.toString());
  }
}
