package com.kaiserpudding.howtheywrite.characterList

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

import com.kaiserpudding.howtheywrite.R

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [NewCharacterFragment.OnNewCharacterFragmentInteractionListener] interface
 * to handle interaction events.
 *
 */
class NewCharacterFragment : Fragment() {
    private var listener: OnNewCharacterFragmentInteractionListener? = null

    private lateinit var newCharacterHanziEditText: EditText
    private lateinit var newCharacterPinyinEditText: EditText
    private lateinit var newCharacterTranslationEditText: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_new_character, container, false)

        newCharacterHanziEditText = view.findViewById(R.id.editNewCharHanzi)
        newCharacterPinyinEditText = view.findViewById(R.id.editNewCharPinyin)
        newCharacterTranslationEditText = view.findViewById(R.id.editNewCharTranslation)

        val button = view.findViewById<Button>(R.id.button_save_new_char)
        button.setOnClickListener {
            if(checkEditText()) {
                listener?.onNewCharacterFinishInteraction(
                        newCharacterHanziEditText.text.toString(),
                        newCharacterPinyinEditText.text.toString(),
                        newCharacterTranslationEditText.text.toString()
                )
            }//TODO else show error

        }

        return view
    }

    /**
     * TODO
     * Checks if input in [newCharacterHanziEditText] is correct
     *
     */
    private fun checkEditText(): Boolean {
        return true
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onFinish(hanzi: String, pinyin: String, translation: String) {
        listener?.onNewCharacterFinishInteraction(hanzi, pinyin, translation)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnNewCharacterFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnNewCharacterFragmentInteractionListener")
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
    interface OnNewCharacterFragmentInteractionListener {
        fun onNewCharacterFinishInteraction(hanzi: String, pinyin: String, translation: String)
    }

}
