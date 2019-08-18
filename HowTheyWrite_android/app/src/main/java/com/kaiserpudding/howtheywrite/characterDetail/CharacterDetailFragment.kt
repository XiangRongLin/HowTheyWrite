package com.kaiserpudding.howtheywrite.characterDetail


import android.content.Context
import android.os.Bundle
import android.view.*
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
    private var characterId: Long = 0L
    private var listener: OnCharacterDetailFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Get characterId from safeArgs
        val safeArgs: CharacterDetailFragmentArgs by navArgs()
        characterId = safeArgs.characterId

        //instantiate the viewModel with the characterId from above
        characterDetailViewModel = ViewModelProviders.of(
                this, CharacterDetailViewModelFactory(activity!!.application, characterId))
                .get(CharacterDetailViewModel::class.java)

        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.character_detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_edit_character -> {
                listener?.onEditCharacterInteraction(
                        characterId,
                        hanziTextView.text.toString(),
                        pinyinTextView.text.toString(),
                        translationTextView.text.toString())
                true
            }
            else -> false
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCharacterDetailFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnCharacterDetailFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnCharacterDetailFragmentInteractionListener{
        fun onEditCharacterInteraction(id: Long, hanzi: String, pinyin: String, translation: String)
    }

}
