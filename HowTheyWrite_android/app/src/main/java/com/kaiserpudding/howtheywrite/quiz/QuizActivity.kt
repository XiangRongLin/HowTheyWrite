//package com.kaiserpudding.howtheywrite.quiz
//
//import android.content.Intent
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.fragment.app.FragmentManager
//import com.kaiserpudding.howtheywrite.R
//import com.kaiserpudding.howtheywrite.characterList.CharacterListFragment
//import com.kaiserpudding.howtheywrite.lessonList.LessonListFragment
//import com.kaiserpudding.howtheywrite.model.Character
//
//class QuizActivity : AppCompatActivity(),
//        QuizFragment.OnQuizFragmentInteractionListener,
//        CharacterListFragment.OnCharacterListFragmentInteractionListener,
//        LessonListFragment.OnLessonListFragmentInteractionListener {
//
//    private val manager: FragmentManager = supportFragmentManager
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_quiz)
//
////        if (savedInstanceState == null) startLessonListFragment()
//    }
//
//
//    override fun onLessonListFragmentInteraction(lessonId: Int) {
//        startCharacterListFragment(lessonId)
//    }
//
//    override fun onQuizCharacterInteraction(character: Character) {
//        val intent = Intent(this, CharacterDetailActivity::class.java)
//        intent.putExtra(CharacterDetailActivity.REPLY_CHARACTER_ID, character.id)
//        startActivity(intent)
//    }
//
//    override fun onToQuizButtonInteraction(lessonId: Int) {
//        startQuizFragment(lessonId)
//    }
//
//    private fun startLessonListFragment() {
//        val transaction = manager.beginTransaction()
//        transaction.add(R.id.container_quiz_fragment, LessonListFragment.newInstance())
//        transaction.commit()
//    }
//
//    private fun startCharacterListFragment(lessonId: Int?) {
//        val transaction = manager.beginTransaction()
//        val characterListFragment =
//                if (lessonId == null) CharacterListFragment.newInstance()
//                else CharacterListFragment.newInstance(lessonId)
//        transaction.replace(R.id.container_quiz_fragment, characterListFragment)
//        transaction.addToBackStack(null)
//        transaction.commit()
//    }
//
//    private fun startQuizFragment(lessonId: Int?) {
//        val transaction = manager.beginTransaction()
//        val quizFragment =
//                if (lessonId == null) QuizFragment.newInstance()
//                else QuizFragment.newInstance(lessonId)
//        transaction.replace(R.id.container_quiz_fragment, quizFragment)
//        transaction.addToBackStack(null)
//        transaction.commit()
//    }
//}
