package com.kaiserpudding.howtheywrite.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import java.util.LinkedList

@Entity(tableName = "lessons", indices = arrayOf(Index(value = ["name"], unique = true)))
class Lesson(@field:ColumnInfo(name = "name")
             var name: String) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    @Ignore
    private var characters: MutableList<Character>

    init {
        this.characters = LinkedList()
    }

    override fun toString(): String {
        return id.toString() + name
    }

    fun getCharacters(): List<Character> {
        return characters
    }

    fun setCharacters(characters: MutableList<Character>) {
        this.characters = characters
    }

    fun addCharacter(character: Character) {
        this.characters.add(character)
    }

    fun removeCharacter(character: Character) {
        this.characters.remove(character)
    }
}
