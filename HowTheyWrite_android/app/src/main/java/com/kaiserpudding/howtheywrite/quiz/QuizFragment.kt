package com.kaiserpudding.howtheywrite.quiz

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.model.Character
import kotlinx.android.synthetic.main.fragment_quiz.*


/**
 * A simple [Fragment] subclass.
 *
 * It displays the quiz for a given lesson.
 * The translation and pinyin for a character will be shown
 * and the user has to input the correct hanzi.
 *
 * Activities that contain this fragment must implement the
 * [QuizFragment.OnQuizFragmentInteractionListener] interface
 * to handle interaction events.
 *
 */
class QuizFragment : Fragment() {

    private var lessonId: Int? = 0
    private lateinit var lessonName: String
    private var listener: OnQuizFragmentInteractionListener? = null

    private lateinit var quizViewModel: QuizViewModel
    private lateinit var quizTranslation: TextView
    private lateinit var quizPinyin: TextView
    private lateinit var quizEditText: EditText

    var first: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val safeArgs: QuizFragmentArgs by navArgs()
        lessonId = safeArgs.lessonId
        lessonName = safeArgs.lessonName

        //TODO adjust to no lesson id
        quizViewModel = ViewModelProviders.of(
                this,
                QuizViewModelFactory(activity!!.application, lessonId!!)).get(QuizViewModel::class.java)
        quizViewModel.finishedLoading.observe(this, Observer {
            setQuizCharacter()
        })

        first = true

        listener?.updateTitle(lessonName)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_quiz, container, false)

        quizTranslation = view.findViewById(R.id.quiz_translation)
        quizPinyin = view.findViewById(R.id.quiz_pinyin)
        quizEditText = view.findViewById(R.id.quiz_input_edit_text)
        quizTranslation.setOnClickListener {
            onTranslationPressed(quizViewModel.currentCharacter)
        }

        val finishedLoading = quizViewModel.finishedLoading.value
        if (finishedLoading != null  && finishedLoading) {
            setQuizCharacter()
        }

        quizEditText.setOnEditorActionListener { _: TextView, _: Int, _: KeyEvent? ->
            if (quizEditText.text != null && quizViewModel.inputIsCorrect(quizEditText.text.toString())) {
                if (quizViewModel.hasNext()) {
                    resetQuizInput()
                    quizViewModel.nextCharacter()
                    setQuizCharacter()
                } else {
                    listener!!.onQuizFinishInteraction()
                }
            } else {
                resetQuizInput()
            }
            true
        }



        return view
    }

    /**
     * Sets the [TextView] for the translation and pinyin to the current character,
     * provided by [QuizViewModel]
     *
     */
    private fun setQuizCharacter() {
        quizTranslation.text = quizViewModel.currentCharacterTranslation
        quizPinyin.text = quizViewModel.currentCharacterPinyin
    }

    private fun resetQuizInput() {
        quizEditText.setText("")
    }

    private fun onTranslationPressed(character: Character) {
        listener?.onQuizCharacterInteraction(character)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnQuizFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnQuizFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onStart() {
        super.onStart()
        showKeyboard()
        if (!first) {
            quizEditText.requestFocus()
        }
        first = false

    }

    override fun onPause() {
        super.onPause()
        view?.let { hideKeyboardAndDefocus(it) }
    }

    /**
     * Focus quizInputEditText and show keyboard
     *
     */
    private fun showKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    /**
     * Clear focus of quizInputEditText and hide keyboard
     *
     * @param view
     */
    private fun hideKeyboardAndDefocus(view: View) {
        quiz_input_edit_text.clearFocus()
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnQuizFragmentInteractionListener {
        fun onQuizCharacterInteraction(character: Character)
        fun onQuizFinishInteraction()
        fun updateTitle(title: String)
    }
}
