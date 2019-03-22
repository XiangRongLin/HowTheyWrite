package com.kaiserpudding.howtheywrite.characterList

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kaiserpudding.howtheywrite.R
import com.kaiserpudding.howtheywrite.model.Character

class CharacterListAdapter
    constructor(context: Context, private val listener: CharacterListFragment.OnCharacterListFragmentInteractionListener?)
    : RecyclerView.Adapter<CharacterListAdapter.CharacterViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private var characters: List<Character>? = null

    inner class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val characterItemView: TextView = itemView.findViewById(R.id.characterTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val itemView = inflater.inflate(R.layout.character_recyclerview_item, parent, false)
        return CharacterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val current = characters!![position]
        holder.characterItemView.text = current.hanzi
        holder.characterItemView.setOnClickListener{
            listener?.onCharacterListFragmentInteraction(characters!![position])
        }
    }

    internal fun setCharacters(characters: List<Character>) {
        this.characters = characters
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (characters != null) characters!!.size
        else 0
    }

}
