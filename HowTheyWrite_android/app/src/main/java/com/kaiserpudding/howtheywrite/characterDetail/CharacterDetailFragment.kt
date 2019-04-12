package com.kaiserpudding.howtheywrite.characterDetail


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs

import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.model.Character

/**
 * A simple [Fragment] subclass.
 *
 */
class CharacterDetailFragment : Fragment() {

    private var hanziTextView: TextView? = null
    private var pinyinTextView: TextView? = null
    private var translationTextView: TextView? = null
    private var characterDetailViewModel: CharacterDetailViewModel? = null

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
        var character: Character? = null

        while (character == null) {
            character = characterDetailViewModel!!.character
        }

        hanziTextView = view.findViewById(R.id.hanzi)
        hanziTextView!!.text = character.hanzi
        pinyinTextView = view.findViewById(R.id.pinyin)
        pinyinTextView!!.text = character.pinyin
        translationTextView = view.findViewById(R.id.translation)
        if (character.translationKey != null) {
            translationTextView!!.setText(
                    resources.getIdentifier(
                            character.translationKey,
                            "string",
                            context!!.packageName
                    )
            )
        } else {
            translationTextView!!.text = character.translation
        }

        return view
    }

}
