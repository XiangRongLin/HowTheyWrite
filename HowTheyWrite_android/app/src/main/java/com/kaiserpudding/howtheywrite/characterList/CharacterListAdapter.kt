package com.kaiserpudding.howtheywrite.characterList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.model.Character

/**
 * Adapter for the recyclerView for characters.
 *
 * Classes using this must implement [CharacterListAdapter.OnCharacterListAdapterItemInteractionListener]
 * to handle interaction events with an item in the list.
 *
 * @property context
 * @property listener
 */
internal class CharacterListAdapter(
        private val context: Context,
        private val listener: OnCharacterListAdapterItemInteractionListener)
    : RecyclerView.Adapter<CharacterListAdapter.CharacterViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private var characters: List<Character>? = null
    private var selectedCharactersId: MutableSet<Int> = mutableSetOf()
    private val _inSelectionMode = MutableLiveData<Boolean>()
    private val _numberOfSelected = MutableLiveData<Int>()
    val inSelectionMode: LiveData<Boolean>
        get() = _inSelectionMode
    val numberOfSelected: LiveData<Int>
        get() = _numberOfSelected
    val selectedCharacterId
        get() = selectedCharactersId.toIntArray()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item_character, parent, false)
        return CharacterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val representation: String?
        representation = if (characters != null) {
            val current = characters!![position]
            current.hanzi
        } else {
            //TODO NOT displayed
            "No Characters"
        }
        holder.characterItemView.text = representation
        holder.itemView.isActivated = selectedCharactersId.contains(characters!![position].id)
    }

    fun setCharacters(characters: List<Character>) {
        this.characters = characters
        notifyDataSetChanged()
    }

    fun toggleSelected(id: Int) {
        if (selectedCharactersId.contains(id)) selectedCharactersId.remove(id)
        else selectedCharactersId.add(id)
        _inSelectionMode.postValue(!selectedCharactersId.isEmpty())
        val a = selectedCharactersId.size
        _numberOfSelected.postValue(selectedCharactersId.size)
        notifyDataSetChanged()
    }

    fun clearSelected() {
        selectedCharactersId.clear()
        _inSelectionMode.postValue(false)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (characters != null)
            characters!!.size
        else
            0
    }

    inner class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val characterItemView: TextView

        init {
            itemView.setOnClickListener { onAdapterItemPressed() }
            itemView.setOnLongClickListener { onAdapterItemLongPressed(); true }
            characterItemView = itemView.findViewById(R.id.characterTextView)
        }

        private fun onAdapterItemPressed() {
            listener.onCharacterListAdapterInteraction(characters!![adapterPosition].id)
        }

        private fun onAdapterItemLongPressed() {
            listener.onCharacterListAdapterLongInteraction(characters!![adapterPosition].id)
        }
    }

    interface OnCharacterListAdapterItemInteractionListener {
        fun onCharacterListAdapterInteraction(characterId: Int)
        fun onCharacterListAdapterLongInteraction(characterId: Int)
    }
}
