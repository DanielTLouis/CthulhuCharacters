package com.example.cthulhucharacter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.compose.ui.text.style.TextDirection.Companion.Content
import kotlinx.coroutines.newFixedThreadPoolContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream


class Create1920sPlayerStepThreeActivity : ComponentActivity() {
    var occupationNames : ArrayList<String> = arrayListOf("-")
    var occupationsDescriptions : ArrayList<String> = arrayListOf("Description")
    var occupationCreditRattings : ArrayList<String> = arrayListOf("CreditRatting")
    var occupationPoints : ArrayList<Int> = arrayListOf(0)
    var occupationsSkills : ArrayList<String> = arrayListOf("Occupation Skills")
    var creditRating : String = ""
    var skills : ArrayList<String> = arrayListOf()
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

    //@SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create1920splayerstepthree)

        //views
        val stepThreeLayout : View = findViewById(R.id.stepThreeLayout)
        val stepThreePart2Layout : View = findViewById(R.id.stepThreePart2Layout)
        val backButton : Button = findViewById(R.id.backButton)
        val continueButton5 : Button = findViewById(R.id.contimueButton5)
        val backToStepTwoButton: Button = findViewById(R.id.backToStepTwoButton)
        val textView2 :TextView = findViewById(R.id.creditRatingSelecttitleTextView)
        val creditSpinner : Spinner = findViewById(R.id.creditSpinner)
        val skillLayout : LinearLayout = findViewById(R.id.skillLayout)
        val warningTextView : TextView = findViewById(R.id.warningTextView)
        val warningContentTextView2 : TextView = findViewById((R.id.warningContentTextView2))
        val skillpointssTextView : TextView = findViewById(R.id.skillpointssTextView)

        var newCharacter: Character = loadCharacter()

        stepThreePart2Layout.visibility = View.GONE

        fun buildOccupationList(){
            try {
                val json : String
                val  inputStream: InputStream = assets.open("Occupations1920s.json")
                json = inputStream.bufferedReader().use{it.readText()}


                var obj = JSONObject(json)
                val occupationsArray = obj.getJSONArray("Occupations")
                for (i in 0 until occupationsArray.length()) {
                    val occupationDetail = occupationsArray.getJSONObject(i)
                    occupationNames.add(occupationDetail.getString("name"))
                    occupationCreditRattings.add(occupationDetail.getString("Credit Rating"))
                    occupationPoints.add(occupationDetail.getInt("Occupation Points"))
                    occupationsSkills.add(occupationDetail.getString("Occupation Skills"))
                    occupationsDescriptions.add(occupationDetail.getString("Description"))
                }

            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

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


        buildOccupationList()

        val occupationSpinner : Spinner = findViewById(R.id.occupationSpinner)
        occupationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val OccupationDescriptionTextView : TextView = findViewById(R.id.OccupationDescriptionTextView)
                val skillsFromOccupationTextView : TextView = findViewById(R.id.skillsFromOccupationTextView)
                val occPointsTextView : TextView = findViewById(R.id.occPointsTextView)
                val creditRatingTextView :TextView = findViewById(R.id.creditRatingTextView)
                if (parent != null) {
                    occPointsTextView.text = occupationPoints[position].toString()
                    OccupationDescriptionTextView.text = occupationsDescriptions[position]
                    skillsFromOccupationTextView.text = occupationsSkills[position]
                    creditRatingTextView.text = occupationCreditRattings[position]
                }
            }

        }
        if(occupationSpinner != null){
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, occupationNames
            )
            occupationSpinner.adapter = adapter
        }



        /**
         * Buttons
         */
        //backToStepTwoButton
        backToStepTwoButton.setOnClickListener() {
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
            //go to last step
            val intent = Intent(
                this@Create1920sPlayerStepThreeActivity,
                Create1920sPlayerStepTwoActivity::class.java
            )
            startActivity(intent)
        }


        continueButton5.setOnClickListener(){
            if(occupationSpinner.selectedItem == "-"){
                warningTextView.text = "Please Select An Occupation"
            }else {
                stepThreeLayout.visibility = View.GONE
                stepThreePart2Layout.visibility = View.VISIBLE

                skillPoints = occupationPoints[occupationSpinner.selectedItemPosition]

                /**credit rating**/
                newCharacter.occupation = occupationSpinner.selectedItem.toString()
                creditRating = occupationCreditRattings[occupationSpinner.selectedItemPosition]
                val lowEnd: Int = (creditRating[0].plus("").plus(creditRating[1])).toInt()
                val highEnd: Int = (creditRating[3].plus("").plus(creditRating[4])).toInt()
                val bounds: ArrayList<Int> = arrayListOf()
                var i: Int = lowEnd
                while (i <= highEnd) {
                    bounds.add(i)
                    i++
                }
                if (creditSpinner != null) {
                    val adapter = ArrayAdapter(
                        this,
                        android.R.layout.simple_spinner_item, bounds
                    )
                    creditSpinner.adapter = adapter
                }

                val skillpointsTextView : TextView = findViewById(R.id.skillpointssTextView)
                skillpointsTextView.text = skillPoints.toString()

                /**generating views for skills**/
                //get the skills
                var tempName: String = ""
                for (i in occupationsSkills[occupationSpinner.selectedItemPosition]) {
                    if(i == '\"' || i == '[' || i == ']' || i == '{' || i == '}') {
                        continue
                    }else if (i == ',') {
                        skills.add(tempName)
                        tempName = ""
                    } else {
                        tempName += i
                    }
                }
                skills.add(tempName)

                //variables for the loop
                val newLayout: LinearLayout = LinearLayout(this)
                newLayout.removeAllViews()
                newLayout.orientation = LinearLayout.VERTICAL
                var skillPoints : Int = occupationPoints[occupationSpinner.selectedItemPosition]
                var needsResolved : ArrayList<String> = arrayListOf()
                var art : ArrayList<String> = arrayListOf()
                var artNumber : ArrayList<Int> = arrayListOf()
                var language : ArrayList<String> = arrayListOf()
                var languageNumber : ArrayList<Int> = arrayListOf()
                var science : ArrayList<String> = arrayListOf()
                var scienceNumber : ArrayList<Int> = arrayListOf()

                //start setting up the views in skills
                for (i in skills) {

                    if (i.contains("ArtandCraft") || i.contains("LanguageOther") || i.contains("Science")) {
                       //need to add a way to add new art and craft items
                        //add them to the arrays while also adding them to the way to get it
                        //also will need a way to save those numbers for the new items
                        var tempTextView: TextView = TextView(this)
                        var containerLayout: LinearLayout = LinearLayout(this)

                        containerLayout.orientation = LinearLayout.HORIZONTAL
                        tempTextView.layoutParams = LinearLayout.LayoutParams(
                            MATCH_PARENT,
                            MATCH_PARENT,
                            25.0f
                        )

                        var splitString : List<String> = i.split(":")
                        var holdList : List<String> = splitString[1].split(";")
                        var trueList : ArrayList<String> = arrayListOf()
                        tempTextView.text = splitString[0].filter { it.isLetterOrDigit() }

                        containerLayout.addView(tempTextView)
                        newLayout.addView(containerLayout)

                        for(k in holdList){
                            trueList.add(k.filter { it.isLetter() })
                        }

                        if(trueList[0] == "any") {
                            val inputText: EditText = EditText(this)
                            val inputButton: Button = Button(this)
                            inputText.hint = "Enter a Art or Craft here"
                            inputButton.text = "Add"
                            inputButton.setOnClickListener() {
                                var tempTextView: TextView = TextView(this)
                                var tempNumberView: TextView = TextView(this)
                                var addButton: Button = Button(this)
                                var subButton: Button = Button(this)
                                var containerLayout: LinearLayout = LinearLayout(this)
                                var dividerView = View(this)

                                saveCharacter(newCharacter)
                                addRow(
                                    tempTextView,
                                    tempNumberView,
                                    addButton,
                                    subButton,
                                    containerLayout,
                                    dividerView,
                                    inputText.text.toString(),
                                    newLayout,
                                    newCharacter,
                                    {
                                        if (skillPoints <= 0) {
                                            warningContentTextView2.text = "You are out of points to spend."
                                        } else if (tempNumberView.text.toString().toInt() == 75) {
                                            warningContentTextView2.text =
                                                "You cannot have a stat exceed 75 points."
                                        } else {
                                            tempNumberView.text =
                                                (tempNumberView.text.toString().toInt() + 1).toString()
                                            saveStat(tempTextView.text.toString(),tempNumberView.text.toString().toInt())
                                            skillPoints -= 1
                                            skillpointssTextView.text = skillPoints.toString()
                                            updateArray("Art", tempTextView.text.toString(),1)
                                            if(artsArray.contains(tempTextView.text.toString()+"."+(tempNumberView.text.toString().toInt() - 1).toString())){
                                                updateArray("Art", tempTextView.text.toString(),1)
                                            }else if(languageArray.contains(tempTextView.text.toString()+"."+(tempNumberView.text.toString().toInt() - 1).toString())){
                                                updateArray("Lan", tempTextView.text.toString(),1)
                                            }else if(scienceArray.contains(tempTextView.text.toString()+"."+(tempNumberView.text.toString().toInt() - 1).toString())){
                                                updateArray("Science", tempTextView.text.toString(),1)
                                            }
                                            warningContentTextView2.text = ""
                                        }
                                    },
                                    {
                                        val tempChar : Character = Character()
                                        if (tempNumberView.text.toString().toInt() == tempChar.skillPicker(tempTextView.text.toString())) {
                                            warningContentTextView2.text =
                                                "You can not subtract any more from that stat."
                                        } else {
                                            tempNumberView.text =
                                                (tempNumberView.text.toString().toInt() - 1).toString()
                                            saveStat(tempTextView.text.toString(),tempNumberView.text.toString().toInt())
                                            skillPoints += 1
                                            skillpointssTextView.text = skillPoints.toString()
                                            if(artsArray.contains(tempTextView.text.toString()+"."+(tempNumberView.text.toString().toInt() + 1).toString())){
                                                updateArray("Art", tempTextView.text.toString(),-1)
                                            }else if(languageArray.contains(tempTextView.text.toString()+"."+(tempNumberView.text.toString().toInt() + 1).toString())){
                                                updateArray("Lan", tempTextView.text.toString(),-1)
                                            }else if(scienceArray.contains(tempTextView.text.toString()+"."+(tempNumberView.text.toString().toInt() + 1).toString())){
                                                updateArray("Science", tempTextView.text.toString(),-1)
                                            }
                                            warningContentTextView2.text = ""
                                        }
                                    },
                                    false
                                )
                                newLayout.addView(dividerView)
                            }

                            containerLayout.addView(inputText)
                            containerLayout.addView(inputButton)
                            //newLayout.addView(containerLayout)

                        }else{
                            var dividerView = View(this)
                            dividerView.layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, 1
                            )
                            dividerView.setBackgroundResource(R.color.purple_500)
                            for(j in trueList){
                                var tempTextView: TextView = TextView(this)
                                var tempNumberView: TextView = TextView(this)
                                var addButton: Button = Button(this)
                                var subButton: Button = Button(this)
                                var containerLayout: LinearLayout = LinearLayout(this)
                                var dividerView = View(this)
                                if(i.contains("ArtandCraft")) {
                                    artsArray.add(j + ".5")
                                }else if(i.contains("LanguageOther")){
                                    languageArray.add(j + ".1")
                                }else if(i.contains("Science")){
                                    scienceArray.add(j + ".1")
                                }
                                saveCharacter(newCharacter)
                                addRow(
                                    tempTextView,
                                    tempNumberView,
                                    addButton,
                                    subButton,
                                    containerLayout,
                                    dividerView,
                                    j,
                                    newLayout,
                                    newCharacter,
                                    {
                                        if (skillPoints <= 0) {
                                            warningContentTextView2.text = "You are out of points to spend."
                                        } else if (tempNumberView.text.toString().toInt() == 75) {
                                            warningContentTextView2.text =
                                                "You cannot have a stat exceed 75 points."
                                        } else {
                                            tempNumberView.text =
                                                (tempNumberView.text.toString().toInt() + 1).toString()
                                            saveStat(tempTextView.text.toString(),tempNumberView.text.toString().toInt())
                                            skillPoints -= 1
                                            skillpointssTextView.text = skillPoints.toString()
                                            updateArray("Art", tempTextView.text.toString(),1)
                                            if(artsArray.contains(tempTextView.text.toString()+"."+(tempNumberView.text.toString().toInt() - 1).toString())){
                                                updateArray("Art", tempTextView.text.toString(),1)
                                            }else if(languageArray.contains(tempTextView.text.toString()+"."+(tempNumberView.text.toString().toInt() - 1).toString())){
                                                updateArray("Lan", tempTextView.text.toString(),1)
                                            }else if(scienceArray.contains(tempTextView.text.toString()+"."+(tempNumberView.text.toString().toInt() - 1).toString())){
                                                updateArray("Science", tempTextView.text.toString(),1)
                                            }
                                            warningContentTextView2.text = ""
                                        }
                                    },
                                    {
                                        val tempChar : Character = Character()
                                        if (tempNumberView.text.toString().toInt() == tempChar.skillPicker(tempTextView.text.toString())) {
                                            warningContentTextView2.text =
                                                "You can not subtract any more from that stat."
                                        } else {
                                            tempNumberView.text =
                                                (tempNumberView.text.toString().toInt() - 1).toString()
                                            saveStat(tempTextView.text.toString(),tempNumberView.text.toString().toInt())
                                            skillPoints += 1
                                            skillpointssTextView.text = skillPoints.toString()
                                            if(artsArray.contains(tempTextView.text.toString()+"."+(tempNumberView.text.toString().toInt() + 1).toString())){
                                                updateArray("Art", tempTextView.text.toString(),-1)
                                            }else if(languageArray.contains(tempTextView.text.toString()+"."+(tempNumberView.text.toString().toInt() + 1).toString())){
                                                updateArray("Lan", tempTextView.text.toString(),-1)
                                            }else if(scienceArray.contains(tempTextView.text.toString()+"."+(tempNumberView.text.toString().toInt() + 1).toString())){
                                                updateArray("Science", tempTextView.text.toString(),-1)
                                            }
                                            warningContentTextView2.text = ""
                                        }
                                    },
                                    false
                                )

                            }
                            newLayout.addView(dividerView)
                        }
                    }else if(i.contains("one") || i.contains("two")) {
                        //need to finish this
                        continue
                    }else {
                        var tempTextView: TextView = TextView(this)
                        var tempNumberView: TextView = TextView(this)
                        var addButton: Button = Button(this)
                        var subButton: Button = Button(this)
                        var containerLayout: LinearLayout = LinearLayout(this)
                        var dividerView = View(this)
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
                                warningContentTextView2.text = "You are out of points to spend."
                                } else if (tempNumberView.text.toString().toInt() == 75) {
                                    warningContentTextView2.text =
                                        "You cannot have a stat exceed 75 points."
                                } else {
                                    tempNumberView.text =
                                        (tempNumberView.text.toString().toInt() + 1).toString()
                                    saveStat(tempTextView.text.toString(),tempNumberView.text.toString().toInt())
                                    skillPoints -= 1
                                    skillpointssTextView.text = skillPoints.toString()
                                    warningContentTextView2.text = ""
                                }
                            },
                            {
                                val tempChar : Character = Character()
                                if (tempNumberView.text.toString().toInt() == tempChar.skillPicker(tempTextView.text.toString())||
                                    tempNumberView.text.toString().toInt() == 1) {
                                warningContentTextView2.text =
                                    "You can not subtract any more from that stat."
                                } else {
                                    tempNumberView.text =
                                        (tempNumberView.text.toString().toInt() - 1).toString()
                                    saveStat(tempTextView.text.toString(),tempNumberView.text.toString().toInt())
                                    skillPoints += 1
                                    skillpointssTextView.text = skillPoints.toString()
                                    warningContentTextView2.text = ""
                                }
                            },
                            true
                        )
                    }
                }
                skillLayout.addView(newLayout)

                val continueToStepFourButton : Button = Button(this)
                continueToStepFourButton.text = "Continue To Next Step"
                continueToStepFourButton.setOnClickListener(){
                    newCharacter.creditRating = creditSpinner.selectedItem.toString().toInt()
                    //load next step for personal points
                    //warningContentTextView2.text = temp
                    saveCharacter(newCharacter)

                    val intent = Intent(this@Create1920sPlayerStepThreeActivity, Create1920sPlayerStepFourActivity::class.java)
                    startActivity(intent)

                }
                skillLayout.addView(continueToStepFourButton)
            }
            //warningContentTextView2.text = artsArray.toString()
            saveCharacter(newCharacter)
        }

        backButton.setOnClickListener(){
            //need to delete the views from previous selected occupation
            //when you went ahead the first time
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
            //skillLayout.removeAllViews()
            val intent = Intent(this@Create1920sPlayerStepThreeActivity, Create1920sPlayerStepThreeActivity::class.java)
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
            MATCH_PARENT,
            MATCH_PARENT,
            25.0f
        )
        tempNumberView.layoutParams = LinearLayout.LayoutParams(
            MATCH_PARENT,
            MATCH_PARENT,
            25.0f
        )
        addButton.layoutParams = LinearLayout.LayoutParams(
            MATCH_PARENT,
            MATCH_PARENT,
            25.0f
        )
        subButton.layoutParams = LinearLayout.LayoutParams(
            MATCH_PARENT,
            MATCH_PARENT,
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

}