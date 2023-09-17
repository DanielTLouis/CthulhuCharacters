package com.example.cthulhucharacter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.OutputStream

class Create1920sPlayerStepFiveActivity : ComponentActivity() {

    var artsArray : ArrayList<String> = arrayListOf()
    var languageArray : ArrayList<String> = arrayListOf()
    var scienceArray : ArrayList<String> = arrayListOf()

    private fun updateArray(arr : String, name : String, num : Int){
        var index : Int = 0
        if(arr == "Art") {
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
        }else if(arr == "Lan") {
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
        } else if(arr == "Science") {
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
        setContentView(R.layout.activity_create1920splayerstepfive)

        val backToStepFourButtonn : Button = findViewById(R.id.backToStepFourButtonn)
        val continueToStepSixButton : Button = findViewById(R.id.continueToStepSixButton)
        val myStoryEditTextTextMultiLine2 : EditText = findViewById(R.id.myStoryEditTextTextMultiLine2)
        val personalDescriptionEditTextTextMultiLine : EditText = findViewById(R.id.personalDescriptionEditTextTextMultiLine)
        val traitEditTextTextMultiLine2 : EditText = findViewById(R.id.traitEditTextTextMultiLine2)
        val ideologyAndBeliefsEditTextTextMultiLine : EditText = findViewById(R.id.ideologyAndBeliefsEditTextTextMultiLine)
        val injuriesAndScarsEditTextTextMultiLine2 : EditText = findViewById(R.id.injuriesAndScarsEditTextTextMultiLine2)
        val significantPeopleEditTextTextMultiLine : EditText = findViewById(R.id.significantPeopleEditTextTextMultiLine)
        val phobiasAndManiasEditTextTextMultiLine2 : EditText = findViewById(R.id.phobiasAndManiasEditTextTextMultiLine2)
        val meaningfulLocationsEditTextTextMultiLine : EditText = findViewById(R.id.meaningfulLocationsEditTextTextMultiLine)
        val arcaneTomesAndSpellsEditTextTextMultiLine2 : EditText = findViewById(R.id.arcaneTomesAndSpellsEditTextTextMultiLine2)
        val treasuredPossessionsEditTextTextMultiLine : EditText = findViewById(R.id.treasuredPossessionsEditTextTextMultiLine)
        val encountersWithStrangeEntitiesEditTextTextMultiLine2 : EditText = findViewById(R.id.encountersWithStrangeEntitiesEditTextTextMultiLine2)

        var newCharacter : Character = loadCharacter()

        /**
         * Buttons
         */
        backToStepFourButtonn.setOnClickListener(){
            val intent = Intent(this@Create1920sPlayerStepFiveActivity, Create1920sPlayerStepFourActivity::class.java)
            startActivity(intent)
        }
        continueToStepSixButton.setOnClickListener(){
            newCharacter.myStory = myStoryEditTextTextMultiLine2.text.toString()
            newCharacter.personalDescription = personalDescriptionEditTextTextMultiLine.text.toString()
            newCharacter.traits = traitEditTextTextMultiLine2.text.toString()
            newCharacter.ideologyAndBeliefs = ideologyAndBeliefsEditTextTextMultiLine.text.toString()
            newCharacter.injuriesAndScars = injuriesAndScarsEditTextTextMultiLine2.text.toString()
            newCharacter.significantPeople = significantPeopleEditTextTextMultiLine.text.toString()
            newCharacter.phobiasAndManias = phobiasAndManiasEditTextTextMultiLine2.text.toString()
            newCharacter.meaningfulLocations = meaningfulLocationsEditTextTextMultiLine.text.toString()
            newCharacter.arcaneTomesAndSpells = arcaneTomesAndSpellsEditTextTextMultiLine2.text.toString()
            newCharacter.treasuredPossessions = treasuredPossessionsEditTextTextMultiLine.text.toString()
            newCharacter.encountersWithStrangeEntities = encountersWithStrangeEntitiesEditTextTextMultiLine2.text.toString()

            saveCharacter(newCharacter)

            val intent = Intent(this@Create1920sPlayerStepFiveActivity, Create1920sPlayerStepSixActivity::class.java)
            startActivity(intent)
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

            openFileOutput(filename, Context.MODE_PRIVATE).use {
                it.write(newCharacter.createJson().toByteArray())
            }
        }
        catch(e: IOException){

        }
    }
}