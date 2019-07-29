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
    internal var filterType = BaseCharacterListFragment.ALL_FILTER
        set(value) {
            field = value
            filter.filter(constraints)
        }
    internal lateinit var constraints: CharSequence

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
                constraints = constraint

                val results = FilterResults()
                if (constraint.isEmpty()) {
                    results.values = list
                    return results
                }
                val resultList = mutableListOf<Character>()
                for (character in list!!) {
                    if (fitsFilter(character, constraint, filterType)) {
                        resultList.add(character)
                    }
                }
                results.values = resultList

                return results
            }

            private fun fitsFilter(character: Character, constraint: CharSequence, filter: String): Boolean {
                return when (filter) {
                    ALL_FILTER -> {
                        fitsAllFilter(character, constraint)
                    }
                    HANZI_FILTER -> {
                        fitsHanziFilter(character, constraint)
                    }
                    PINYIN_FILTER -> {
                        fitsPinyinFilter(character, constraint)
                    }
                    TRANSLATION_FILTER -> {
                        fitsTranslationFilter(character, constraint)
                    }
                    else -> {
                        false
                    }
                }
            }

            private fun fitsAllFilter(character: Character, constraint: CharSequence): Boolean {
                return (fitsHanziFilter(character, constraint)
                        || fitsPinyinFilter(character, constraint)
                        || fitsTranslationFilter(character, constraint))
            }

            private fun fitsHanziFilter(character: Character, constraint: CharSequence): Boolean {
                return character.hanzi.contains(constraint, true)
            }

            private fun fitsPinyinFilter(character: Character, constraint: CharSequence): Boolean {
                return StringUtils.stripAccents(character.pinyin).contains(constraint, true)
            }

            private fun fitsTranslationFilter(character: Character, constraint: CharSequence): Boolean {
                return (character.isCustom && character.translation!!.contains(constraint, true))
                        || (!character.isCustom && context.resources.getText(
                        context.resources.getIdentifier(
                                character.translationKey!!
                                , "string"
                                , context.packageName))
                        .contains(constraint, true))

            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                filteredList = results.values as List<Character>
                notifyDataSetChanged()
            }

        }
    }

    companion object {
        const val ALL_FILTER = "all_filter"
        const val HANZI_FILTER = "hanzi_filter"
        const val PINYIN_FILTER = "pinyin_filter"
        const val TRANSLATION_FILTER = "translation_filter"
    }
}
