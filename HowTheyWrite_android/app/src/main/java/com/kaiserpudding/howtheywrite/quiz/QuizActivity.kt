package com.kaiserpudding.howtheywrite.quiz

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.characterDetail.CharacterDetailFragment
import com.kaiserpudding.howtheywrite.model.Character

class QuizActivity : AppCompatActivity(),
        QuizFragment.OnQuizFragmentInteractionListener,
        CharacterDetailFragment.OnCharacterDetailFragmentInteractionListener{

    private val manager: FragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val lessonId = intent.getIntExtra(LESSON_ID, 0)
        val characterIds = intent.getIntArrayExtra(CHARACTER_IDS)

        //add quizFragment
        val transaction = manager.beginTransaction()
        transaction.add(R.id.quiz_fragment_container,
                QuizFragment.newInstance(lessonId, characterIds))
        transaction.commit()
    }

    override fun onQuizFragmentInteraction(character: Character) {
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.quiz_fragment_container,
                CharacterDetailFragment.newInstance(character.id))
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCharacterDetailFragmentInteraction(character: Character) {
    }

    companion object {
        const val LESSON_ID = "howTheyWrite.LESSON_ID"
        const val CHARACTER_IDS = "howTheyWrite.CHARACTER_IDS"
    }
}
