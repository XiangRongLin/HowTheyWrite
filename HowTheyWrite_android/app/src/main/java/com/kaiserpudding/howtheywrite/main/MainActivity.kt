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
import com.kaiserpudding.howtheywrite.characterDetail.CharacterDetailFragment
import com.kaiserpudding.howtheywrite.characterDetail.CharacterDetailFragmentDirections
import com.kaiserpudding.howtheywrite.characterList.*
import com.kaiserpudding.howtheywrite.characterList.BaseCharacterListFragment.BaseCharacterListType
import com.kaiserpudding.howtheywrite.database.ChineseDbHelper
import com.kaiserpudding.howtheywrite.lessonList.*
import com.kaiserpudding.howtheywrite.lessonList.BaseLessonListFragment.BaseLessonListType
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
        BaseLessonListFragment.OnBaseLessonListFragmentInteractionListener,
        AddLessonListFragment.OnAddLessonListFragmentInteractionListener,
        AddCharactersFragment.OnAddCharacterFragmentInteractionListener,
        NewCharacterFragment.OnNewCharacterFragmentInteractionListener,
        NewLessonFragment.OnNewLessonFragmentInteractionListener,
        CharacterDetailFragment.OnCharacterDetailFragmentInteractionListener {

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


    override fun onToQuizInteraction(lessonId: Long, lessonName: String) {
        val action = CharacterListFragmentDirections
                .actionCharacterListToQuiz(lessonId, lessonName, null)
        navController.navigate(action)
    }

    override fun onToQuizInteraction(characterIds: LongArray, lessonName: String) {
        val action = CharacterListFragmentDirections
                .actionCharacterListToQuiz(-1, lessonName, characterIds)
        navController.navigate(action)
    }

    override fun onCharacterListItemInteraction(characterId: Long, type: BaseCharacterListType) {
        when (type) {
            BaseCharacterListType.CHARACTER_LIST -> {
                navController.navigate(
                        CharacterListFragmentDirections
                                .actionCharacterListToCharacterDetail(characterId))
            }
            BaseCharacterListType.ADD_CHARACTER -> {
                navController.navigate(
                        AddCharactersFragmentDirections
                                .actionAddCharactersToCharacterDetail(characterId))
            }
        }
    }

    override fun onQuizCharacterInteraction(character: Character) {
        val action = QuizFragmentDirections.actionQuizToCharacterDetail(character.id)
        navController.navigate(action)
    }

    override fun onBaseLessonListItemInteraction(lessonId: Long, type: BaseLessonListType) {
        when (type) {
            BaseLessonListType.LESSON_LIST -> {
                val action = LessonListFragmentDirections
                        .actionLessonListToCharacterList(lessonId)
                navController.navigate(action)
            }
            else -> {
                //TODO throw error
            }
        }
    }

    override fun onAddLessonListItemInteraction(addToId: Long, addToName: String, selectedId: Long) {
        val action = AddLessonListFragmentDirections
                .actionAddLessonListFragmentToAddCharactersFragment(
                        selectedId, addToId, addToName
                )
        navController.navigate(action)
    }

    override fun onToNewLessonInteraction() {
        val action = LessonListFragmentDirections.actionLessonListToNewLesson()
        navController.navigate(action)
    }

    override fun onNewCharacterInteraction(lessonId: Long, type: BaseCharacterListType) {
        when (type) {
            BaseCharacterListType.CHARACTER_LIST -> {
                navController.navigate(
                        CharacterListFragmentDirections
                                .actionCharacterListToNewCharacter(lessonId, hanzi = null, pinyin = null, translation = null)
                )
            }
            else -> return
        }
    }

    override fun onAddToLessonInteraction(lessonId: Long, lessonName: String) {
        val action = CharacterListFragmentDirections
                .actionCharacterListFragmentToAddLessonListFragment(lessonId, lessonName)
        navController.navigate(action)
    }

    override fun onEditCharacterInteraction(id: Long, hanzi: String, pinyin: String, translation: String) {
        val action = CharacterDetailFragmentDirections
                .actionCharacterDetailFragmentToNewCharacterFragment(0, id, hanzi, pinyin, translation)
        navController.navigate(action)
    }

    override fun updateTitle(title: String) {
        toolbar.title = title
    }

    override fun onFinish() {
        navController.popBackStack()
    }

    override fun onAddFinish() {
        //pop twice to skip through AddLessonListFragment
        navController.popBackStack()
        navController.popBackStack()
    }

}
