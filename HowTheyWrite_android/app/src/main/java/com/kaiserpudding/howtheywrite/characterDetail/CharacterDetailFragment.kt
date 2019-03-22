package com.kaiserpudding.howtheywrite.characterDetail

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.model.Character

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val CHARACTER_ID = "howTheyWrite_CHARACTER_ID"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CharacterDetailFragment.OnCharacterDetailFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CharacterDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class CharacterDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var characterId: Int = 0
    private var listener: OnCharacterDetailFragmentInteractionListener? = null
    private lateinit var characterDetailViewModel: CharacterDetailViewModel
    private lateinit var hanziTextView: TextView
    private lateinit var pinyinTextView: TextView
    private lateinit var translationTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            characterId = it.getInt(CHARACTER_ID)
        }

        characterDetailViewModel = ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return CharacterDetailViewModel(activity!!.application, characterId) as T
            }
        })[CharacterDetailViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_character_detail, container, false)

        val character = characterDetailViewModel.character

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
                            view.context.packageName
                    )
            )
        } else {
            translationTextView.text = character.translation
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCharacterDetailFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnCharacterDetailFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnCharacterDetailFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onCharacterDetailFragmentInteraction(character: Character)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param characterId Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CharacterDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(characterId: Int) =
                CharacterDetailFragment().apply {
                    arguments = Bundle().apply {
                        putInt(CHARACTER_ID, characterId)
                    }
                }
    }
}
