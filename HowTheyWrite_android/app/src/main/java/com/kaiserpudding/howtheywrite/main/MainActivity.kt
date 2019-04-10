package com.kaiserpudding.howtheywrite.main

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.characterDetail.CharacterDetailActivity
import com.kaiserpudding.howtheywrite.characterList.CharacterListFragment
import com.kaiserpudding.howtheywrite.database.ChineseDbHelper
import com.kaiserpudding.howtheywrite.lessonList.LessonListFragment
import com.kaiserpudding.howtheywrite.model.Character
import com.kaiserpudding.howtheywrite.model.Lesson
import com.kaiserpudding.howtheywrite.quiz.QuizFragment

class MainActivity : AppCompatActivity(),
        QuizFragment.OnQuizFragmentInteractionListener,
        CharacterListFragment.OnCharacterListFragmentInteractionListener,
        LessonListFragment.OnLessonFragmentInteractionListener {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val databaseInitializer = DatabaseInitializer()
        databaseInitializer.execute(this)

        val host: NavHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return

        //Set up Action Bar
        val navController = host.navController

        appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()

//        setupActionBar(navController, appBarConfiguration)

        setUpBottomNavMenu(navController)
    }

    private fun setUpBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        NavigationUI.setupWithNavController(bottomNav, navController)
    }

    private fun setupActionBar(navController: NavController, appBarConfiguration: AppBarConfiguration) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private inner class DatabaseInitializer : AsyncTask<Context, Void, Void>() {

        override fun doInBackground(vararg contexts: Context): Void? {
            val a = ChineseDbHelper(contexts[0])
            a.readableDatabase
            return null
        }
    }

    override fun onQuizFragmentInteraction(character: Character) {
        val intent = Intent(this, CharacterDetailActivity::class.java)
        intent.putExtra(CharacterDetailActivity.REPLY_CHARACTER_ID, character.id)
        startActivity(intent)}

    override fun onToQuizButtonInteraction(lessonId: Int?) {
        Navigation.createNavigateOnClickListener(R.id.action_next)
    }

    override fun onLessonFragmentInteraction(lesson: Lesson) {

    }
}
