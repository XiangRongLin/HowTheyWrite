package com.kaiserpudding.howtheywrite.characterList

import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.characterDetail.CharacterDetailActivity
import com.kaiserpudding.howtheywrite.lessonDetail.LessonDetailViewModel
import com.kaiserpudding.howtheywrite.lessonDetail.LessonDetailViewModelFactory

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CharacterListFragment.OnCharacterListFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CharacterListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class CharacterListFragment
    : Fragment(), CharacterListAdapter.OnCharacterListAdapterItemInteractionListener{
    private var lessonId: Int? = null
    private var listenerCharacterList: OnCharacterListFragmentInteractionListener? = null
    private lateinit var characterListViewModel: CharacterListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            lessonId = it.getInt(ARG_LESSON_ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_character_list, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.character_recyclerview)
        val adapter = CharacterListAdapter(view.context, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(view.context, 5)

        val tmpId = lessonId
        characterListViewModel =
                if (tmpId != null) {
                    ViewModelProviders.of(
                            this, LessonDetailViewModelFactory(activity!!.application, tmpId))
                            .get(CharacterListViewModel::class.java)

                } else {
                    ViewModelProviders.of(this).get(CharacterListViewModel::class.java)
                }

        val toQuiz = view.findViewById<Button>(R.id.to_quiz_button)
        toQuiz.setOnClickListener { onToQuizButtonPressed() }

        return view
    }

    fun onToQuizButtonPressed() {
        listenerCharacterList?.onToQuizButtonInteraction(lessonId)
    }

    override fun onCharacterListAdapterInteraction(characterId: Int) {
        val intent = Intent(context, CharacterDetailActivity::class.java)
        intent.putExtra(CharacterDetailActivity.REPLY_CHARACTER_ID, characterId)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCharacterListFragmentInteractionListener) {
            listenerCharacterList = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnCharacterListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listenerCharacterList = null
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
    interface OnCharacterListFragmentInteractionListener {
        fun onToQuizButtonInteraction(lessonId: Int?)
    }

    companion object {

        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_LESSON_ID = "lessonId"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CharacterListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(lessonId: Int) =
                CharacterListFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_LESSON_ID, lessonId)
                    }
                }


        @JvmStatic
        fun newInstance() = CharacterListFragment()
    }
}
