package com.kaiserpudding.howtheywrite.characterList

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.characterDetail.CharacterDetailFragment
import com.kaiserpudding.howtheywrite.characterList.CharacterListFragment.OnCharacterListFragmentInteractionListener
import com.kaiserpudding.howtheywrite.model.Character

class CharacterListActivity : AppCompatActivity(),
        OnCharacterListFragmentInteractionListener,
        CharacterDetailFragment.OnCharacterDetailFragmentInteractionListener {

    private val manager: FragmentManager = supportFragmentManager

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        setContentView(R.layout.activity_character_list)

        //add characterListFragment
        val transaction = manager.beginTransaction()
        val characterListFragment = CharacterListFragment.newInstance()
        transaction.add(R.id.character_list_fragment_container, characterListFragment)
        transaction.commit()
    }


    override fun onCharacterListFragmentInteraction(character: Character) {
        val transaction = manager.beginTransaction()

        //Transfer id of character that was clicked
        val characterDetailFragment =
                CharacterDetailFragment.newInstance(character.id)

        //Replace current fragment with characterDetailFragment displaying the selected character
        transaction.replace(R.id.character_list_fragment_container, characterDetailFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCharacterDetailFragmentInteraction(character: Character) {
    }
}
