package com.kaiserpudding.howtheywrite.characterList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.model.Character
import com.kaiserpudding.howtheywrite.shared.multiSelect.MultiSelectAdapter

/**
 * Adapter for the recyclerView for characters.
 *
 * Classes using this must implement [MultiSelectAdapter.MultiSelectAdapterItemInteractionListener]
 * to handle interaction events with an item in the list.
 *
 * @property context
 * @property listener
 */
class CharacterListAdapter(
        private val context: Context,
        listener: MultiSelectAdapterItemInteractionListener)
    : MultiSelectAdapter<Character>(listener) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    override val viewHolderId = R.id.characterTextView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultiSelectViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item_character, parent, false)
        return createViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MultiSelectViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        val representation: String?
        representation = if (list != null) {
            val current = list!![position]
            current.hanzi
        } else {
            //TODO NOT displayed
            "No Characters"
        }
        holder.textView.text = representation
    }

    fun setCharacters(characters: List<Character>) {
        list = characters
        notifyDataSetChanged()

    }

    override fun getItemCount(): Int {
        return if (list != null) list!!.size
        else 0
    }

    override fun getMyItemId(position: Int): Int {
        return if (list != null) list!![position].id
        else 0
    }
}
