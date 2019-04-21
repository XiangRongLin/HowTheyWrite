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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Get characterId from safeArgs
        val safeArgs: CharacterDetailFragmentArgs by navArgs()
        val characterId = safeArgs.characterId

        //instantiate the viewModel with the characterId from above
        characterDetailViewModel = ViewModelProviders.of(
                this, CharacterDetailViewModelFactory(activity!!.application, characterId))
                .get(CharacterDetailViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_character_detail, container, false)

        //instantiate the TextViews
        hanziTextView = view.findViewById(R.id.hanzi)
        pinyinTextView = view.findViewById(R.id.pinyin)
        translationTextView = view.findViewById(R.id.translation)

        // Add a observer to the finishedLoading field and set the text in the textViews when it is true
        characterDetailViewModel.finishedLoading.observe(this, Observer {
            if (it) {
                hanziTextView.text = characterDetailViewModel.hanzi

                pinyinTextView.text = characterDetailViewModel.pinyin

                translationTextView.text = characterDetailViewModel.translation
            }
        })
        return view
    }

}
