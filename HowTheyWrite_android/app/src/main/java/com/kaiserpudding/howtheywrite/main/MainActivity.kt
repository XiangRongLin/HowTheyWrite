package com.kaiserpudding.howtheywrite.main

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.characterList.CharacterListFragment
import com.kaiserpudding.howtheywrite.characterList.CharacterListFragmentDirections
import com.kaiserpudding.howtheywrite.characterList.NewCharacterFragment
import com.kaiserpudding.howtheywrite.database.ChineseDbHelper
import com.kaiserpudding.howtheywrite.lessonList.LessonListFragment
import com.kaiserpudding.howtheywrite.lessonList.LessonListFragmentDirections
import com.kaiserpudding.howtheywrite.lessonList.NewLessonFragment
import com.kaiserpudding.howtheywrite.model.Character
import com.kaiserpudding.howtheywrite.quiz.QuizFragment
import com.kaiserpudding.howtheywrite.quiz.QuizFragmentDirections

/**
 * The single activity in the projects which hosts all fragments.
 * It checks if the database needs to be updated.
 * It only handles the navigation between different fragment per callbacks from the fragments
 */
class MainActivity : AppCompatActivity(),
        QuizFragment.OnQuizFragmentInteractionListener,
        CharacterListFragment.OnCharacterListFragmentInteractionListener,
        LessonListFragment.OnLessonListFragmentInteractionListener,
        NewCharacterFragment.OnNewCharacterFragmentInteractionListener,
        NewLessonFragment.OnNewLessonFragmentInteractionListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val databaseInitializer = DatabaseInitializer()
        databaseInitializer.execute(this)

        val host: NavHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return

        //Set up Action Bar
        navController = host.navController

        appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()

//        setupActionBar(navController, appBarConfiguration)

        setUpBottomNavMenu(navController)
    }

    private fun setUpBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav?.setupWithNavController(navController)
    }

    private inner class DatabaseInitializer : AsyncTask<Context, Void, Void>() {

        override fun doInBackground(vararg contexts: Context): Void? {
            val a = ChineseDbHelper(contexts[0])
            a.readableDatabase
            a.close()
            return null
        }
    }

    override fun onToQuizInteraction(lessonId: Int) {
        val action = CharacterListFragmentDirections.actionCharacterListToQuiz(lessonId)
        navController.navigate(action)
    }

    override fun onCharacterListItemInteraction(characterId: Int) {
        val action = CharacterListFragmentDirections.actionCharacterListToCharacterDetail(characterId)
        navController.navigate(action)
    }

    override fun onQuizCharacterInteraction(character: Character) {
        val action = QuizFragmentDirections.actionQuizToCharacterDetail(character.id)
        navController.navigate(action)
    }

    override fun onLessonListItemInteraction(lessonId: Int) {
        val action = LessonListFragmentDirections.actionLessonListToCharacterList(lessonId)
        navController.navigate(action)
    }

    override fun onToNewLessonInteraction() {
        val action = LessonListFragmentDirections.actionLessonListToNewLesson()
        navController.navigate(action)
    }

    override fun onNewCharacterInteraction() {
        val action = CharacterListFragmentDirections.actionCharacterListToNewCharacter()
        navController.navigate(action)
    }

    override fun onQuizFinishInteraction() {
        navController.popBackStack()
    }

    override fun onNewCharacterFinishInteraction() {
        navController.popBackStack()
    }

    override fun onNewLessonFinishInteraction() {
        navController.popBackStack()
    }


}
