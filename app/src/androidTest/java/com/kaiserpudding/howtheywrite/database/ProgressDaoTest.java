package com.kaiserpudding.howtheywrite.database;

import static org.junit.Assert.*;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import com.kaiserpudding.howtheywrite.model.Progress;
import com.kaiserpudding.howtheywrite.util.LiveDataTestUtil;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProgressDaoTest {

  private AppDatabase db;
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
    progressDao = db.progressDao();
  }

  @After
  public void tearDown() {
    db.close();
  }

  @Test
  public void insertAndGetProgress() {
    Progress progress = new Progress(1);
    int progressId = (int) progressDao.insertProgress(progress);
    progress.setId(progressId);

    Progress actualProgress = LiveDataTestUtil.getValue(progressDao.getProgressById(progressId));

    assertEquals(progress.toString(), actualProgress.toString());
  }

  @Test
  public void insertAndDeleteProgress() {
    Progress progress = new Progress(1);
    int progressId = (int) progressDao.insertProgress(progress);
    progress.setId(progressId);
    progressDao.deleteProgress(progress);

    Progress actualProgress = LiveDataTestUtil.getValue(progressDao.getProgressById(progressId));

    assertNull(actualProgress);
  }

  @Test
  public void insertAndUpdateProgress() {
    Progress progress = new Progress(1);
    int progressId = (int) progressDao.insertProgress(progress);
    progress.setId(progressId);
    progress.setValue(5);
    progressDao.updateProgress(progress);

    Progress actualProgress = LiveDataTestUtil.getValue(progressDao.getProgressById(progressId));

    assertEquals(progress.toString(), actualProgress.toString());
  }

  @Test
  public void getProgressesByCharacterIds() {
    Progress progressA = new Progress(1);
    int progressAId = (int) progressDao.insertProgress(progressA);
    progressA.setId(progressAId);
    Progress progressB = new Progress(2);
    int progressBId = (int) progressDao.insertProgress(progressB);
    progressB.setId(progressBId);
    List<Progress> progresses = new LinkedList<Progress>(Arrays.asList(progressA, progressB));

    int[] characterIds = {1, 2};
    List<Progress> actualProgresses = LiveDataTestUtil.getValue(progressDao.getProgressesByCharacterIds(characterIds));

    assertEquals(progresses.toString(), actualProgresses.toString());
  }
}