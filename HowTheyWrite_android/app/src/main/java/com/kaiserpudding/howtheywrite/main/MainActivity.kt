package com.kaiserpudding.howtheywrite.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.characterList.*
import com.kaiserpudding.howtheywrite.database.ChineseDbHelper
import com.kaiserpudding.howtheywrite.lessonList.LessonListFragment
import com.kaiserpudding.howtheywrite.lessonList.LessonListFragmentDirections
import com.kaiserpudding.howtheywrite.lessonList.NewLessonFragment
import com.kaiserpudding.howtheywrite.model.Character
import com.kaiserpudding.howtheywrite.quiz.QuizFragment
import com.kaiserpudding.howtheywrite.quiz.QuizFragmentDirections
import kotlinx.android.synthetic.main.activity_main.*

/**
 * The single activity in the projects which hosts all fragments.
 * It checks if the database needs to be updated.
 * It only handles the navigation between different fragment per callbacks from the fragments
 */
class MainActivity : AppCompatActivity(),
        QuizFragment.OnQuizFragmentInteractionListener,
        BaseCharacterListFragment.OnCharacterListFragmentInteractionListener,
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

        setSupportActionBar(findViewById(R.id.toolbar))

        val host: NavHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return

        //Set up Action Bar
        navController = host.navController

        appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()

        setupActionBar(navController, appBarConfiguration)
    }

    private fun setupActionBar(navController: NavController, appBarConfiguration: AppBarConfiguration) {
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    @SuppressLint("StaticFieldLeak")
    private inner class DatabaseInitializer : AsyncTask<Context, Void, Void>() {

        override fun doInBackground(vararg contexts: Context): Void? {
            val a = ChineseDbHelper(contexts[0])
            a.readableDatabase
            a.close()
            return null
        }
    }


    override fun onToQuizInteraction(lessonId: Int, lessonName: String) {
        val action = CharacterListFragmentDirections.actionCharacterListToQuiz(lessonId, lessonName, null)
        navController.navigate(action)
    }

    override fun onToQuizInteraction(characterIds: IntArray, lessonName: String) {
        val action = CharacterListFragmentDirections.actionCharacterListToQuiz(-1, lessonName, characterIds)
        navController.navigate(action)
    }

    override fun onCharacterListItemInteraction(characterId: Int, type: Int) {
        navController.currentDestination
        val action = when (type) {
            0 -> CharacterListFragmentDirections.actionCharacterListToCharacterDetail(characterId)
            1 -> AddCharactersFragmentDirections.actionAddCharactersFragmentToCharacterDetailFragment(characterId)
            else -> AllCharactersFragmentDirections.actionAllCharactersFragmentToCharacterDetailFragment(characterId)
        }
        navController.navigate(action)
    }

    override fun onQuizCharacterInteraction(character: Character) {
        val action = QuizFragmentDirections.actionQuizToCharacterDetail(character.id)
        navController.navigate(action)
    }

    override fun onLessonListItemInteraction(lessonId: Int) {
        val action = if (lessonId == 0) LessonListFragmentDirections.actionLessonListFragmentToAllCharactersFragment()
        else LessonListFragmentDirections.actionLessonListToCharacterList(lessonId)
        navController.navigate(action)
    }

    override fun onToNewLessonInteraction() {
        val action = LessonListFragmentDirections.actionLessonListToNewLesson()
        navController.navigate(action)
    }

    override fun onNewCharacterInteraction(lessonId: Int, lessonName: String) {
        val action = CharacterListFragmentDirections.actionCharacterListToNewCharacter()
        navController.navigate(action)
    }

    override fun onAddToLessonInteraction(lessonId: Int, lessonName: String) {
        val action = CharacterListFragmentDirections.actionCharacterListFragmentToAddCharactersFragment(lessonId, lessonName)
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

    override fun updateTitle(title: String) {
        toolbar.title = title
    }

    override fun onFinish() {
        navController.popBackStack()
    }

}
