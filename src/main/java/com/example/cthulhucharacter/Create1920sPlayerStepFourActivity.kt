package com.example.cthulhucharacter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.OutputStream


class Create1920sPlayerStepFourActivity : ComponentActivity() {

    var skillPoints : Int = 0
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
        setContentView(R.layout.activity_create1920splayerstepfour)

        val testing4 : TextView = findViewById(R.id.testing4)
        val backToStepThreeButton : Button = findViewById(R.id.backToStepThreeButton)
        val textView3 : TextView = findViewById(R.id.skillpointssTextView)
        val pointsTextView : TextView = findViewById(R.id.pointsTextView)
        val skillLayout : LinearLayout = findViewById(R.id.skillLayout)
        val warningContentTextView3 : TextView = findViewById(R.id.warningContentTextView3)

        var newCharacter : Character = loadCharacter()
        skillPoints = newCharacter.intelligence * 2
        pointsTextView.text = skillPoints.toString()
        //testing4.text = newCharacter.createJson()

        //add dilimitor for if it needs the array lists
        fun saveStat(text : String, num : Int) {
            when(text){
                "Accounting"->newCharacter.accounting = num
                "Anthropology"->newCharacter.anthropology = num
                "Appraise"->newCharacter.appraise = num
                "Archaeology"->newCharacter.archaeology = num
                "ArtandCraft"->newCharacter.artAndCraft = num
                "Charm"->newCharacter.charm = num
                "Climb"->newCharacter.climb = num
                "Cthulhu Mythos"->newCharacter.cthulhuMythos = num
                "Disguise"->newCharacter.disguise = num
                "Drive Auto"->newCharacter.driveAuto = num
                "Elec Repair"->newCharacter.elcRepair = num
                "Fast Talk"->newCharacter.fastTalk = num
                "Fighting(Brawl)"->newCharacter.fightingBrawl = num
                "Firearms(Handgun)"->newCharacter.firearmsHandgun = num
                "Firearms(Rifle)"->newCharacter.firearmsRifle = num
                "First Aid"->newCharacter.firstAid = num
                "History"->newCharacter.history = num
                "Intimidate"->newCharacter.intimidate = num
                "Jump"->newCharacter.jump = num
                "Language Other"->newCharacter.languageOther = num
                "Language Own"->newCharacter.languageOwn = num
                "Law"->newCharacter.law = num
                "Library Use"->newCharacter.libraryUse = num
                "Listen"->newCharacter.listen = num
                "Locksmith"->newCharacter.locksmith = num
                "Mechanical"->newCharacter.mechRepair = num
                "Medicine"->newCharacter.medicine = num
                "Natural World"->newCharacter.naturalWorld = num
                "Navigate"->newCharacter.navigate = num
                "Occult"->newCharacter.occult = num
                "Persuade"->newCharacter.persuade = num
                "Pilot"->newCharacter.pilot = num
                "Psychoanalysis"->newCharacter.psychoanalysis = num
                "Psychology"->newCharacter.psychology = num
                "Ride"->newCharacter.ride = num
                "Science"->newCharacter.science = num
                "Sleight of Hand"->newCharacter.sleightOfHand = num
                "Spot Hidden"->newCharacter.spotHidden = num
                "Stealth"->newCharacter.stealth = num
                "Survival"->newCharacter.survival = num
                "Throw"->newCharacter.thro = num
                "Track"->newCharacter.track = num
                else->return
            }
            saveCharacter(newCharacter)
        }

        saveCharacter(newCharacter)

        var skills : ArrayList<String> = arrayListOf(
            "Accounting",
            "Anthropology",
            "Appraise",
            "Archaeology",
            "ArtandCraft",
            "Charm",
            "Climb",
            "Cthulhu Mythos",
            "Disguise",
            "Drive Auto",
            "Elec Repair",
            "Fast Talk",
            "Fighting(Brawl)",
            "Firearms(Handgun)",
            "Firearms(Rifle)",
            "First Aid",
            "History",
            "Intimidate",
            "Jump",
            "Language Other",
            "Language Own",
            "Law",
            "Library Use",
            "Listen",
            "Locksmith",
            "Mechanical",
            "Medicine",
            "Natural World",
            "Navigate",
            "Occult",
            "Persuade",
            "Pilot",
            "Psychoanalysis",
            "Psychology",
            "Ride",
            "Science",
            "Sleight of Hand",
            "Spot Hidden",
            "Stealth",
            "Survival",
            "Throw",
            "Track"
        )
        for(i in artsArray){
            if(i.filter { it.isLetterOrDigit() } != "") {
                skills.add(i)
                Log.d("ArtArray", "["+i+"]")
            }
        }
        for(i in languageArray){
            if(i.filter { it.isLetterOrDigit() } != "") {
                skills.add(i)
            }
        }
        for(i in scienceArray){
            if(i.filter { it.isLetterOrDigit() } != "") {
                skills.add(i)
            }
        }

        for(i in skills) {
            var newLayout : LinearLayout = LinearLayout(this)
            var tempTextView: TextView = TextView(this)
            var tempNumberView: TextView = TextView(this)
            var addButton: Button = Button(this)
            var subButton: Button = Button(this)
            var containerLayout: LinearLayout = LinearLayout(this)
            var dividerView = View(this)
            newLayout.orientation = LinearLayout.VERTICAL
            addRow(
                tempTextView,
                tempNumberView,
                addButton,
                subButton,
                containerLayout,
                dividerView,
                i,
                newLayout,
                newCharacter,
                {
                    if (skillPoints <= 0) {
                        warningContentTextView3.text = "You are out of points to spend."
                    } else if (tempNumberView.text.toString().toInt() == 75) {
                        warningContentTextView3.text =
                            "You cannot have a stat exceed 75 points."
                    } else {
                        tempNumberView.text =
                            (tempNumberView.text.toString().toInt() + 1).toString()
                        saveStat(tempTextView.text.toString(),tempNumberView.text.toString().toInt())
                        skillPoints -= 1
                        pointsTextView.text = skillPoints.toString()
                        warningContentTextView3.text = ""
                    }
                },
                {
                    val tempChar : Character = Character()
                    if (tempNumberView.text.toString().toInt() == tempChar.skillPicker(tempTextView.text.toString())||
                        tempNumberView.text.toString().toInt() == 1) {
                        warningContentTextView3.text =
                            "You can not subtract any more from that stat."
                    } else {
                        tempNumberView.text =
                            (tempNumberView.text.toString().toInt() - 1).toString()
                        saveStat(tempTextView.text.toString(),tempNumberView.text.toString().toInt())
                        skillPoints += 1
                        pointsTextView.text = skillPoints.toString()
                        warningContentTextView3.text = ""
                    }
                },
                true
            )
            skillLayout.addView(newLayout)
        }
        val continueButton : Button = Button(this)
        continueButton.text = "Continue to Next Step"
        continueButton.setOnClickListener(){
            saveCharacter(newCharacter)
            val intent = Intent(this@Create1920sPlayerStepFourActivity, Create1920sPlayerStepFiveActivity::class.java)
            startActivity(intent)
        }
        skillLayout.addView(continueButton)
        /**
         * Buttons
         */
        backToStepThreeButton.setOnClickListener(){
            val intent = Intent(this@Create1920sPlayerStepFourActivity, Create1920sPlayerStepThreeActivity::class.java)
            startActivity(intent)
        }

    }

    private fun addRow(tempTextView: TextView, tempNumberView: TextView, addButton: Button, subButton: Button, containerLayout: LinearLayout, dividerView : View, i: String, newLayout: LinearLayout, newCharacter : Character, lmbdadd: () -> Unit, lmbdsub: () -> Unit, divide : Boolean) {

        dividerView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, 1
        )
        dividerView.setBackgroundResource(R.color.purple_500)
        containerLayout.orientation = LinearLayout.HORIZONTAL
        tempTextView.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
            25.0f
        )
        tempNumberView.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
            25.0f
        )
        addButton.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
            25.0f
        )
        subButton.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
            25.0f
        )

        tempTextView.text = i
        tempTextView.tag = i.filter { it.isLetterOrDigit() } + "TextView"

        tempNumberView.text = newCharacter.skillPicker(i).toString()
        tempNumberView.tag =
            "number" + i.filter { it.isLetterOrDigit() } + "TextView"

        addButton.text = "+"
        addButton.tag = "add" + i.filter { it.isLetterOrDigit() } + "Button"
        addButton.setOnClickListener() {
            lmbdadd()
        }

        subButton.text = "-"
        subButton.tag = "sub" + i.filter { it.isLetterOrDigit() } + "Button"
        subButton.setOnClickListener() {
            lmbdsub()
        }


        containerLayout.addView(tempTextView)
        containerLayout.addView(tempNumberView)
        containerLayout.addView(subButton)
        containerLayout.addView(addButton)
        newLayout.addView(containerLayout)

        if(divide) {
            newLayout.addView(dividerView)
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