package com.kaiserpudding.howtheywrite.characterList

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.model.Character
import java.util.LinkedList

class CharacterListActivity : AppCompatActivity() {

    private var characterListViewModel: CharacterListViewModel? = null

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        setContentView(R.layout.actvity_character_list)

        val recyclerView = findViewById<RecyclerView>(R.id.character_recyclerview)
        val adapter = CharacterListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 5)

        characterListViewModel = ViewModelProviders.of(this).get(CharacterListViewModel::class.java)
        characterListViewModel!!.characters.observe(this, Observer<List<Character>> { adapter.setCharacters(it) })

        val fab = findViewById<FloatingActionButton>(R.id.add_character_fab)
        fab.setOnClickListener {
            val intent = Intent(this@CharacterListActivity, NewCharacterActivity::class.java)
            startActivityForResult(intent, NEW_CHAR_ACTIVITY_REQUEST_CODE)
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NEW_CHAR_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val character = Character(
                    data!!.getStringExtra(NewCharacterActivity.REPLY_CHAR_HANZI),
                    data.getStringExtra(NewCharacterActivity.REPLY_CHAR_HIRAGANA),
                    null,
                    data.getStringExtra(NewCharacterActivity.REPLY_CHAR_TRANSLATION),
                    true
            )
            characterListViewModel!!.insertCharacter(character)
        }
    }

    companion object {

        private val NEW_CHAR_ACTIVITY_REQUEST_CODE = 2
    }
}
