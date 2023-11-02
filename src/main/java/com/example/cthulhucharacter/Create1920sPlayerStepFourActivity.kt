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

//personal skill point selection
/**
 * Create1920sPlayerStepFourActivity Class
 * * This activity is the step that allows the user to spend personal points on the skills they wish to improve
 * ** The user can select any skill to improve (unlike the professional skills being a select few)
 * ** As they select the skills to improve the personal skill points will go down until it reaches 0
 * *** and then the user will no longer be able to spend points
 */
class Create1920sPlayerStepFourActivity : ComponentActivity() {

    var skillPoints : Int = 0
    var artsArray : ArrayList<String> = arrayListOf()
    var languageArray : ArrayList<String> = arrayListOf()
    var scienceArray : ArrayList<String> = arrayListOf()

    /**
     * Function updateArray
     * *This function will update the Arts, Language, and Science arrays from the json file so that
     * * the unique states for those can be displayed
     * * This function takes 3 parameters
     * ** An arr variable that is a string of the json chracter
     * ** a name varaible which is also a string in the name of the element to be updated
     * ** And an num that is an Int for the number the to increase by that much of if it does not exsitst
     * * in the array it will be added with the starting number of the inputs
     * *This app does not return anything
     */
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

        /**
         * Function saveStat
         * * Will save a specific stat and write it to the json file
         * * This function will take 2 input parameters
         * ** One is the name (String) of the skill that needs to be saved
         * ** Two is the new value (Int)  for the skill to save
         */
        //add deliminator for if it needs the array lists
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
        /**
         * backToStepThreeButton Button
         * will set all improved skills back to original number and then send the user back to the beginning of step 3
         */
        backToStepThreeButton.setOnClickListener(){
            newCharacter.accounting = 5 //105 would be checked
            newCharacter.anthropology  = 1 // 1 would be unchecked
            newCharacter.appraise  = 5
            newCharacter.archaeology  = 1
            newCharacter.artAndCraft  = 5
            newCharacter.arts  = ""
            newCharacter.creditRating  = 0
            newCharacter.charm = 15
            newCharacter.climb = 20
            newCharacter.cthulhuMythos = 0
            newCharacter.disguise = 5
            newCharacter.driveAuto = 20
            newCharacter.elcRepair = 10
            newCharacter.fastTalk = 5
            newCharacter.fightingBrawl = 25
            newCharacter.firearmsHandgun = 20
            newCharacter.firearmsRifle = 25
            newCharacter.firstAid = 30
            newCharacter.history = 5
            newCharacter.intimidate = 15
            newCharacter.jump = 20
            newCharacter.languageOther = 1
            newCharacter.languages = ""
            newCharacter.languageOwn = 0
            newCharacter.law = 5
            newCharacter.libraryUse = 20
            newCharacter.listen = 20
            newCharacter.locksmith = 1
            newCharacter.mechRepair = 10
            newCharacter.medicine = 1
            newCharacter.naturalWorld = 10
            newCharacter.navigate = 10
            newCharacter.occult = 5
            newCharacter.persuade = 10
            newCharacter.pilot = 1
            newCharacter.psychoanalysis = 1
            newCharacter.psychology = 10
            newCharacter.ride = 5
            newCharacter.science = 1
            newCharacter.sciences = ""
            newCharacter.sleightOfHand = 10
            newCharacter.spotHidden = 25
            newCharacter.stealth = 20
            newCharacter.survival = 10
            newCharacter.swim = 20
            newCharacter.thro = 20
            newCharacter.track = 10
            saveCharacter(newCharacter)
            val intent = Intent(this@Create1920sPlayerStepFourActivity, Create1920sPlayerStepThreeActivity::class.java)
            startActivity(intent)
        }

    }

    /**
     * Function addRow
     * *This function will create views and dynamically append them onto the main linear layout for the step
     * ** These will be the views for each of the skills that are avalible to improve for the user.
     * * there are 12 input paramaters
     * **
     * **
     * **
     * **
     * **     fill this out
     * **
     * **
     * **
     * **
     * **
     * **
     * * This function does not return any value, at the end it will add a row of a linear layout view that is horizontally oriented
     * ** with the name of the skill, the current numeric value of the skill, an increase button to increase the skill, and a decrease button.
     */
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