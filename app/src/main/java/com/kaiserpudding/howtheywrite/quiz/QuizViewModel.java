package com.kaiserpudding.howtheywrite.quiz;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import com.kaiserpudding.howtheywrite.model.Character;
import com.kaiserpudding.howtheywrite.repositories.CharacterRepository;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class QuizViewModel extends AndroidViewModel {

  private final CharacterRepository characterRepository;
  private final Executor executor;

  private List<Character> characters;
  private int currentCharacterIndex;
  private int charactersSize;

  public QuizViewModel(@NonNull Application application, int lessonId) {
    super(application);
    this.characterRepository = new CharacterRepository(application);
    this.executor = Executors.newCachedThreadPool();
    this.currentCharacterIndex = -1;
    executor.execute(() -> {
      this.characters = characterRepository.getCharacterByLessonId(lessonId);
      this.charactersSize = characters.size();
    });
  }

  public Character getNextWord() {
    //TODO multiple calls return different values
    currentCharacterIndex++;
    if (currentCharacterIndex >= charactersSize) {
      return null;
    }
    return characters.get(currentCharacterIndex);
  }

  public List<Character> getCharacters() {
    return characters;
  }
}
