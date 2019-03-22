package com.kaiserpudding.howtheywrite.quiz

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView

import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.model.Character

private const val LESSON_ID = "howTheyWrite.LESSON_ID"
private const val CHARACTER_IDS = "howTheyWrite.CHARACTER_IDS"

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

    private var lessonId: Int = 0;
    private var characterIds: IntArray? = null;
    private var listener: OnQuizFragmentInteractionListener? = null
    private lateinit var quizViewModel: QuizViewModel
    private lateinit var quizTranslation: TextView
    private lateinit var quizPinyin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            lessonId = it.getInt(LESSON_ID)
            characterIds = it.getIntArray(CHARACTER_IDS)
        }

        quizViewModel = ViewModelProviders.of(this, object: ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return QuizViewModel(activity!!.application, lessonId, characterIds) as T
            }
        })[QuizViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_quiz, container, false)

        quizTranslation = view.findViewById(R.id.quiz_translation)
        quizPinyin = view.findViewById(R.id.quiz_pinyin)
        quizTranslation.setOnClickListener {
            onButtonPressed(quizViewModel.currentCharacter)
        }

        //TODO proper solution for uninitialized quizViewModel.characters, which takes some time
        Thread.sleep(500)
        setQuizCharacter(quizViewModel.currentCharacter)

        val quizInput: EditText = view.findViewById(R.id.quiz_input_edit_text)
        //if user clicks enter clear editText input and if it is correct show next character
        quizInput.setOnEditorActionListener { textView, i, keyEvent ->
            if (quizInput.text != null
                    && quizInput.text.toString() == quizViewModel.currentCharacter.hanzi) {
                quizInput.setText("");
                if (quizViewModel.hasNext()) {
                    val nextCharacter = quizViewModel.nextCharacter
                    setQuizCharacter(nextCharacter)
                } else {
                    activity!!.supportFragmentManager.popBackStack()
                }
            }
            true
        }



        return view
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
            throw RuntimeException(context.toString() + " must implement OnQuizFragmentInteractionListener")
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
        fun onQuizFragmentInteraction(character: Character)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param lessonId The id of the lesson to display
         * @param characterIds The ids of the characters to display
         * @return A new instance of fragment QuizFragment.
         */
        @JvmStatic
        fun newInstance(lessonId: Int?, characterIds: IntArray?) =
                QuizFragment().apply {
                    arguments = Bundle().apply {
                        if (lessonId != null) putInt(LESSON_ID, lessonId)
                        if (characterIds != null) putIntArray(CHARACTER_IDS, characterIds)
                    }
                }
    }

    private fun setQuizCharacter(character: Character) {
        quizTranslation!!.text = if (character.translationKey != null)
            resources.getText(resources.getIdentifier(
                character.translationKey,
                "string",
                context!!.packageName
        )) else character.translation
    }
}
