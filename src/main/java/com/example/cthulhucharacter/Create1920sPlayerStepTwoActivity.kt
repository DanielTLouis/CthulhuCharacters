package com.example.cthulhucharacter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.google.gson.Gson
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.time.temporal.Temporal

class Create1920sPlayerStepTwoActivity : ComponentActivity() {
    var artsArray : ArrayList<String> = arrayListOf()
    var languageArray : ArrayList<String> = arrayListOf()
    var scienceArray : ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create1920splayersteptwo)

        var newCharacter : Character = loadCharacter()

        val testing : TextView = findViewById(R.id.testing)
        //testing.text = newCharacter.arts[0].toString() + " " + newCharacter.arts[0].toString()

        val stepTwoPartTwoRollLayout : View = findViewById(R.id.stepTwoPartTwoRollLayout)
        val stepTwoPartTwoStandardArrayLayout : View = findViewById(R.id.stepTwoPartTwoStandardArrayLayout)
        stepTwoPartTwoRollLayout.visibility = View.GONE
        stepTwoPartTwoStandardArrayLayout.visibility = View.GONE

        val stndArrayVsRollspinner : Spinner = findViewById(R.id.stndArrayVsRollspinner)
        if(stndArrayVsRollspinner != null){
            val adapter = ArrayAdapter(
                this,
                //add stanard array later "Standard Array"
                android.R.layout.simple_spinner_item, arrayListOf<String>("Roll for Stats")
            )
            stndArrayVsRollspinner.adapter = adapter
        }

        val strengthStatTextView : TextView = findViewById(R.id.strengthStatTextView)
        val constitutionStatsTextView : TextView = findViewById(R.id.constitutionStatsTextView)
        val dexterityStatsTextView : TextView = findViewById(R.id.dexterityStatsTextView)
        val intelligenceStatsTextView : TextView = findViewById(R.id.intelligenceStatsTextView)
        val sizeStatsTextView : TextView = findViewById(R.id.sizeStatsTextView)
        val powerStatsTextView : TextView = findViewById(R.id.powerStatsTextView)
        val appearanceStatsTextView : TextView = findViewById(R.id.appearanceStatsTextView)
        val educationStatsTextView3 : TextView = findViewById(R.id.educationStatsTextView3)

        val hitPointsStatsTextView : TextView = findViewById(R.id.hitPointsStatsTextView)
        val magicPointsStatsTextView : TextView = findViewById(R.id.magicPointsStatsTextView)
        val luckStatsTextView : TextView = findViewById(R.id.luckStatsTextView)
        val sanityStatsTextView : TextView = findViewById(R.id.sanityStatsTextView)

        fun rollStats(){
            //roll stats
            /**
             * add a re-roll button
             */
            strengthStatTextView.text = (((1..6).random() + (1..6).random() + (1..6).random())*5).toString()
            constitutionStatsTextView.text = (((1..6).random() + (1..6).random() + (1..6).random())*5).toString()
            dexterityStatsTextView.text = (((1..6).random() + (1..6).random() + (1..6).random())*5).toString()
            intelligenceStatsTextView.text = (((1..6).random() + (1..6).random() + 6)*5).toString()
            sizeStatsTextView.text = (((1..6).random() + (1..6).random() + 6)*5).toString()
            powerStatsTextView.text = (((1..6).random() + (1..6).random() + (1..6).random())*5).toString()
            appearanceStatsTextView.text = (((1..6).random() + (1..6).random() + (1..6).random())*5).toString()
            educationStatsTextView3.text = (((1..6).random() + (1..6).random() + 6)*5).toString()

            hitPointsStatsTextView.text = ((constitutionStatsTextView.text.toString().toInt() + sizeStatsTextView.text.toString().toInt()) / 10).toString()
            magicPointsStatsTextView.text = (powerStatsTextView.text.toString().toInt() / 5).toString()
            luckStatsTextView.text = (((1..6).random() + (1..6).random() + (1..6).random())*5).toString()
            sanityStatsTextView.text = powerStatsTextView.text.toString()
        }

        val printingChracterTestTextView3 : TextView = findViewById(R.id.printingChracterTestTextView3)
        //printingChracterTestTextView3.text = newCharacter.name

        /**
         * Buttons
         */

        //will return to pervious screen back to step one
        val backToStepOneButton : Button = findViewById(R.id.backToStepOneButton)
        backToStepOneButton.setOnClickListener(){
            //go to next step
            val intent = Intent(this@Create1920sPlayerStepTwoActivity, Create1920sPlayerStepOneActivity::class.java)
            startActivity(intent)
        }
        //continueButton2
        val continueButton2 : Button = findViewById(R.id.continueButton2)
        continueButton2.setOnClickListener(){
            val stndArrayVsRollspinner : Spinner = findViewById(R.id.stndArrayVsRollspinner)
            if(stndArrayVsRollspinner.selectedItem.toString() == "Roll for Stats"){
                val stepTwoPartTwoRollLayout : View = findViewById(R.id.stepTwoPartTwoRollLayout)
                stepTwoPartTwoRollLayout.visibility = View.VISIBLE

                rollStats()
            }
            else{
                val stepTwoPartTwoStandardArrayLayout : View = findViewById(R.id.stepTwoPartTwoStandardArrayLayout)
                stepTwoPartTwoStandardArrayLayout.visibility = View.VISIBLE
            }
        }

        val continueButton3 : Button = findViewById(R.id.continueButton3)
        continueButton3.setOnClickListener(){
            newCharacter.strength = strengthStatTextView.text.toString().toInt()
            newCharacter.constitution = constitutionStatsTextView.text.toString().toInt()
            newCharacter.dexterity = dexterityStatsTextView.text.toString().toInt()
            newCharacter.intelligence = intelligenceStatsTextView.text.toString().toInt()
            newCharacter.size = sizeStatsTextView.text.toString().toInt()
            newCharacter.power = powerStatsTextView.text.toString().toInt()
            newCharacter.appearance = appearanceStatsTextView.text.toString().toInt()
            newCharacter.education = educationStatsTextView3.text.toString().toInt()

            newCharacter.hitPoints = hitPointsStatsTextView.text.toString().toInt()
            newCharacter.maxHitPoints = hitPointsStatsTextView.text.toString().toInt()
            newCharacter.magicPoints = magicPointsStatsTextView.text.toString().toInt()
            newCharacter.maxMagicPoints = magicPointsStatsTextView.text.toString().toInt()
            newCharacter.luck = luckStatsTextView.text.toString().toInt()
            newCharacter.startingLuck = luckStatsTextView.text.toString().toInt()
            newCharacter.sanity = sanityStatsTextView.text.toString().toInt()
            newCharacter.startingSanity = sanityStatsTextView.text.toString().toInt()

            newCharacter.dodge = dexterityStatsTextView.text.toString().toInt() / 2
            when(strengthStatTextView.text.toString().toInt() + sizeStatsTextView.text.toString().toInt()){
                in 2..64 -> {
                    newCharacter.damageBonus = "-2"
                    newCharacter.build = -2
                }
                in 65..84 -> {
                    newCharacter.damageBonus = "-1"
                    newCharacter.build = -1
                }
                in 85..124 -> {
                    newCharacter.damageBonus = "None"
                    newCharacter.build = 0
                }
                in 125..164 ->{
                    newCharacter.damageBonus = "+1D4"
                    newCharacter.build = 1
                }
                in 165..205 -> {
                    newCharacter.damageBonus = "+1D6"
                    newCharacter.build = 2
                }

            }
            newCharacter.move = moveStat(dexterityStatsTextView.text.toString().toInt(), strengthStatTextView.text.toString().toInt(), sizeStatsTextView.text.toString().toInt())

            saveCharacter(newCharacter)

            //go to next step
            val intent = Intent(this@Create1920sPlayerStepTwoActivity, Create1920sPlayerStepThreeActivity::class.java)
            startActivity(intent)
            //stepTwoPartTwoRollLayout.visibility = View.GONE

        }

        val rerollButton : Button = findViewById(R.id.rerollButton)
        rerollButton.setOnClickListener(){
            rollStats()
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

    fun moveStat(dex : Int, str : Int, siz : Int):Int{
        if(dex > siz && str > siz){
            return 9
        }else if( dex > siz || str > siz){
            return 8
        }else{
            return 7
        }
        return -1
    }
}
