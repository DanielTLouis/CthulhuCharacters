package com.example.cthulhucharacter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.preference.PreferenceManager
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.google.gson.Gson
import org.json.JSONObject
import java.io.BufferedOutputStream
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID


class DisplayCharactersActivity : ComponentActivity() {
    var artsArray: ArrayList<String> = arrayListOf()
    var languageArray: ArrayList<String> = arrayListOf()
    var scienceArray: ArrayList<String> = arrayListOf()

    var itemsArray: ArrayList<String> = arrayListOf()
    var weaponNameArray: ArrayList<String> = arrayListOf()
    var weaponDamageArray: ArrayList<String> = arrayListOf()
    var weaponNumOfAttacksArray: ArrayList<String> = arrayListOf()
    var weaponRangeArray: ArrayList<String> = arrayListOf()
    var weaponAmmoArray: ArrayList<String> = arrayListOf()
    var weaponMalfArray: ArrayList<String> = arrayListOf()

    var gearArray: ArrayList<String> = arrayListOf()
    var playerAttackName: ArrayList<String> = arrayListOf()
    var playerDamage: ArrayList<String> = arrayListOf()
    var playerNumOfA: ArrayList<String> = arrayListOf()
    var playerRange: ArrayList<String> = arrayListOf()
    var playerAmmo: ArrayList<String> = arrayListOf()
    var playerMalf: ArrayList<String> = arrayListOf()

    var characterList: ArrayList<Character> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_displaycharacters)

        val backToMainButton : Button = findViewById(R.id.backToMainButton)
        val jsonTextView : TextView = findViewById(R.id.jsonTextView)
        val characterLayout : LinearLayout = findViewById(R.id.characterLayout)

        createCharacterList()
        if(characterList.isEmpty()){
            jsonTextView.text = "Please Create A Character"
        }else {
            /*var tempString: String = ""
            for (i in characterList) {
                tempString += i.createJson() + "\n"
            }
            jsonTextView.text = tempString*/
            jsonTextView.text= ""
        }
        for(i in characterList) {
            val charName : TextView = TextView(this)
            charName.text = i.name
            val choiceButton : Button = Button(this)
            choiceButton.text = "Select"
            choiceButton.setOnClickListener(){
                //move to next activity with selected character
                val intent = Intent(this@DisplayCharactersActivity, DisplayCharacterSheetActivity::class.java)
                intent.putExtra("name", i.name)
                startActivity(intent)
            }
            val deleteButton : Button = Button(this)
            deleteButton.text = "Delete"
            deleteButton.setOnClickListener(){
                for(j in characterList) {
                    if(j.name == i.name) {
                        characterList.remove(j)
                        updateCharacterListAndJson()
                    }
                }
                val intent = Intent(this@DisplayCharactersActivity, DisplayCharactersActivity::class.java)
                startActivity(intent)
            }
            val horLayout : LinearLayout = LinearLayout(this)
            horLayout.orientation = LinearLayout.HORIZONTAL
            horLayout.addView(charName)
            horLayout.addView(choiceButton)
            horLayout.addView(deleteButton)
            characterLayout.addView(horLayout)
        }

        /**
         * Button
         */
        backToMainButton.setOnClickListener(){
            val intent = Intent(this@DisplayCharactersActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun updateCharacterListAndJson(){
        var tempString : String = "{\"Characters\" : \n[\n"
        val filename = "Characters.json"
        val output : OutputStream
        for(i in characterList.indices){
            if(i < characterList.size-1) {
                tempString += characterList[i].createJson() + ",\n"
            } else{
                tempString += characterList[i].createJson()
            }
        }
        tempString += "]\n}"
        openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(tempString.toByteArray())
        }
    }

    fun createCharacterList(){
        try {
            val json : String
            val bufferedReader: BufferedReader = File(filesDir, "Characters.json").bufferedReader()
            json = bufferedReader.use{ it.readText() }
            var tempCharacter : Character = Character()

            var obj = JSONObject(json)
            val characterArray = obj.getJSONArray("Characters")
            for (i in 0 .. characterArray.length()) {
                tempCharacter = Character()
                val characterDetail = characterArray.getJSONObject(i)
                tempCharacter.loadCharacter(characterDetail.toString())
                characterList.add(tempCharacter)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun loadCharacter(): Character {
        var tempCharacter : Character = Character()
        try {
            val bufferedReader: BufferedReader = File(filesDir, "NewCharacter.json").bufferedReader()
            val json = bufferedReader.use { it.readText() }
            tempCharacter.loadCharacter(json)
        }
        catch(e: IOException){
            tempCharacter.name = "Did not find file to open"
        }
        var holdArtsList : List<String> = listOf()
        holdArtsList = tempCharacter.arts.split(';')
        for(i in holdArtsList){
            if(i.filter { it.isLetterOrDigit() } != "") {
                artsArray.add(i)
            }
        }
        var holdLanguageList : List<String> = listOf()
        holdLanguageList = tempCharacter.languages.split(';')
        for(i in holdLanguageList){
            if(i.filter { it.isLetterOrDigit() } != "") {
                languageArray.add(i)
            }
        }
        var holdScienceList : List<String> = listOf()
        holdScienceList = tempCharacter.sciences.split(';')
        for(i in holdScienceList){
            if(i.filter { it.isLetterOrDigit() } != "") {
                scienceArray.add(i)
            }
        }
        var holdGearList : List<String> = listOf()
        holdGearList = tempCharacter.gearAndPossessions.split(';')
        for(i in holdGearList){
            if(i.filter { it.isLetterOrDigit() } != ""){
                gearArray.add(i)
            }
        }

        var holdAttackName : List<String> = listOf()
        holdAttackName = tempCharacter.attackName.split(';')
        for(i in holdAttackName){
            if(i.filter {it.isLetterOrDigit()} != ""){
                playerAttackName.add(i)
            }
        }
        var holdAttackDamage : List<String> = listOf()
        holdAttackDamage = tempCharacter.damage.split(';')
        for(i in holdAttackDamage){
            if(i.filter {it.isLetterOrDigit()} != ""){
                playerDamage.add(i)
            }
        }
        var holdAttackNumOfAt : List<String> = listOf()
        holdAttackNumOfAt = tempCharacter.numOfAttacks.split(';')
        for(i in holdAttackNumOfAt){
            if(i.filter {it.isLetterOrDigit()} != ""){
                playerNumOfA.add(i)
            }
        }
        var holdAttackAmmo : List<String> = listOf()
        holdAttackAmmo = tempCharacter.ammo.split(';')
        for(i in holdAttackAmmo){
            if(i.filter {it.isLetterOrDigit()} != ""){
                playerAmmo.add(i)
            }
        }
        var holdAttackMalf : List<String> = listOf()
        holdAttackMalf = tempCharacter.malf.split(';')
        for(i in holdAttackMalf){
            if(i.filter {it.isLetterOrDigit()} != ""){
                playerMalf.add(i)
            }
        }
        var holdAttackRange : List<String> = listOf()
        holdAttackRange = tempCharacter.range.split(';')
        for(i in holdAttackRange){
            if(i.filter {it.isLetterOrDigit()} != ""){
                playerRange.add(i)
            }
        }

        return tempCharacter
    }
}
