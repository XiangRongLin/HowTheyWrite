//package com.kaiserpudding.howtheywrite.characterDetail
//
//import androidx.lifecycle.ViewModelProviders
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.TextView
//import com.kaiserpudding.howtheywrite.R
//import com.kaiserpudding.howtheywrite.model.Character
//
//class CharacterDetailActivity : AppCompatActivity() {
//
//    private var hanziTextView: TextView? = null
//    private var pinyinTextView: TextView? = null
//    private var translationTextView: TextView? = null
//
//    private var characterDetailViewModel: CharacterDetailViewModel? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_character_detail)
//
//        //TODO proper handling for 0
//        val characterId = intent.getIntExtra(REPLY_CHARACTER_ID, 0)
//
//        //TODO proper fix for racing condition of initChar and getChar
//        characterDetailViewModel = ViewModelProviders.of(
//                this, CharacterDetailViewModelFactory(application, characterId))
//                .get(CharacterDetailViewModel::class.java)
//        var character: Character? = null
//
//        while (character == null) {
//            character = characterDetailViewModel!!.character
//        }
//
//        hanziTextView = findViewById(R.id.hanzi)
//        hanziTextView!!.text = character.hanzi
//        pinyinTextView = findViewById(R.id.pinyin)
//        pinyinTextView!!.text = character.pinyin
//        translationTextView = findViewById(R.id.translation)
//        if (character.translationKey != null) {
//            translationTextView!!.setText(
//                    resources.getIdentifier(
//                            character.translationKey,
//                            "string",
//                            applicationContext.packageName
//                    )
//            )
//        } else {
//            translationTextView!!.text = character.translation
//        }
//    }
//
//    companion object {
//
//        const val REPLY_CHARACTER_ID = "howTheyWrite.CHARACTER_ID"
//    }
//}
