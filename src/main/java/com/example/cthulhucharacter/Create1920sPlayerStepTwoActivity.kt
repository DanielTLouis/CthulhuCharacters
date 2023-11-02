package com.example.cthulhucharacter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.compose.runtime.traceEventEnd
import com.google.gson.Gson
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.time.temporal.Temporal

/**
 * Create1920sPlayerStepTwoActivity Class
 * * Will set the base stats for the character and will allow the user to re-roll the stats if they desire
 * ** Base stats that are set: strength, constitution, dexterity, intelligent, size, power, appearance,
 * *** education, health, sanity, luck   
 */
//generate base stats
class Create1920sPlayerStepTwoActivity : ComponentActivity() {
    var artsArray : ArrayList<String> = arrayListOf()
    var languageArray : ArrayList<String> = arrayListOf()
    var scienceArray : ArrayList<String> = arrayListOf()

    var stdArraystg : ArrayList<String> = arrayListOf( "-","80","70","60","60","50","50","50","40")
    var stdArraycon : ArrayList<String> = arrayListOf( "-","80","70","60","60","50","50","50","40")
    var stdArraydex : ArrayList<String> = arrayListOf( "-","80","70","60","60","50","50","50","40")
    var stdArrayint : ArrayList<String> = arrayListOf( "-","80","70","60","60","50","50","50","40")
    var stdArraysiz : ArrayList<String> = arrayListOf( "-","80","70","60","60","50","50","50","40")
    var stdArraypow : ArrayList<String> = arrayListOf( "-","80","70","60","60","50","50","50","40")
    var stdArrayapp : ArrayList<String> = arrayListOf( "-","80","70","60","60","50","50","50","40")
    var stdArrayedu : ArrayList<String> = arrayListOf( "-","80","70","60","60","50","50","50","40")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create1920splayersteptwo)

        var newCharacter : Character = loadCharacter()

        val stepTwoPartTwoRollLayout : View = findViewById(R.id.stepTwoPartTwoRollLayout)
        val stepTwoPartTwoStandardArrayLayout : View = findViewById(R.id.stepTwoPartTwoStandardArrayLayout)
        val startAct2LinearLayout : View = findViewById(R.id.startAct2LinearLayout)
        startAct2LinearLayout.visibility = View.GONE
        stepTwoPartTwoRollLayout.visibility = View.VISIBLE
        stepTwoPartTwoStandardArrayLayout.visibility = View.GONE

        val stndArrayVsRollspinner : Spinner = findViewById(R.id.stndArrayVsRollspinner)
        if(stndArrayVsRollspinner != null){
            val adapter = ArrayAdapter(
                this,
                //add stanard array later "Standard Array"
                android.R.layout.simple_spinner_item, arrayListOf<String>("Roll for Stats","Standard Array")
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

        val stgSpinner : Spinner = findViewById(R.id.stgSpinner)
        val conSpinner : Spinner = findViewById(R.id.conSpinner)
        val dexSpinner : Spinner = findViewById(R.id.dexSpinner)
        val intSpinner : Spinner = findViewById(R.id.intSpinner)
        val sizSpinner : Spinner = findViewById(R.id.sizSpinner)
        val powSpinner : Spinner = findViewById(R.id.powSpinner)
        val appSpinner : Spinner = findViewById(R.id.appSpinner)
        val eduSpinner : Spinner = findViewById(R.id.eduSpinner)

        val continueButton4 : Button = findViewById(R.id.continueButton4)
        val testing : TextView = findViewById(R.id.testing)


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
        rollStats()

        val printingChracterTestTextView3 : TextView = findViewById(R.id.printingChracterTestTextView3)
        var firstSelectSize : Boolean = false
        var firstSelectStr: Boolean = false
        var firstSelectCon : Boolean = false
        var firstSelectDex : Boolean = false
        var firstSelectInt : Boolean = false
        var firstSelectPow : Boolean = false
        var firstSelectEdu : Boolean = false
        var firstSelectApp  : Boolean = false
        if(stgSpinner!= null){
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, stdArraystg
            )
            stgSpinner.adapter = adapter
        }
        stgSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            var tempStirng : String = stdArraystg[0]
            override fun onNothingSelected(parent: AdapterView<*>?) {
                var tempStringSup : String = "Standard Array: "
                for(i in stdArraystg){
                    tempStringSup += i + " "
                }
                printingChracterTestTextView3.text = tempStringSup
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(tempStirng == "-" && firstSelectStr == true){
                    tempStirng= stgSpinner.selectedItem.toString()
                    stdArrayint.remove(tempStirng)
                    stdArraycon.remove(tempStirng)
                    stdArraydex.remove(tempStirng)
                    stdArraypow.remove(tempStirng)
                    stdArrayapp.remove(tempStirng)
                    stdArrayedu.remove(tempStirng)
                    stdArraysiz.remove(tempStirng)
                }
                else if( firstSelectStr == true){
                    stdArrayint.add(tempStirng)
                    stdArraycon.add(tempStirng)
                    stdArraydex.add(tempStirng)
                    stdArraypow.add(tempStirng)
                    stdArrayapp.add(tempStirng)
                    stdArrayedu.add(tempStirng)
                    stdArraysiz.add(tempStirng)
                    tempStirng= stgSpinner.selectedItem.toString()
                    if(tempStirng != "-") {
                        stdArrayint.remove(tempStirng)
                        stdArraycon.remove(tempStirng)
                        stdArraydex.remove(tempStirng)
                        stdArraypow.remove(tempStirng)
                        stdArrayapp.remove(tempStirng)
                        stdArrayedu.remove(tempStirng)
                        stdArraysiz.remove(tempStirng)
                    }
                }else{
                    firstSelectStr = true
                }
                var tempStringSup : String = ""
                for(i in stdArraystg){
                    tempStringSup += i + " "
                }
                printingChracterTestTextView3.text = tempStringSup
            }
        }
        if(conSpinner!= null){
            val adapter = ArrayAdapter(
                this,
                //add stanard array later "Standard Array"
                android.R.layout.simple_spinner_item, stdArraycon
            )
            conSpinner.adapter = adapter
        }
        conSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            var tempStirng : String = stdArraycon[0]
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(tempStirng == "-" && firstSelectCon == true){
                    tempStirng= conSpinner.selectedItem.toString()
                    stdArraystg.remove(tempStirng)
                    stdArrayint.remove(tempStirng)
                    stdArraydex.remove(tempStirng)
                    stdArraypow.remove(tempStirng)
                    stdArrayapp.remove(tempStirng)
                    stdArrayedu.remove(tempStirng)
                    stdArraysiz.remove(tempStirng)
                }
                else if(firstSelectCon == true){
                    stdArraystg.add(tempStirng)
                    stdArrayint.add(tempStirng)
                    stdArraydex.add(tempStirng)
                    stdArraypow.add(tempStirng)
                    stdArrayapp.add(tempStirng)
                    stdArrayedu.add(tempStirng)
                    stdArraysiz.add(tempStirng)
                    tempStirng= conSpinner.selectedItem.toString()
                    if(tempStirng != "-"){
                        stdArraystg.remove(tempStirng)
                        stdArrayint.remove(tempStirng)
                        stdArraydex.remove(tempStirng)
                        stdArraypow.remove(tempStirng)
                        stdArrayapp.remove(tempStirng)
                        stdArrayedu.remove(tempStirng)
                        stdArraysiz.remove(tempStirng)
                    }
                }
                else{
                    firstSelectCon = true
                }
            }
        }
        if(dexSpinner!= null){
            val adapter = ArrayAdapter(
                this,
                //add stanard array later "Standard Array"
                android.R.layout.simple_spinner_item, stdArraydex
            )
            dexSpinner.adapter = adapter
        }
        dexSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            var tempStirng : String = stdArraydex[0]
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(tempStirng == "" && firstSelectDex == true){
                    tempStirng= dexSpinner.selectedItem.toString()
                    stdArraystg.remove(tempStirng)
                    stdArrayint.remove(tempStirng)
                    stdArraycon.remove(tempStirng)
                    stdArraypow.remove(tempStirng)
                    stdArrayapp.remove(tempStirng)
                    stdArrayedu.remove(tempStirng)
                    stdArraysiz.remove(tempStirng)
                }
                else if(firstSelectDex == true){
                    stdArraystg.add(tempStirng)
                    stdArrayint.add(tempStirng)
                    stdArraycon.add(tempStirng)
                    stdArraypow.add(tempStirng)
                    stdArrayapp.add(tempStirng)
                    stdArrayedu.add(tempStirng)
                    stdArraysiz.add(tempStirng)
                    tempStirng= dexSpinner.selectedItem.toString()
                    if(tempStirng != "-"){
                        stdArraystg.remove(tempStirng)
                        stdArrayint.remove(tempStirng)
                        stdArraycon.remove(tempStirng)
                        stdArraypow.remove(tempStirng)
                        stdArrayapp.remove(tempStirng)
                        stdArrayedu.remove(tempStirng)
                        stdArraysiz.remove(tempStirng)
                    }
                }else{
                    firstSelectDex = true
                }
            }
        }
        if(intSpinner!= null){
            val adapter = ArrayAdapter(
                this,
                //add stanard array later "Standard Array"
                android.R.layout.simple_spinner_item, stdArrayint
            )
            intSpinner.adapter = adapter
        }
        intSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            var tempStirng : String = stdArrayint[0]
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(tempStirng == "-" && firstSelectInt == true){
                    tempStirng= intSpinner.selectedItem.toString()
                    stdArraystg.remove(tempStirng)
                    stdArraycon.remove(tempStirng)
                    stdArraydex.remove(tempStirng)
                    stdArraypow.remove(tempStirng)
                    stdArrayapp.remove(tempStirng)
                    stdArrayedu.remove(tempStirng)
                    stdArraysiz.remove(tempStirng)
                }
                else if(firstSelectInt == true){
                    stdArraystg.add(tempStirng)
                    stdArraycon.add(tempStirng)
                    stdArraydex.add(tempStirng)
                    stdArraypow.add(tempStirng)
                    stdArrayapp.add(tempStirng)
                    stdArrayedu.add(tempStirng)
                    stdArraysiz.add(tempStirng)
                    tempStirng= intSpinner.selectedItem.toString()
                    if(tempStirng != "-"){
                        stdArraystg.remove(tempStirng)
                        stdArraycon.remove(tempStirng)
                        stdArraydex.remove(tempStirng)
                        stdArraypow.remove(tempStirng)
                        stdArrayapp.remove(tempStirng)
                        stdArrayedu.remove(tempStirng)
                        stdArraysiz.remove(tempStirng)
                    }
                }else{
                    firstSelectInt = true
                }
            }
        }
        if(sizSpinner!= null){
            val adapter = ArrayAdapter(
                this,
                //add stanard array later "Standard Array"
                android.R.layout.simple_spinner_item, stdArraysiz
            )
            sizSpinner.adapter = adapter
        }
        sizSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            var tempStirng : String = stdArraysiz[0]
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(tempStirng == "-" && firstSelectSize == true){
                    tempStirng= sizSpinner.selectedItem.toString()
                    stdArraystg.remove(tempStirng)
                    stdArrayint.remove(tempStirng)
                    stdArraycon.remove(tempStirng)
                    stdArraydex.remove(tempStirng)
                    stdArraypow.remove(tempStirng)
                    stdArrayapp.remove(tempStirng)
                    stdArrayedu.remove(tempStirng)
                }
                else if(firstSelectSize == true){
                    stdArraystg.add(tempStirng)
                    stdArrayint.add(tempStirng)
                    stdArraycon.add(tempStirng)
                    stdArraydex.add(tempStirng)
                    stdArraypow.add(tempStirng)
                    stdArrayapp.add(tempStirng)
                    stdArrayedu.add(tempStirng)
                    tempStirng= sizSpinner.selectedItem.toString()
                    if(tempStirng != "-"){
                        stdArraystg.remove(tempStirng)
                        stdArrayint.remove(tempStirng)
                        stdArraycon.remove(tempStirng)
                        stdArraydex.remove(tempStirng)
                        stdArraypow.remove(tempStirng)
                        stdArrayapp.remove(tempStirng)
                        stdArrayedu.remove(tempStirng)
                    }
                }else{
                    firstSelectSize = true
                }
            }
        }
        if(powSpinner!= null){
            val adapter = ArrayAdapter(
                this,
                //add stanard array later "Standard Array"
                android.R.layout.simple_spinner_item, stdArraypow
            )
            powSpinner.adapter = adapter
        }
        powSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            var tempStirng : String = stdArraypow[0]
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(tempStirng == "-" && firstSelectPow == true){
                    tempStirng= powSpinner.selectedItem.toString()
                    stdArraystg.remove(tempStirng)
                    stdArrayint.remove(tempStirng)
                    stdArraycon.remove(tempStirng)
                    stdArraydex.remove(tempStirng)
                    stdArraysiz.remove(tempStirng)
                    stdArrayapp.remove(tempStirng)
                    stdArrayedu.remove(tempStirng)
                }
                else if(firstSelectPow==true){
                    stdArraystg.add(tempStirng)
                    stdArrayint.add(tempStirng)
                    stdArraycon.add(tempStirng)
                    stdArraydex.add(tempStirng)
                    stdArraysiz.add(tempStirng)
                    stdArrayapp.add(tempStirng)
                    stdArrayedu.add(tempStirng)
                    tempStirng= powSpinner.selectedItem.toString()
                    if(tempStirng != "-"){
                        stdArraystg.remove(tempStirng)
                        stdArrayint.remove(tempStirng)
                        stdArraycon.remove(tempStirng)
                        stdArraydex.remove(tempStirng)
                        stdArraysiz.remove(tempStirng)
                        stdArrayapp.remove(tempStirng)
                        stdArrayedu.remove(tempStirng)
                    }
                }else{
                    firstSelectPow = true
                }
            }
        }
        if(appSpinner!= null){
            val adapter = ArrayAdapter(
                this,
                //add stanard array later "Standard Array"
                android.R.layout.simple_spinner_item, stdArrayapp
            )
            appSpinner.adapter = adapter
        }
        appSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            var tempStirng : String = stdArrayapp[0]
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(tempStirng == "-" && firstSelectApp == true){
                    tempStirng= appSpinner.selectedItem.toString()
                    stdArraystg.remove(tempStirng)
                    stdArrayint.remove(tempStirng)
                    stdArraycon.remove(tempStirng)
                    stdArraydex.remove(tempStirng)
                    stdArraypow.remove(tempStirng)
                    stdArraysiz.remove(tempStirng)
                    stdArrayedu.remove(tempStirng)
                }
                else if(firstSelectApp == true){
                    stdArraystg.add(tempStirng)
                    stdArrayint.add(tempStirng)
                    stdArraycon.add(tempStirng)
                    stdArraydex.add(tempStirng)
                    stdArraypow.add(tempStirng)
                    stdArraysiz.add(tempStirng)
                    stdArrayedu.add(tempStirng)
                    tempStirng= appSpinner.selectedItem.toString()
                    if(tempStirng != "-"){
                        stdArraystg.remove(tempStirng)
                        stdArrayint.remove(tempStirng)
                        stdArraycon.remove(tempStirng)
                        stdArraydex.remove(tempStirng)
                        stdArraypow.remove(tempStirng)
                        stdArraysiz.remove(tempStirng)
                        stdArrayedu.remove(tempStirng)
                    }
                }else{
                    firstSelectApp = true
                }
            }
        }
        if(eduSpinner!= null){
            val adapter = ArrayAdapter(
                this,
                //add stanard array later "Standard Array"
                android.R.layout.simple_spinner_item, stdArrayedu
            )
            eduSpinner.adapter = adapter
        }
        eduSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            var tempStirng : String = stdArrayedu[0]
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(tempStirng == "-" && firstSelectEdu == true){
                    tempStirng= eduSpinner.selectedItem.toString()
                    stdArraystg.remove(tempStirng)
                    stdArrayint.remove(tempStirng)
                    stdArraycon.remove(tempStirng)
                    stdArraydex.remove(tempStirng)
                    stdArraypow.remove(tempStirng)
                    stdArraysiz.remove(tempStirng)
                    stdArrayapp.remove(tempStirng)
                }
                else if(firstSelectEdu){
                    stdArraystg.add(tempStirng)
                    stdArrayint.add(tempStirng)
                    stdArraycon.add(tempStirng)
                    stdArraydex.add(tempStirng)
                    stdArraypow.add(tempStirng)
                    stdArraysiz.add(tempStirng)
                    stdArrayapp.add(tempStirng)
                    tempStirng= eduSpinner.selectedItem.toString()
                    if(tempStirng != "-"){
                        stdArraystg.remove(tempStirng)
                        stdArrayint.remove(tempStirng)
                        stdArraycon.remove(tempStirng)
                        stdArraydex.remove(tempStirng)
                        stdArraypow.remove(tempStirng)
                        stdArraysiz.remove(tempStirng)
                        stdArrayapp.remove(tempStirng)
                    }
                }else{
                    firstSelectEdu = true
                }
            }
        }

        /**
         * Buttons
         */
        continueButton4.setOnClickListener(){
            if(
                stgSpinner.selectedItem.toString() != "-" &&
                conSpinner.selectedItem.toString() != "-" &&
                dexSpinner.selectedItem.toString() != "-" &&
                intSpinner.selectedItem.toString() != "-" &&
                sizSpinner.selectedItem.toString() != "-" &&
                powSpinner.selectedItem.toString() != "-" &&
                appSpinner.selectedItem.toString() != "-" &&
                eduSpinner.selectedItem.toString() != "-"
            ) {
                //newCharacter.strength = stgSpinner.selectedItem.toString().toInt()
                /*newCharacter.constitution = conSpinner.selectedItem.toString().toInt()
                newCharacter.dexterity = dexSpinner.selectedItem.toString().toInt()
                newCharacter.intelligence = intSpinner.selectedItem.toString().toInt()
                newCharacter.size = sizSpinner.selectedItem.toString().toInt()
                newCharacter.power = powSpinner.selectedItem.toString().toInt()
                newCharacter.appearance = appSpinner.selectedItem.toString().toInt()
                newCharacter.education = eduSpinner.selectedItem.toString().toInt()

                newCharacter.hitPoints = ((constitutionStatsTextView.text.toString().toInt() + sizeStatsTextView.text.toString().toInt()) / 10).toString()
                newCharacter.maxHitPoints = newCharacter.hitPoints
                newCharacter.magicPoints = (powerStatsTextView.text.toString().toInt() / 5).toString()
                newCharacter.maxMagicPoints = newCharacter.magicPoints
                newCharacter.luck = (((1..6).random() + (1..6).random() + (1..6).random())*5).toString()
                newCharacter.startingLuck = newChracter.luck
                newCharacter.sanity =  powerStatsTextView.text.toString()
                newCharacter.startingSanity = newCharacter.sanity


                newCharacter.dodge = dexSpinner.selectedItem.toString().toInt() / 2
                when(stgSpinner.selectedItem.toString().toInt() + sizSpinner.selectedItem.toString().toInt()){
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

                newCharacter.move = moveStat(dexSpinner.selectedItem.toString().toInt(), stgSpinner.selectedItem.toString().toInt(), sizSpinner.selectedItem.toString().toInt())
                */
                testing.text = "We got through"+
                        stgSpinner.selectedItem.toString()  + " " +
                        conSpinner.selectedItem.toString() + " " +
                        dexSpinner.selectedItem.toString() + " " +
                        intSpinner.selectedItem.toString() + " " +
                        sizSpinner.selectedItem.toString() + " " +
                        powSpinner.selectedItem.toString() + " " +
                        appSpinner.selectedItem.toString() + " " +
                        eduSpinner.selectedItem.toString() + " "
                //saveCharacter(newCharacter)

                //go to next step
                //val intent = Intent( this@Create1920sPlayerStepTwoActivity, Create1920sPlayerStepThreeActivity::class.java)
                //startActivity(intent)
                //stepTwoPartTwoRollLayout.visibility = View.GONE
            }else{
                testing.text = "Change this to a warning to fill out all selections " +
                        stgSpinner.selectedItem.toString()  + " " +
                        conSpinner.selectedItem.toString() + " " +
                        dexSpinner.selectedItem.toString() + " " +
                        intSpinner.selectedItem.toString() + " " +
                        sizSpinner.selectedItem.toString() + " " +
                        powSpinner.selectedItem.toString() + " " +
                        appSpinner.selectedItem.toString() + " " +
                        eduSpinner.selectedItem.toString() + " "
            }
        }

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

        val backToStepTwoButton2 : Button = findViewById(R.id.backToStepTwoButton2)
        backToStepTwoButton2.setOnClickListener(){
            val stepTwoPartTwoStandardArrayLayout : View = findViewById(R.id.stepTwoPartTwoStandardArrayLayout)
            stepTwoPartTwoStandardArrayLayout.visibility = View.GONE
        }

        val backToStep2Button : Button = findViewById(R.id.backToStep2Button)
        backToStep2Button.setOnClickListener(){
            val stepTwoPartTwoRollLayout : View = findViewById(R.id.stepTwoPartTwoRollLayout)
            stepTwoPartTwoRollLayout.visibility = View.GONE
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
