package com.kaiserpudding.howtheywrite.characterList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.model.Character
import com.kaiserpudding.howtheywrite.shared.multiSelect.MultiSelectAdapter
import org.apache.commons.lang3.StringUtils

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
    : MultiSelectAdapter<Character>(listener), Filterable {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    override val viewHolderId = R.id.characterTextView
    private var filteredList = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultiSelectViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item_character, parent, false)
        return createViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MultiSelectViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        val representation: String?
        representation = if (filteredList != null) {
            val current = filteredList!![position]
            current.hanzi
        } else {
            //TODO NOT displayed
            "No Characters"
        }
        holder.textView.text = representation
    }

    fun setCharacters(characters: List<Character>) {
        list = characters
        filteredList = list
        notifyDataSetChanged()
    }

    fun resetCharacters() {
        filteredList = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (filteredList != null) filteredList!!.size
        else 0
    }

    override fun getMyItemId(position: Int): Long {
        return if (filteredList != null) filteredList!![position].id
        else 0
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val results = FilterResults()
                if (constraint.isEmpty()) {
                    results.values = list
                } else {
                    val resultList = mutableListOf<Character>()
                    for (character in list!!) {
                        if (character.hanzi.contains(constraint, true)
                                || StringUtils.stripAccents(character.pinyin).contains(constraint, true)
                                || (character.isCustom && character.translation!!
                                        .contains(constraint, true))
                                || (!character.isCustom && context.resources.getText(
                                        context.resources.getIdentifier(
                                                character.translationKey!!
                                                , "string"
                                                , context.packageName))
                                        .contains(constraint, true))) {
                            resultList.add(character)
                        }

                    }
                    results.values = resultList
                }
                return results
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                filteredList = results.values as List<Character>
                notifyDataSetChanged()
            }

        }
    }

}
