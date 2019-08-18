package com.kaiserpudding.howtheywrite.characterList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.model.Character
import com.kaiserpudding.howtheywrite.shared.setSafeOnClickListener
import kotlinx.android.synthetic.main.fragment_lesson_list.*

/**
 * A simple [Fragment] subclass.
 *
 * Add a new character to the data source
 *
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
    private lateinit var insertionViewModel: InsertionViewModel
    private lateinit var mode: Mode
    private var lessonId = 0L
    private var characterId = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val safeArgs: NewCharacterFragmentArgs by navArgs()
        lessonId = safeArgs.lessonId
        characterId = safeArgs.characterId
        mode = if (lessonId > 0) Mode.NEW
        else Mode.EDIT
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_new_character, container, false)

        insertionViewModel = ViewModelProviders.of(this).get(InsertionViewModel::class.java)

        newCharacterHanziEditText = view.findViewById(R.id.editNewCharHanzi)
        newCharacterPinyinEditText = view.findViewById(R.id.editNewCharPinyin)
        newCharacterTranslationEditText = view.findViewById(R.id.editNewCharTranslation)

        if (mode == Mode.EDIT) {
            val safeArgs: NewCharacterFragmentArgs by navArgs()
            newCharacterHanziEditText.setText(safeArgs.hanzi)
            newCharacterPinyinEditText.setText(safeArgs.pinyin)
            newCharacterTranslationEditText.setText(safeArgs.translation)
        }

        val button = view.findViewById<Button>(R.id.button_save_new_char)
        button.setSafeOnClickListener {
            if (checkEditText()) {
                when (mode) {
                    Mode.NEW -> {
                        insertionViewModel.insertCharacter(
                                Character(newCharacterHanziEditText.text.toString(),
                                        newCharacterPinyinEditText.text.toString(),
                                        newCharacterTranslationEditText.text.toString()),
                                lessonId

                        )
                    }
                    Mode.EDIT -> {
                        val character = Character(
                                newCharacterHanziEditText.text.toString(),
                                newCharacterPinyinEditText.text.toString(),
                                newCharacterTranslationEditText.text.toString())
                        character.id = characterId
                        insertionViewModel.updateCharacter(character)
                    }
                }

                val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
                listener?.onFinish()
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnNewCharacterFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnNewCharacterFragmentInteractionListener")
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
     */
    interface OnNewCharacterFragmentInteractionListener {
        fun onFinish()
    }

    enum class Mode {
        NEW, EDIT
    }

}
