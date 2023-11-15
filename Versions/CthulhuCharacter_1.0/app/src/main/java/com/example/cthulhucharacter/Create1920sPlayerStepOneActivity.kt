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
import java.io.OutputStream
import java.util.UUID

/**
 * Create1920sPlayerStepOneActivity Class
 * * First step in character creation, the user will set the values of base information
 * ** Mandatory: name
 * ** Optional: gender, age, residence
 */
class Create1920sPlayerStepOneActivity : ComponentActivity() {
    var artsArray : ArrayList<String> = arrayListOf()
    var languageArray : ArrayList<String> = arrayListOf()
    var scienceArray : ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create1920splayerstepone)
        var newCharacter : Character = Character()

        val genders : ArrayList<String> = arrayListOf("Male", "Female", "Other")
        val genderSpinner : Spinner = findViewById(R.id.genderSpinner)
        if (genderSpinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, genders
            )
            genderSpinner.adapter = adapter
        }

        val ages : ArrayList<Int> = arrayListOf()
        for(i in 1..105){
            ages.add(i)
        }
        val ageSpinner : Spinner = findViewById(R.id.ageSpinner)
        if(ageSpinner != null){
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, ages
            )
            ageSpinner.adapter = adapter
        }

        /**
         * Buttons
         */

        /**
         * backToHomeButton Button
         * * Will return to the MainActivity
         */
        val backToHomeButton : Button = findViewById(R.id.backToHomeButton)
        backToHomeButton.setOnClickListener{
            val intent = Intent(this@Create1920sPlayerStepOneActivity, MainActivity::class.java)
            startActivity(intent)
        }

        /**
         * continueButton1 Button
         * * Will load the next step in the creation process
         */
        val continueButton1 : Button = findViewById(R.id.continueButton1)
        continueButton1.setOnClickListener(){
            val characterNameEditText : EditText = findViewById(R.id.nameEditText)
            if (characterNameEditText.text.toString() == ""){
                val WarningMessageTextView : TextView = findViewById(R.id.WarningMessageTextView)
                WarningMessageTextView.text = "Please Enter A Name"
            }else {
                newCharacter.name = characterNameEditText.text.toString()

                val residenceEditText: EditText = findViewById(R.id.residenceEditText)
                newCharacter.residence = residenceEditText.text.toString()

                when (genderSpinner.selectedItem.toString()) {
                    "Male" -> newCharacter.gender = 1
                    "Female" -> newCharacter.gender = 2
                    "Other" -> newCharacter.gender = 3
                    else -> newCharacter.gender = 0
                }


                val titleTextView1: TextView = findViewById(R.id.titleTextView1)
                val genderTextView: TextView = findViewById(R.id.genderTextView)
                val ageTextView: TextView = findViewById(R.id.ageTextView)

                newCharacter.age = ageSpinner.selectedItem.toString().toInt()

                //save to json file
                saveCharacter(newCharacter)

                //go to next step
                val intent = Intent(
                    this@Create1920sPlayerStepOneActivity,
                    Create1920sPlayerStepTwoActivity::class.java
                )
                startActivity(intent)
                val printingChracterTestTextView2: TextView =
                    findViewById(R.id.printingChracterTestTextView2)
                //printingChracterTestTextView2.text = newCharacter.createJson()
            }

        }
    }

    /**
     * Function loadCharacter
     * * This function will load a character from the temporary json file and store the values into the corresponding stats of a Character object
     * * This function takes no input parameters
     * * It will return a Character object that has all the stats replaced with the corresponding json file
     */
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
            artsArray.add(i)
        }
        var holdLanguageList : List<String> = listOf()
        holdLanguageList = tempCharacter.languages.split(';')
        for(i in holdLanguageList){
            languageArray.add(i)
        }
        var holdScienceList : List<String> = listOf()
        holdScienceList = tempCharacter.sciences.split(';')
        for(i in holdScienceList){
            scienceArray.add(i)
        }

        return tempCharacter
    }

    /**
     * Function saveCharacter
     * * This function will save the json string created by the Character object to the temporary character json
     * ** This uses the characters createJson method to write to the external json file "NewCharacter.json"
     * * The function takes one parameter as imput
     * ** the input is a character object that the user wants to save to the json file
     * * the function will not return anything, at the end it will have saved the json string to the json file
     */
    //print character class to TextView this will change to save json file
    private fun saveCharacter(newCharacter : Character) {
        try {
            val filename = "NewCharacter.json"
            val output : OutputStream
            var tempString : String = ""
            for(i in artsArray){
                tempString += i + "; "
            }
            tempString.dropLast(2)
            newCharacter.arts = tempString

            tempString = ""
            for(i in languageArray){
                tempString += i + "; "
            }
            tempString.dropLast(2)
            newCharacter.languages = tempString

            tempString = ""
            for(i in scienceArray){
                tempString += i + "; "
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

