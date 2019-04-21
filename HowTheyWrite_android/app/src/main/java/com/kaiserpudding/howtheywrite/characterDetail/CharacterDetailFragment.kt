package com.kaiserpudding.howtheywrite.characterDetail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.kaiserpudding.howtheywrite.R

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

        characterDetailViewModel = ViewModelProviders.of(
                this, CharacterDetailViewModelFactory(activity!!.application, characterId))
                .get(CharacterDetailViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_character_detail, container, false)

        hanziTextView = view.findViewById(R.id.hanzi)
        pinyinTextView = view.findViewById(R.id.pinyin)
        translationTextView = view.findViewById(R.id.translation)

        characterDetailViewModel.finishedLoading.observe(this, Observer {
            if (it) {
                hanziTextView.text = characterDetailViewModel.character.hanzi

                pinyinTextView.text = characterDetailViewModel.character.pinyin

                if (characterDetailViewModel.character.translationKey != null) {
                    translationTextView.setText(
                            resources.getIdentifier(
                                    characterDetailViewModel.character.translationKey,
                                    "string",
                                    context!!.packageName
                            )
                    )
                } else {
                    translationTextView.text = characterDetailViewModel.character.translation
                }
            }
        })
        return view
    }

}
