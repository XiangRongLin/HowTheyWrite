package com.kaiserpudding.howtheywrite.model

import androidx.room.*
import java.util.*

@Entity(tableName = "lessons", indices = [Index(value = ["name"], unique = true)])
class Lesson(
        @ColumnInfo(name = "name") var name: String
) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    @Ignore
    var characters: MutableList<Character> = LinkedList()

//    constructor(id: Int, name: String, characters: MutableList<Character>): this(name) {
//        this.characters = characters
//    }

    override fun toString(): String {
        return id.toString() + name
    }

}
