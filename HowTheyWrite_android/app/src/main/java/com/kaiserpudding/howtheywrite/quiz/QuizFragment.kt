package com.kaiserpudding.howtheywrite.quiz

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView

import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.model.Character

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [QuizFragment.OnQuizFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [QuizFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class QuizFragment : Fragment() {

    private var lessonId: Int? = 0
    private var listener: OnQuizFragmentInteractionListener? = null

    private lateinit var quizViewModel: QuizViewModel
    private lateinit var quizTranslation: TextView
    private lateinit var quizPinyin: TextView
    private lateinit var quizInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            lessonId = it.getInt(ARG_LESSON_ID)
        }
        //TODO adjust to no lesson id
        quizViewModel = ViewModelProviders.of(
                this,
                QuizViewModelFactory(activity!!.application, lessonId!!)).get(QuizViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_quiz, container, false)

        quizTranslation = view.findViewById(R.id.quiz_translation)
        quizPinyin = view.findViewById(R.id.quiz_pinyin)
        //open [CharacterDetailActivity] when clicked
        quizTranslation.setOnClickListener {
            onButtonPressed(quizViewModel.currentWord)
        }

        var a: List<Character>? = null
        while (a == null) {
            a = quizViewModel.characters
        }

        quizViewModel.randomizeList()
        quizViewModel.nextWord?.let { setQuizWord(it) }

        quizInput = view.findViewById(R.id.quiz_input_edit_text)
        quizInput.setOnEditorActionListener { textView: TextView, i: Int, keyEvent: KeyEvent ->
            if (quizInput.text != null && quizInput.text.toString() == quizViewModel.currentWord.hanzi) {
                val nextCharacter = quizViewModel.nextWord
                if (nextCharacter != null) {
                    setQuizWord(nextCharacter)
                    resetQuizInput()
                } else {
                    activity!!.finish()
                }
            } else {
                resetQuizInput()
            }
            true
        }

        return view
    }

    private fun setQuizWord(character: Character) {
        quizTranslation.text = if (character.translationKey == null) {
            character.translation
        } else {
            resources.getText(resources.getIdentifier(
                    character.translationKey,
                    "string",
                    context!!.packageName))
        }
        quizPinyin.text = character.pinyin
    }

    private fun resetQuizInput() {
        quizInput.setText("")
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(character: Character) {
        listener?.onQuizFragmentInteraction(character)
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
    interface OnQuizFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onQuizFragmentInteraction(character: Character)
    }

    companion object {

        //the fragment initialization parameters
        private const val ARG_LESSON_ID = "lessonId"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param lessonId Id of the lesson.
         * @return A new instance of fragment QuizFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(lessonId: Int) =
                QuizFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_LESSON_ID, lessonId)
                    }
                }

        @JvmStatic
        fun newInstance() = QuizFragment()
    }
}
