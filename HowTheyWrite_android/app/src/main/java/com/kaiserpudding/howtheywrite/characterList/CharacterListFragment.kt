package com.kaiserpudding.howtheywrite.characterList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kaiserpudding.howtheywrite.R

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
    : Fragment(), CharacterListAdapter.OnCharacterListAdapterItemInteractionListener {
    private var lessonId: Int? = null
    private var listener: OnCharacterListFragmentInteractionListener? = null
    private lateinit var characterListViewModel: CharacterListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val safeArgs: CharacterListFragmentArgs by navArgs()
        lessonId = safeArgs.lessonId
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
                            this, CharacterListViewModelFactory(activity!!.application, tmpId))
                            .get(CharacterListViewModel::class.java)

                } else {
                    ViewModelProviders.of(this).get(CharacterListViewModel::class.java)
                }
        characterListViewModel.characters.observe(this,
                Observer { characters ->
                    adapter.setCharacters(characters)
                }
        )

        val newCharacterFab = view.findViewById<FloatingActionButton>(R.id.new_character_fab)
        newCharacterFab.setOnClickListener { onToNewCharacterFabPressed() }

        val toQuizButton = view.findViewById<Button>(R.id.to_quiz_button)
        toQuizButton.setOnClickListener { onToQuizButtonPressed() }

        return view
    }

    fun onToQuizButtonPressed() {
        lessonId?.let { listener?.onToQuizInteraction(it) }
    }

    override fun onCharacterListAdapterInteraction(characterId: Int) {
        listener?.onCharacterListItemInteraction(characterId)
    }

    fun onToNewCharacterFabPressed() {
        listener?.onNewCharacterInteraction()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCharacterListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnCharacterListFragmentInteractionListener")
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
    interface OnCharacterListFragmentInteractionListener {
        fun onToQuizInteraction(lessonId: Int)
        fun onCharacterListItemInteraction(characterId: Int)
        fun onNewCharacterInteraction()
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
