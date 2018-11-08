//package com.kaiserpudding.howtheywrite.quiz;
//
//import android.app.Application;
//import android.arch.lifecycle.AndroidViewModel;
//import android.arch.lifecycle.LiveData;
//import android.arch.lifecycle.MutableLiveData;
//import android.support.annotation.NonNull;
//import com.kaiserpudding.howtheywrite.model.Character;
//import com.kaiserpudding.howtheywrite.model.Lesson;
//import com.kaiserpudding.howtheywrite.repositories.CharacterRepository;
//import com.kaiserpudding.howtheywrite.repositories.LessonRepository;
//import java.util.List;
//import java.util.concurrent.Executor;
//import java.util.concurrent.Executors;
//
//public class LessonViewModel extends AndroidViewModel {
//
//  private final LessonRepository lessonRepository;
//  private final CharacterRepository characterRepository;
//  private final Executor executor;
//
//  private LiveData<Lesson> lesson;
//  private LiveData<List<Character>> characters;
//
//  public LessonViewModel(@NonNull Application application) {
//    super(application);
//    lessonRepository = new LessonRepository(application);
//    characterRepository = new CharacterRepository(application);
//    executor = Executors.newCachedThreadPool();
//  }
//
//  public void insertCharacter(Character character) {
//    executor.execute(() -> {
//      characterRepository.insertCharacter(character);
//    });
//  }
//
//  public void deleteCharacter(Character character) {
//    executor.execute(() -> {
//      characterRepository.deleteCharacter(character);
//    });
//  }
//
//  public void initLessonAndCharacters(int lessonId) {
//    lesson = lessonRepository.getLiveDataLessonWithRelationById(lessonId);
//    characterRepository.getLiveDataCharacterByLessonId(lessonId);
//  }
//
//  public LiveData<Lesson> getLiveDataLesson() {
//    return lesson;
//  }
//}
