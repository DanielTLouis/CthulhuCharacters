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


class Create1920sPlayerStepSevenActivity : ComponentActivity() {
    var artsArray: ArrayList<String> = arrayListOf()
    var languageArray: ArrayList<String> = arrayListOf()
    var scienceArray: ArrayList<String> = arrayListOf()

    var itemsArray : ArrayList<String> = arrayListOf()
    var weaponNameArray : ArrayList<String> = arrayListOf()
    var weaponDamageArray : ArrayList<String> = arrayListOf()
    var weaponNumOfAttacksArray : ArrayList<String> = arrayListOf()
    var weaponRangeArray : ArrayList<String> = arrayListOf()
    var weaponAmmoArray : ArrayList<String> = arrayListOf()
    var weaponMalfArray : ArrayList<String> = arrayListOf()

    var gearArray : ArrayList<String> = arrayListOf()
    var playerAttackName : ArrayList<String> = arrayListOf()
    var playerDamage : ArrayList<String> = arrayListOf()
    var playerNumOfA : ArrayList<String> = arrayListOf()
    var playerRange : ArrayList<String> = arrayListOf()
    var playerAmmo : ArrayList<String> = arrayListOf()
    var playerMalf : ArrayList<String> = arrayListOf()

    var characterList : ArrayList<Character> = arrayListOf()

    private fun updateArray(arr: String, name: String, num: Int) {
        var index: Int = 0
        if (arr == "Art") {
            for (i in artsArray.indices) {
                if (artsArray[i].contains(name)) {
                    index = i
                    break
                }
            }
            var tempList: List<String> = artsArray.get(index).split('.')
            var tempNum = tempList[1].toInt() + num
            var tempText: String = tempList[0] + "." + tempNum.toString()
            artsArray.removeAt(index)
            artsArray.add(tempText)
        } else if (arr == "Lan") {
            for (i in languageArray.indices) {
                if (languageArray[i].contains(name)) {
                    index = i
                    break
                }
            }
            var tempList: List<String> = languageArray.get(index).split('.')
            var tempNum = tempList[1].toInt() + num
            var tempText: String = tempList[1] + "." + tempNum.toString()
            languageArray.removeAt(index)
            languageArray.add(tempText)
        } else if (arr == "Science") {
            for (i in scienceArray.indices) {
                if (scienceArray[i].contains(name)) {
                    index = i
                    break
                }
            }
            var tempList: List<String> = scienceArray.get(index).split('.')
            var tempNum = tempList[1].toInt() + num
            var tempText: String = tempList[1] + "." + tempNum.toString()
            scienceArray.removeAt(index)
            scienceArray.add(tempText)
        }
        return
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create1920splayerstepseven)

        val cashButton : Button = findViewById(R.id.cashButton)
        val spendingButton : Button = findViewById(R.id.spendingButton)
        val assetButton : Button = findViewById(R.id.assetButton)
        val otherInvestigatorsButton : Button = findViewById(R.id.otherInvestigatorsButton)
        val finializeButton : Button = findViewById(R.id.finializeButton)
        val backToStepSixButton : Button = findViewById(R.id.backToStepSixButton)

        var newCharacter : Character = loadCharacter()


        /**
         * Button
         */
        backToStepSixButton.setOnClickListener(){
            val intent = Intent(this@Create1920sPlayerStepSevenActivity, Create1920sPlayerStepSixActivity::class.java)
            startActivity(intent)
        }

        cashButton.setOnClickListener(){
            val cashEditText : EditText = findViewById(R.id.cashEditText)
            newCharacter.cash = cashEditText.text.toString().toInt()
            saveCharacter(newCharacter)
        }
        spendingButton.setOnClickListener(){
            val spendingEditText : EditText = findViewById(R.id.spendingEditText)
            newCharacter.spendingLevel = spendingEditText.text.toString().toInt()
            saveCharacter(newCharacter)
        }
        assetButton.setOnClickListener(){
            val assetEditText : EditText = findViewById(R.id.assetEditText)
            var tempString = newCharacter.assets
            val assetsTextView : TextView = findViewById(R.id.assetsTextView)
            tempString += assetEditText.text.toString() + "; "
            newCharacter.assets = tempString
            assetsTextView.text = tempString.replace(";", "\n")
            saveCharacter(newCharacter)
        }
        otherInvestigatorsButton.setOnClickListener(){
            val player1EditText : EditText = findViewById(R.id.player1EditText)
            val player2EditText : EditText = findViewById(R.id.player2EditText)
            val player3EditText : EditText = findViewById(R.id.player3EditText)
            val player4EditText : EditText = findViewById(R.id.player4EditText)
            val player5EditText : EditText = findViewById(R.id.player5EditText)
            val character1EditText : EditText = findViewById(R.id.character1EditText)
            val character2EditText : EditText = findViewById(R.id.character2EditText)
            val character3EditText : EditText = findViewById(R.id.character3EditText)
            val character4EditText : EditText = findViewById(R.id.character4EditText)
            val character5EditText : EditText = findViewById(R.id.character5EditText)
            var tempPlayerString = player1EditText.text.toString() +
                    player2EditText.text.toString() +
                    player3EditText.text.toString() +
                    player4EditText.text.toString() +
                    player5EditText.text.toString()
            var tempCharacterString : String = character1EditText.text.toString() +
                    character2EditText.text.toString() +
                    character3EditText.text.toString() +
                    character4EditText.text.toString() +
                    character5EditText.text.toString()
            newCharacter.characterNames = tempCharacterString
            newCharacter.playerNames = tempPlayerString
            saveCharacter(newCharacter)
        }
        finializeButton.setOnClickListener(){
            newCharacter.era = "1920s"
            saveCharacter(newCharacter)
            createCharacterList(newCharacter)
            updateCharacterListAndJson()
            var temp : String = "Characters\n"
            for(i in characterList){
                temp += i.createJson() + "\n"
            }
            val assetsTextView : TextView = findViewById(R.id.assetsTextView)

            val tesingTextView : TextView = findViewById(R.id.tesingTextView)
            var temp2 : String = ""
            for(i in characterList){
                temp2 += i.name + " "
            }
            tesingTextView.text = temp2
            val intent = Intent(this@Create1920sPlayerStepSevenActivity, MainActivity::class.java)
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
                tempString += characterList[i].createJson() + "\n"
            }
        }
        tempString += "]\n}"
        openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(tempString.toByteArray())
        }
    }

    fun createCharacterList(newCharacter: Character){
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
        characterList.add(newCharacter)
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


    //print character class to TextView this will change to save json file
    private fun saveCharacter(newCharacter : Character) {
        try {
            val filename = "NewCharacter.json"
            val output : OutputStream
            var tempString : String = ""
            for(i in artsArray){
                if(i.filter { it.isLetterOrDigit() } != "") {
                    tempString += i + "; "
                }
            }
            tempString.dropLast(2)
            newCharacter.arts = tempString

            tempString = ""
            for(i in languageArray){
                if(i.filter { it.isLetterOrDigit() } != "") {
                    tempString += i + "; "
                }
            }
            tempString.dropLast(2)
            newCharacter.languages = tempString

            tempString = ""
            for(i in scienceArray){
                if(i.filter { it.isLetterOrDigit() } != "") {
                    tempString += i + "; "
                }
            }
            tempString.dropLast(2)
            newCharacter.sciences = tempString

            tempString = ""
            for(i in gearArray){
                if(i.filter { it.isLetterOrDigit() } != ""){
                    tempString += i + "; "
                }
            }
            tempString.dropLast(2)
            newCharacter.gearAndPossessions = tempString

            tempString = ""
            for(i in playerAttackName){
                if(i.filter { it.isLetterOrDigit() } != ""){
                    tempString += i + "; "
                }
            }
            tempString.dropLast(2)
            newCharacter.attackName = tempString
            tempString = ""
            for(i in playerDamage){
                if(i.filter { it.isLetterOrDigit() } != ""){
                    tempString += i + "; "
                }
            }
            tempString.dropLast(2)
            newCharacter.damage = tempString
            tempString = ""
            for(i in playerNumOfA){
                tempString += i.toString() + "; "
            }
            tempString.dropLast(2)
            newCharacter.numOfAttacks = tempString
            tempString = ""
            for(i in playerAmmo){
                if(i.filter { it.isLetterOrDigit() } != ""){
                    tempString += i + "; "
                }
            }
            tempString.dropLast(2)
            newCharacter.ammo = tempString
            tempString = ""
            for(i in playerMalf){
                if(i.filter { it.isLetterOrDigit() } != ""){
                    tempString += i + "; "
                }
            }
            tempString.dropLast(2)
            newCharacter.malf = tempString
            tempString = ""
            for(i in playerRange){
                if(i.filter { it.isLetterOrDigit() } != ""){
                    tempString += i + "; "
                }
            }
            tempString.dropLast(2)
            newCharacter.range = tempString

            openFileOutput(filename, Context.MODE_PRIVATE).use {
                it.write(newCharacter.createJson().toByteArray())
            }
        }
        catch(e: IOException){

        }
    }
}
