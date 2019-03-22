package com.kaiserpudding.howtheywrite.lesson

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.characterDetail.CharacterDetailFragment
import com.kaiserpudding.howtheywrite.characterList.CharacterListFragment
import com.kaiserpudding.howtheywrite.lesson.LessonListFragment.OnLessonListFragmentInteractionListener
import com.kaiserpudding.howtheywrite.model.Character
import com.kaiserpudding.howtheywrite.model.Lesson

class LessonActivity : AppCompatActivity(),
        OnLessonListFragmentInteractionListener,
        CharacterListFragment.OnCharacterListFragmentInteractionListener,
        CharacterDetailFragment.OnCharacterDetailFragmentInteractionListener {

    companion object {

        private val NEW_LESSON_ACTIVITY_REQUEST_CODE = 1
    }

    private val manager: FragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson_list)

        //Add lessonListFragment
        val transaction = manager.beginTransaction()
        val lessonListFragment = LessonListFragment()
        transaction.add(R.id.lesson_list_fragment_container, lessonListFragment)
        transaction.commit()
    }

    override fun onLessonListFragmentInteraction(lesson: Lesson) {
        val transaction = manager.beginTransaction()

        //Transfer id of lesson that was clicked
        val characterListFragment = CharacterListFragment.newInstance(lesson.id)

        //Replace current lessonListFragment with fragment displaying characters of selected lesson
        transaction.replace(R.id.lesson_list_fragment_container, characterListFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    override fun onCharacterListFragmentInteraction(character: Character) {
        val transaction = manager.beginTransaction()

        //Transfer id of character that was clicked
        val characterDetailFragment =
                CharacterDetailFragment.newInstance(character.id)

        //Replace current fragment with caracterDetailFragment displaying the selected character
        transaction.replace(R.id.lesson_list_fragment_container, characterDetailFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCharacterDetailFragmentInteraction(character: Character) {
    }
}
