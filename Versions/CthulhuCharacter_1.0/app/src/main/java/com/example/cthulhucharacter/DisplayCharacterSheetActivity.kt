package com.example.cthulhucharacter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextClock
import android.widget.TextView
import androidx.activity.ComponentActivity
import kotlinx.coroutines.selects.select
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.OutputStream
import kotlin.random.Random

/**
 * DisplayCharacterSheetActivity Class
 * * This activity will display the selected character from the previous activity
 * * The display will be what shows up on a character sheet
 * ** along with the ability to "roll" a dice 1-100 to see if the result is less or more thann the selected skill
 */
class DisplayCharacterSheetActivity : ComponentActivity() {
    /**
     * View variables for this activity
     */
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

    var otherInvesticatorsNames : ArrayList<String> = arrayListOf()
    var otherInvesticatorsCharacters : ArrayList<String> = arrayListOf()

    var characterList : ArrayList<Character> = arrayListOf()

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
        val anthropologyTextView : TextView = findViewById(R.id.anthropologyTextView)
        val appraisetextView : TextView = findViewById(R.id.appraisetextView)
        val archaeologyTextView : TextView = findViewById(R.id.archaeologyTextView)
        val artAndCraftTextView : TextView = findViewById(R.id.artAndCraftTextView)
        val charmTextView : TextView = findViewById(R.id.charmTextView)
        val climbTextView : TextView = findViewById(R.id.climbTextView)
        val cthulhuMythosTextView : TextView = findViewById(R.id.cthulhuMythosTextView)
        val disguiseTextView : TextView = findViewById(R.id.disguiseTextView)
        val driveAutotextView : TextView = findViewById(R.id.driveAutotextView)
        val elcRepairTextView : TextView = findViewById(R.id.elcRepairTextView)
        val fastTalkTextView : TextView = findViewById(R.id.fastTalkTextView)
        val fightingBrawlTextView : TextView = findViewById(R.id.fightingBrawlTextView)
        val firearmsHandgunTextView : TextView = findViewById(R.id.firearmsHandgunTextView)
        val firearmsRifletextView : TextView = findViewById(R.id.firearmsRifletextView)
        val firstAidTextView : TextView = findViewById(R.id.firstAidTextView)
        val IntimidateTextView : TextView = findViewById(R.id.IntimidateTextView)
        val jumpTextView : TextView = findViewById(R.id.jumpTextView)
        val languageOtherTextView : TextView = findViewById(R.id.languageOtherTextView)
        val lawTextView : TextView = findViewById(R.id.lawTextView)
        val libraryUseTextView : TextView = findViewById(R.id.libraryUseTextView)
        val listenTextView : TextView = findViewById(R.id.listenTextView)
        val locksmithTextView : TextView = findViewById(R.id.locksmithTextView)
        val mechRepairTextView : TextView = findViewById(R.id.mechRepairTextView)
        val medicineTextView : TextView = findViewById(R.id.medicineTextView)
        val naturalWorldTextView : TextView = findViewById(R.id.naturalWorldTextView)
        val navigateTextView : TextView = findViewById(R.id.navigateTextView)
        val occultTextView : TextView = findViewById(R.id.occultTextView)
        val persuadeTextView : TextView = findViewById(R.id.persuadeTextView)
        val pilotTextView : TextView = findViewById(R.id.pilotTextView)
        val psychoanalysisTextView : TextView = findViewById(R.id.psychoanalysisTextView)
        val psychologyTextView8 : TextView = findViewById(R.id.psychologyTextView8)
        val rideTextView : TextView = findViewById(R.id.rideTextView)
        val scienceTextView : TextView = findViewById(R.id.scienceTextView)
        val sleightOfHandTextView : TextView = findViewById(R.id.sleightOfHandTextView)
        val spotHiddenTextView : TextView = findViewById(R.id.spotHiddenTextView)
        val stealthTextView : TextView = findViewById(R.id.stealthTextView)
        val survivalTextView : TextView = findViewById(R.id.survivalTextView)
        val swimTextView : TextView = findViewById(R.id.swimTextView)
        val throwTextView : TextView = findViewById(R.id.throwTextView)
        val trackTextView : TextView = findViewById(R.id.trackTextView)
        val eraTextView : TextView = findViewById(R.id.eraTextView)
        val myStoryTextView : TextView = findViewById(R.id.myStoryTextView)
        val personalDescriptionTextView : TextView = findViewById(R.id.personalDescriptionTextView)
        val traitsTextView : TextView = findViewById(R.id.traitsTextView)
        val ideologyAndBeliefsTextView : TextView = findViewById(R.id.ideologyAndBeliefsTextView)
        val injuriesAndScarsTextView : TextView = findViewById(R.id.injuriesAndScarsTextView)
        val significantPeopleTextView : TextView = findViewById(R.id.significantPeopleTextView)
        val phobiasAndManiastextView : TextView = findViewById(R.id.phobiasAndManiastextView)
        val meaningfulLocationsTextView : TextView = findViewById(R.id.meaningfulLocationsTextView)
        val arcaneTomesAndSpellsTextView : TextView = findViewById(R.id.arcaneTomesAndSpellsTextView)
        val teasuredPossessionsTextView : TextView = findViewById(R.id.teasuredPossessionsTextView)
        val encountersWithStrangeEntitiesTextView : TextView = findViewById(R.id.encountersWithStrangeEntitiesTextView)
        val dodgeTextView : TextView = findViewById(R.id.dodgeTextView)
        val moveTextView : TextView = findViewById(R.id.moveTextView)
        val damageBonusTextView : TextView = findViewById(R.id.damageBonusTextView)
        val buildTextView : TextView = findViewById(R.id.buildTextView)
        val accountingButton : Button = findViewById(R.id.accountingButton)
        val anthropologyButton : Button = findViewById(R.id.anthropologyButton)
        val appraiseButton : Button = findViewById(R.id.appraiseButton)
        val archaeologyButton : Button = findViewById(R.id.archaeologyButton)
        val artAndCraftButton : Button = findViewById(R.id.artAndCraftButton)
        val charmButton : Button = findViewById(R.id.charmButton)
        val climbButton : Button = findViewById(R.id.climbButton)
        val cthulhuMythosButton : Button = findViewById(R.id.cthulhuMythosButton)
        val disguiseButton : Button = findViewById(R.id.disguiseButton)
        val driveAutoButton : Button = findViewById(R.id.driveAutoButton)
        val elcRepairButton : Button = findViewById(R.id.elcRepairButton)
        val fastTalkButton : Button = findViewById(R.id.fastTalkButton)
        val fightingBrawlButton : Button = findViewById(R.id.fightingBrawlButton)
        val firearmsHandgunButton : Button = findViewById(R.id.firearmsHandgunButton)
        val firearmsRiflebutton : Button = findViewById(R.id.firearmsRiflebutton)
        val firstAidButton : Button = findViewById(R.id.firstAidButton)
        val IntimidateButton : Button = findViewById(R.id.IntimidateButton)
        val jumpButton : Button = findViewById(R.id.jumpButton)
        val languageOtherButton : Button = findViewById(R.id.languageOtherButton)
        val lawButton : Button = findViewById(R.id.lawButton)
        val libraryUseButton : Button = findViewById(R.id.libraryUseButton)
        val listenButton : Button = findViewById(R.id.listenButton)
        val locksmithButton : Button = findViewById(R.id.locksmithButton)
        val mechRepairButton : Button = findViewById(R.id.mechRepairButton)
        val medicineButton : Button = findViewById(R.id.medicineButton)
        val naturalWorldButton : Button = findViewById(R.id.naturalWorldButton)
        val navigateButton : Button = findViewById(R.id.navigateButton)
        val occultButton : Button = findViewById(R.id.occultButton)
        val persuadeButton : Button = findViewById(R.id.persuadeButton)
        val pilotButton : Button = findViewById(R.id.pilotButton)
        val psychoanalysisButton : Button = findViewById(R.id.psychoanalysisButton)
        val psychologyButton : Button = findViewById(R.id.psychologyButton)
        val rideButton : Button = findViewById(R.id.rideButton)
        val scienceButton : Button = findViewById(R.id.scienceButton)
        val sleightOfHandButton : Button = findViewById(R.id.sleightOfHandButton)
        val spotHiddenButton : Button = findViewById(R.id.spotHiddenButton)
        val stealthButton : Button = findViewById(R.id.stealthButton)
        val survivalButton : Button = findViewById(R.id.survivalButton)
        val swimButton : Button = findViewById(R.id.swimButton)
        val throwButton : Button = findViewById(R.id.throwButton)
        val trackButton : Button = findViewById(R.id.trackButton)
        val gearLayout : LinearLayout = findViewById(R.id.gearLayout)
        val attackNameLayout : LinearLayout = findViewById(R.id.attackNameLayout)
        val damageLayout : LinearLayout = findViewById(R.id.damageLayout)
        val numberOfAttacksLayout : LinearLayout = findViewById(R.id.numberOfAttacksLayout)
        val rangesLayout : LinearLayout = findViewById(R.id.rangesLayout)
        val amoLayout : LinearLayout = findViewById(R.id.amoLayout)
        val malfLayout : LinearLayout = findViewById(R.id.malfLayout)
        val rollLayout : LinearLayout = findViewById(R.id.rollLayout)
        val otherInvestigatorsLinearLayout : LinearLayout = findViewById(R.id.otherInvestigatorsLinearLayout)
        val cashTextView : TextView = findViewById(R.id.cashTextView)
        val spendingLevelTextView : TextView = findViewById(R.id.spendingLevelTextView)
        val assetsTextView : TextView = findViewById(R.id.assetsTextView)

        val editCharaterButton : Button = findViewById(R.id.editCharaterButton)

        /**
         * Change this to allow the edit button
         */
        editCharaterButton.visibility = View.GONE

        createCharacterList()
        var selectedCharacter : Character = Character()

        for(i in characterList.indices){
            if(characterList[i].name.toString() == intent.getStringExtra("name").toString()){
                selectedCharacter = characterList[i]
            }
        }
        loadArrays(selectedCharacter)

        jsonTextView.text = "Character: \n" + selectedCharacter.createJson()
        nameTextView.text = selectedCharacter.name
        occupationTextView.text = selectedCharacter.occupation
        residenceTextView.text = selectedCharacter.residence
        ageTextView.text = selectedCharacter.age.toString()
        eraTextView.text = selectedCharacter.era.toString()
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
        anthropologyTextView.text = selectedCharacter.anthropology.toString()
        appraisetextView.text = selectedCharacter.appraise.toString()
        archaeologyTextView.text = selectedCharacter.archaeology.toString()
        artAndCraftTextView.text = selectedCharacter.artAndCraft.toString()
        charmTextView.text = selectedCharacter.charm.toString()
        climbTextView.text = selectedCharacter.climb.toString()
        cthulhuMythosTextView.text= selectedCharacter.cthulhuMythos.toString()
        disguiseTextView.text = selectedCharacter.disguise.toString()
        driveAutotextView.text = selectedCharacter.driveAuto.toString()
        elcRepairTextView.text = selectedCharacter.elcRepair.toString()
        fastTalkTextView.text = selectedCharacter.fastTalk.toString()
        fightingBrawlTextView.text = selectedCharacter.fightingBrawl.toString()
        firearmsHandgunTextView.text = selectedCharacter.firearmsHandgun.toString()
        firearmsRifletextView.text = selectedCharacter.firearmsRifle.toString()
        firstAidTextView.text = selectedCharacter.firstAid.toString()
        IntimidateTextView.text = selectedCharacter.intimidate.toString()
        jumpTextView.text = selectedCharacter.jump.toString()
        languageOtherTextView.text = selectedCharacter.languageOther.toString()
        lawTextView.text = selectedCharacter.law.toString()
        libraryUseTextView.text = selectedCharacter.libraryUse.toString()
        listenTextView.text = selectedCharacter.listen.toString()
        locksmithTextView.text = selectedCharacter.locksmith.toString()
        mechRepairTextView.text = selectedCharacter.mechRepair.toString()
        medicineTextView.text = selectedCharacter.medicine.toString()
        naturalWorldTextView.text = selectedCharacter.naturalWorld.toString()
        navigateTextView.text = selectedCharacter.navigate.toString()
        occultTextView.text = selectedCharacter.occult.toString()
        persuadeTextView.text = selectedCharacter.persuade.toString()
        pilotTextView.text = selectedCharacter.pilot.toString()
        psychoanalysisTextView.text = selectedCharacter.psychoanalysis.toString()
        psychologyTextView8.text = selectedCharacter.psychology.toString()
        rideTextView.text = selectedCharacter.ride.toString()
        scienceTextView.text = selectedCharacter.science.toString()
        sleightOfHandTextView.text = selectedCharacter.sleightOfHand.toString()
        spotHiddenTextView.text = selectedCharacter.spotHidden.toString()
        stealthTextView.text = selectedCharacter.stealth.toString()
        survivalTextView.text = selectedCharacter.survival.toString()
        swimTextView.text = selectedCharacter.swim.toString()
        throwTextView.text = selectedCharacter.thro.toString()
        trackTextView.text = selectedCharacter.track.toString()

        dodgeTextView.text = selectedCharacter.dodge.toString()
        moveTextView.text = selectedCharacter.move.toString()
        damageBonusTextView.text = selectedCharacter.damageBonus
        buildTextView.text = selectedCharacter.build.toString()

        myStoryTextView.text = selectedCharacter.myStory
        personalDescriptionTextView.text = selectedCharacter.personalDescription
        traitsTextView.text = selectedCharacter.traits
        ideologyAndBeliefsTextView.text = selectedCharacter.ideologyAndBeliefs
        injuriesAndScarsTextView.text = selectedCharacter.injuriesAndScars
        significantPeopleTextView.text = selectedCharacter.significantPeople
        phobiasAndManiastextView.text = selectedCharacter.phobiasAndManias
        meaningfulLocationsTextView.text = selectedCharacter.meaningfulLocations
        arcaneTomesAndSpellsTextView.text = selectedCharacter.arcaneTomesAndSpells
        teasuredPossessionsTextView.text = selectedCharacter.treasuredPossessions
        encountersWithStrangeEntitiesTextView.text = selectedCharacter.encountersWithStrangeEntities

        cashTextView.text = selectedCharacter.cash.toString()
        spendingLevelTextView.text = selectedCharacter.spendingLevel.toString()
        assetsTextView.text = selectedCharacter.assets

        for(i in gearArray){
            if(i == "Brawl"){
                continue
            }
            val newTextView : TextView = TextView(this)
            newTextView.text = i
            gearLayout.addView(newTextView)
        }
        for( i in playerAttackName){
            val newTextView2 : TextView = TextView(this)
            newTextView2.text = i
            attackNameLayout.addView(newTextView2)
        }
        for(i in playerDamage){
            val newTextView3 : TextView = TextView(this)
            newTextView3.text = i
            damageLayout.addView(newTextView3)
        }
        for(i in playerNumOfA){
            val newTextView4 : TextView = TextView(this)
            newTextView4.text = i
            numberOfAttacksLayout.addView(newTextView4)
        }
        for(i in playerRange){
            val newTextView5 : TextView = TextView(this)
            newTextView5.text = i
            rangesLayout.addView(newTextView5)
        }
        for(i in playerAmmo){
            val newTextView6 : TextView = TextView(this)
            newTextView6.text = i
            amoLayout.addView(newTextView6)
        }
        for(i in playerMalf){
            val newTextView7 : TextView = TextView(this)
            newTextView7.text = i
            malfLayout.addView(newTextView7)
        }
        for(i in playerAttackName.indices){
            val newButton : Button = Button(this)
            newButton.text = "Roll"
            newButton.setOnClickListener(){
                var damage = playerDamage[i]
                var damageSplit = damage.split('+')
                val d = damageSplit[0].split('D')
                val numOfDice : Int = d[0].toInt()
                val sizeOfDice : Int = d[1].toInt()
                var dbNumOfDice : Int = 0
                var dbSzeOfDice : Int = 0
                var offset : Int = 0
                if(damageSplit[1] != null && damageSplit[1] == "DB"){
                    if(selectedCharacter.damageBonus != "None"){
                        if(selectedCharacter.damageBonus == "-1" || selectedCharacter.damageBonus == "-2"){
                            offset = selectedCharacter.damageBonus.toInt()
                        }else {
                            var scDb = selectedCharacter.damageBonus.split('D')
                            dbNumOfDice = scDb[0].toInt()
                            dbSzeOfDice = scDb[1].toInt()
                        }
                    }
                }
                var finDamage : Int = 0
                for(i in 1..numOfDice){
                    finDamage += Random.nextInt(1, sizeOfDice)
                }
                for(i in 1..dbNumOfDice){
                    finDamage += Random.nextInt(1, dbSzeOfDice)
                }
                finDamage -= offset
                rollResultTextView.text = "Damage = " +  finDamage.toString()
            }
            rollLayout.addView(newButton)
        }

        for(i in otherInvesticatorsNames.indices){
            if(otherInvesticatorsNames[i] != "" && otherInvesticatorsCharacters[i] != "") {
                val horLinLayout: LinearLayout = LinearLayout(this)
                horLinLayout.orientation = LinearLayout.HORIZONTAL
                val nameText: TextView = TextView(this)
                val charText: TextView = TextView(this)
                val viewBreak: View = View(this)

                horLinLayout.orientation = LinearLayout.HORIZONTAL
                viewBreak.layoutParams = LinearLayout.LayoutParams(
                    2,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0.002f
                )
                viewBreak.setBackgroundResource(R.color.purple_500)

                nameText.layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    0.50f
                )
                charText.layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    0.50f
                )

                //re arrange the views
                nameText.text = otherInvesticatorsNames[i]
                charText.text = otherInvesticatorsCharacters[i]
                horLinLayout.addView(nameText)
                horLinLayout.addView(viewBreak)
                horLinLayout.addView(charText)
                otherInvestigatorsLinearLayout.addView(horLinLayout)
            }
        }

        /**
         * Buttons
         */

        /**
         * BackToCharSelectButton Button
         * * Will load the previous activity moving the use back a step
         */
        BackToCharSelectButton.setOnClickListener(){
            val intent = Intent(this@DisplayCharacterSheetActivity, DisplayCharactersActivity::class.java)
            startActivity(intent)
        }
        /**
         * rollSTRButton Button
         * * Will generate a random number from 1-100 and compare it to the Strength stat
         * ** and will display the result being one of exterem success, hard success, success, or failure
         */
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

        accountingButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.accounting){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.accounting / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.accounting / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        anthropologyButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.anthropology){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.anthropology / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.anthropology / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        appraiseButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.appraise){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.appraise / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.appraise / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        archaeologyButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.archaeology){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.archaeology / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.archaeology / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        artAndCraftButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.artAndCraft){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.artAndCraft / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.artAndCraft / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        charmButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.charm){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.charm / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.charm / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        climbButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.climb){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.climb / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.climb / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        cthulhuMythosButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.cthulhuMythos){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.cthulhuMythos / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.cthulhuMythos / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        disguiseButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.disguise){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.disguise / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.disguise / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        driveAutoButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.driveAuto){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.driveAuto / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.driveAuto / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        elcRepairButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.elcRepair){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.elcRepair / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.elcRepair / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        fastTalkButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.fastTalk){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.fastTalk / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.fastTalk / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        fightingBrawlButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.fightingBrawl){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.fightingBrawl / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.fightingBrawl / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        firearmsHandgunButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.firearmsHandgun){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.firearmsHandgun / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.firearmsHandgun / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        firearmsRiflebutton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.firearmsRifle){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.firearmsRifle / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.firearmsRifle / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        firstAidButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.firstAid){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.firstAid / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.firstAid / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        IntimidateButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.intimidate){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.intimidate / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.intimidate / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        jumpButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.intimidate){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.intimidate / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.intimidate / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        languageOtherButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.languageOther){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.languageOther / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.languageOther / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        lawButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.law){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.law / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.law / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        libraryUseButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.libraryUse){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.libraryUse / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.libraryUse / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        listenButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.listen){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.listen / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.listen / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        locksmithButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.locksmith){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.locksmith / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.locksmith / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        mechRepairButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.mechRepair){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.mechRepair / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.mechRepair / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        medicineButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.medicine){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.medicine / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.medicine / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        naturalWorldButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.naturalWorld){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.naturalWorld / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.naturalWorld / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        navigateButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.navigate){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.navigate / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.navigate / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        occultButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.occult){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.occult / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.occult / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        persuadeButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.persuade){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.persuade / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.persuade / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        pilotButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.pilot){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.pilot / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.pilot / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        psychoanalysisButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.psychoanalysis){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.psychoanalysis / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.psychoanalysis / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        psychologyButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.psychology){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.psychology / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.psychology / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        rideButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.ride){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.ride / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.ride / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        scienceButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.science){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.science / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.science / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        sleightOfHandButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.sleightOfHand){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.sleightOfHand / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.sleightOfHand / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        spotHiddenButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.spotHidden){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.spotHidden / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.spotHidden / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        stealthButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.stealth){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.stealth / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.stealth / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        survivalButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.survival){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.survival / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.survival / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        swimButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.swim){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.swim / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.swim / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        throwButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.thro){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.thro / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.thro / 2){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Hard Success"
            }else {
                rollResultTextView.text = "Roll Result: " + num.toString() + " Success"
            }
        }
        trackButton.setOnClickListener(){
            var num : Int = Random.nextInt(1, 100)
            if(num >= selectedCharacter.track){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Fail"
            }else if(num < selectedCharacter.track / 5){
                rollResultTextView.text = "Roll Result: " + num.toString() + " Exterme Success"
            }else if(num < selectedCharacter.track / 2){
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
    private fun loadArrays(character : Character){
        var tempCharacter : Character = Character()
        try {
            tempCharacter.loadCharacter(character.createJson())
        }
        catch(e: IOException){
            tempCharacter.name = "Did not find file to open"
        }

        var holdNames : List<String> = listOf()
        holdNames = tempCharacter.playerNames.split(';')
        for(i in holdNames){
            otherInvesticatorsNames.add(i)
        }
        var holdCharacters : List<String> = listOf()
        holdCharacters = tempCharacter.characterNames.split(';')
        for(i in holdCharacters){
            otherInvesticatorsCharacters.add(i)
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
    }
}
