package com.kaiserpudding.howtheywrite.characterDetail


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs

import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.model.Character

/**
 * A simple [Fragment] subclass.
 * It displays a single character.
 *
 */
class CharacterDetailFragment : Fragment() {

    private lateinit var hanziTextView: TextView
    private lateinit var pinyinTextView: TextView
    private lateinit var translationTextView: TextView
    private lateinit var characterDetailViewModel: CharacterDetailViewModel

    private var characterId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val safeArgs: CharacterDetailFragmentArgs by navArgs()
        characterId = safeArgs.characterId
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_character_detail, container, false)

        //TODO proper fix for racing condition of initChar and getChar
        characterDetailViewModel = ViewModelProviders.of(
                this, CharacterDetailViewModelFactory(activity!!.application, characterId))
                .get(CharacterDetailViewModel::class.java)

//        var character: Character? = null
//
//        while (character == null) {
//            character = characterDetailViewModel.character
//        }

        characterDetailViewModel.character.observe(this, Observer {character ->
            hanziTextView = view.findViewById(R.id.hanzi)
            hanziTextView.text = character.hanzi
            pinyinTextView = view.findViewById(R.id.pinyin)
            pinyinTextView.text = character.pinyin
            translationTextView = view.findViewById(R.id.translation)
            if (character.translationKey != null) {
                translationTextView.setText(
                        resources.getIdentifier(
                                character.translationKey,
                                "string",
                                context!!.packageName
                        )
                )
            } else {
                translationTextView.text = character.translation
            }
        })



        return view
    }

}
