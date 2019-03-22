package com.kaiserpudding.howtheywrite.characterList

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.model.Character
import com.kaiserpudding.howtheywrite.quiz.QuizActivity

private const val LESSON_ID = "howTheyWrite.LESSON_ID"

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [CharacterListFragment.OnCharacterListFragmentInteractionListener] interface.
 */
class CharacterListFragment : Fragment() {

    companion object {
        const val NEW_CHAR_ACTIVITY_REQUEST_CODE = 2


        //Factory method to create a new Instance of this fragment
        @JvmStatic
        fun newInstance() = CharacterListFragment()

        //Factory method to create a new Instance of this fragment
        @JvmStatic
        fun newInstance(lessonId: Int) =
                CharacterListFragment().apply {
                    arguments = Bundle().apply {
                        putInt(LESSON_ID, lessonId)
                    }
                }
    }

    private var listener: OnCharacterListFragmentInteractionListener? = null
    private lateinit var adapter: CharacterListAdapter
    private lateinit var characterListViewModel: CharacterListViewModel
    private var lessonId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { it ->
            lessonId = it.getInt(LESSON_ID)
        }

        adapter = CharacterListAdapter(context!!, listener)

        //Get ID of lesson which characters will be displayed. Default is 0, which displays all characters
        val bundle = arguments
        var lessonId = 0
        if (bundle != null) {
            lessonId = bundle.getInt(LESSON_ID)
        }
        characterListViewModel = ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return CharacterListViewModel(activity!!.application, lessonId) as T
            }
        })[CharacterListViewModel::class.java]
        characterListViewModel.characters.observe(this, Observer<List<Character>> { adapter.setCharacters(it!!) })

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_character_list, container, false)

        // Set the adapter
        val displayMetrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(displayMetrics)

        val width = displayMetrics.widthPixels

        val recyclerView = view.findViewById<RecyclerView>(R.id.character_recyclerview)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(view.context, width / 216)

        //Set OnClickListener for new Character Button
        val fab = view.findViewById<FloatingActionButton>(R.id.add_character_fab)
        fab.setOnClickListener {
            val intent = Intent(context, NewCharacterActivity::class.java)
            startActivityForResult(intent, NEW_CHAR_ACTIVITY_REQUEST_CODE)
        }

        //Set OnClickListener for startQuiz Button
        val toQuiz = view.findViewById<Button>(R.id.to_quiz_button)
        toQuiz.setOnClickListener{
            val intent = Intent(context, QuizActivity::class.java)
            intent.putExtra(QuizActivity.LESSON_ID, lessonId)
            startActivity(intent)
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCharacterListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnLessonListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NEW_CHAR_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val character = Character(
                    data!!.getStringExtra(NewCharacterActivity.REPLY_CHAR_HANZI),
                    data.getStringExtra(NewCharacterActivity.REPLY_CHAR_PINYIN),
                    null,
                    data.getStringExtra(NewCharacterActivity.REPLY_CHAR_TRANSLATION),
                    true)
            characterListViewModel.insertCharacter(character)
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnCharacterListFragmentInteractionListener {
        fun onCharacterListFragmentInteraction(character: Character)
    }
}
