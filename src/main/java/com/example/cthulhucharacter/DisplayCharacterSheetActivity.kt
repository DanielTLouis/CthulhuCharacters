package com.example.cthulhucharacter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import kotlinx.coroutines.selects.select
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.OutputStream
import kotlin.random.Random

class DisplayCharacterSheetActivity : ComponentActivity() {
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
        setContentView(R.layout.activity_displaycharactersheet)

        val BackToCharSelectButton : Button = findViewById(R.id.BackToCharSelectButton)
        val jsonTextView : TextView = findViewById(R.id.jsonTextView)
        val nameTextView : TextView = findViewById(R.id.nameTextView)
        val occupationTextView : TextView = findViewById(R.id.occupationTextView)
        val genderTextView : TextView = findViewById(R.id.genderTextView)
        val residenceTextView : TextView = findViewById(R.id.residenceTextView)
        val ageTextView : TextView = findViewById(R.id.ageTextView)
        val strTextView : TextView = findViewById(R.id.strTextView)
        val rollResultTextView : TextView = findViewById(R.id.rollResultTextView)
        val rollSTRButton : Button = findViewById(R.id.rollSTRButton)
        val intTextView : TextView = findViewById(R.id.intTextView)
        val conTextView : TextView = findViewById(R.id.conTextView)
        val dexTextView : TextView = findViewById(R.id.dexTextView)
        val sizTextView : TextView = findViewById(R.id.sizTextView)
        val powTextView : TextView = findViewById(R.id.powTextView)
        val intRollButton : Button = findViewById(R.id.intRollButton)
        val powRollbutton : Button = findViewById(R.id.powRollButton)
        val conRollButton : Button = findViewById(R.id.conRollButton)
        val dexRollButton : Button = findViewById(R.id.dexRollButton)
        val eduTextView : TextView = findViewById(R.id.eduTextView)
        val maxHealthtextView : TextView = findViewById(R.id.maxHealthtextView)
        val maxSanityTextView : TextView = findViewById(R.id.maxSanityTextView)
        val maxLuckTextView : TextView = findViewById(R.id.maxLuckTextView)
        val currentHealthTextView : TextView = findViewById(R.id.currentHealthTextView)
        val currentSanityTextView : TextView = findViewById(R.id.currentSanityTextView)
        val currentLuckTextView : TextView = findViewById(R.id.currentLuckTextView)
        val appTextView : TextView = findViewById(R.id.appTextView)
        val healthMinusButton : Button = findViewById(R.id.healthMinusButton)
        val healthPlusButton : Button = findViewById(R.id.healthPlusButton)
        val sanityMinusButton : Button = findViewById(R.id.sanityMinusButton)
        val sanityPlusButton : Button = findViewById(R.id.sanityPlusButton)
        val luckMinusButton : Button = findViewById(R.id.luckMinusButton)
        val luckPlusButton : Button = findViewById(R.id.luckPlusButton)
        val eduRollButton : Button = findViewById(R.id.eduRollButton)
        val accountingTextView : TextView = findViewById(R.id.accountingTextView)

        createCharacterList()
        var selectedCharacter : Character = Character()

        for(i in characterList.indices){
            if(characterList[i].name == intent.getStringExtra("name")){
                selectedCharacter = characterList[i]
            }
        }
        jsonTextView.text = "Character: \n" + selectedCharacter.createJson()
        nameTextView.text = selectedCharacter.name
        occupationTextView.text = selectedCharacter.occupation
        residenceTextView.text = selectedCharacter.residence
        ageTextView.text = selectedCharacter.age.toString()
        if(selectedCharacter.gender == 1){
            genderTextView.text = "Male"
        }
        else if(selectedCharacter.gender == 2){
            genderTextView.text = "Female"
        }else{
            genderTextView.text = "Other"
        }

        strTextView.text = selectedCharacter.strength.toString()
        conTextView.text = selectedCharacter.constitution.toString()
        dexTextView.text = selectedCharacter.dexterity.toString()
        intTextView.text = selectedCharacter.intelligence.toString()
        sizTextView.text = selectedCharacter.size.toString()
        powTextView.text = selectedCharacter.power.toString()
        eduTextView.text = selectedCharacter.education.toString()
        appTextView.text = selectedCharacter.appearance.toString()

        maxHealthtextView.text = selectedCharacter.maxHitPoints.toString()
        maxSanityTextView.text = selectedCharacter.startingSanity.toString()
        maxLuckTextView.text = selectedCharacter.startingLuck.toString()
        currentHealthTextView.text = selectedCharacter.hitPoints.toString()
        currentSanityTextView.text = selectedCharacter.sanity.toString()
        currentLuckTextView.text = selectedCharacter.luck.toString()

        accountingTextView.text = selectedCharacter.accounting.toString()

        /**
         * Buttons
         */
        BackToCharSelectButton.setOnClickListener(){
            val intent = Intent(this@DisplayCharacterSheetActivity, DisplayCharactersActivity::class.java)
            startActivity(intent)
        }
        rollSTRButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.strength){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.strength / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.strength / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        intRollButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.intelligence){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.intelligence / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.intelligence / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        powRollbutton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.power){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.power / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.power / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        conRollButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.constitution){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.constitution / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.constitution / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        dexRollButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.dexterity){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.dexterity / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.dexterity / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        healthMinusButton.setOnClickListener(){
            selectedCharacter.hitPoints -= 1
            currentHealthTextView.text = selectedCharacter.hitPoints.toString()
            saveCharacter(selectedCharacter)
            for(i in characterList.indices){
                if(characterList[i].name == intent.getStringExtra("name")){
                     characterList[i] = selectedCharacter
                }
            }
            updateCharacterListAndJson()
        }
        healthPlusButton.setOnClickListener(){
            selectedCharacter.hitPoints += 1
            currentHealthTextView.text = selectedCharacter.hitPoints.toString()
            saveCharacter(selectedCharacter)
            for(i in characterList.indices){
                if(characterList[i].name == intent.getStringExtra("name")){
                    characterList[i] = selectedCharacter
                }
            }
            updateCharacterListAndJson()
        }
        sanityMinusButton.setOnClickListener(){
            selectedCharacter.sanity -= 1
            currentSanityTextView.text = selectedCharacter.sanity.toString()
            saveCharacter(selectedCharacter)
            for(i in characterList.indices){
                if(characterList[i].name == intent.getStringExtra("name")){
                    characterList[i] = selectedCharacter
                }
            }
            updateCharacterListAndJson()
        }
        sanityPlusButton.setOnClickListener(){
            selectedCharacter.sanity += 1
            currentSanityTextView.text = selectedCharacter.sanity.toString()
            saveCharacter(selectedCharacter)
            for(i in characterList.indices){
                if(characterList[i].name == intent.getStringExtra("name")){
                    characterList[i] = selectedCharacter
                }
            }
            updateCharacterListAndJson()
        }
        luckMinusButton.setOnClickListener(){
            selectedCharacter.luck -= 1
            currentLuckTextView.text = selectedCharacter.luck.toString()
            saveCharacter(selectedCharacter)
            for(i in characterList.indices){
                if(characterList[i].name == intent.getStringExtra("name")){
                    characterList[i] = selectedCharacter
                }
            }
            updateCharacterListAndJson()
        }
        luckPlusButton.setOnClickListener(){
            selectedCharacter.luck += 1
            currentLuckTextView.text = selectedCharacter.luck.toString()
            saveCharacter(selectedCharacter)
            for(i in characterList.indices){
                if(characterList[i].name == intent.getStringExtra("name")){
                    characterList[i] = selectedCharacter
                }
            }
            updateCharacterListAndJson()
        }
        eduRollButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.education){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.education / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.education / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
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
            for (i in 0 until characterArray.length()) {
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
        holdAttackNumOfAt = tempCharacter.damage.split(';')
        for(i in holdAttackNumOfAt){
            if(i.filter {it.isLetterOrDigit()} != ""){
                playerNumOfA.add(i)
            }
        }
        var holdAttackAmmo : List<String> = listOf()
        holdAttackAmmo = tempCharacter.damage.split(';')
        for(i in holdAttackAmmo){
            if(i.filter {it.isLetterOrDigit()} != ""){
                playerAmmo.add(i)
            }
        }
        var holdAttackMalf : List<String> = listOf()
        holdAttackMalf = tempCharacter.damage.split(';')
        for(i in holdAttackMalf){
            if(i.filter {it.isLetterOrDigit()} != ""){
                playerMalf.add(i)
            }
        }
        var holdAttackRange : List<String> = listOf()
        holdAttackRange = tempCharacter.damage.split(';')
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
