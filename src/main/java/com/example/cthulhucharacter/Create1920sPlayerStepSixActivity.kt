package com.example.cthulhucharacter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class Create1920sPlayerStepSixActivity : ComponentActivity() {

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
        setContentView(R.layout.activity_create1920splayerstepsix)

        val sixTextView : TextView = findViewById(R.id.ItemsListTextView)
        val backToStepSixButton : Button = findViewById(R.id.backToStepSixButton)
        val standardWepSpinner : Spinner = findViewById(R.id.standardWepSpinner)
        val standardItemsSpinner : Spinner = findViewById(R.id.standardItemsSpinner)
        val addItemButton : Button = findViewById(R.id.addItemButton)
        val customItemAddButton : Button = findViewById(R.id.customItemAddButton)
        val addStandardWepButton : Button = findViewById(R.id.addStandardWepButton)
        val addCustomWeaponButton : Button = findViewById(R.id.addCustomWeaponButton)
        val ContinuetoStepSevenButton : Button = findViewById(R.id.ContinuetoStepSevenButton)

        var newCharacter : Character = loadCharacter()

        //sixTextView.text = newCharacter.createJson()

        fun buildLists(){
            try {
                val json : String
                val  inputStream: InputStream = assets.open("Wepons.json")
                json = inputStream.bufferedReader().use{it.readText()}


                var obj = JSONObject(json)
                val wepArray = obj.getJSONArray("Weapons")
                for (i in 0 until wepArray.length()) {
                    val wepDetail = wepArray.getJSONObject(i)
                    weaponNameArray.add(wepDetail.getString("Name"))
                    weaponDamageArray.add(wepDetail.getString("Damage"))
                    weaponNumOfAttacksArray.add(wepDetail.getString("NumberOfAttacks"))
                    weaponRangeArray.add(wepDetail.getString("Range"))
                    weaponAmmoArray.add(wepDetail.getString("Ammo"))
                    weaponMalfArray.add(wepDetail.getString("Malf"))
                }
                inputStream.close()

                val json2 : String
                val inputStream2 : InputStream = assets.open("Item.json")
                json2 = inputStream2.bufferedReader().use{it.readText()}

                var obj2 = JSONObject(json2)
                val itArray = obj2.getJSONArray("Item")
                for(i  in 0 until  itArray.length()){
                    val itDetail = itArray.getJSONObject(i)
                    itemsArray.add(itDetail.getString("Name"))
                }

            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
        buildLists()

        if(standardWepSpinner != null){
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, weaponNameArray
            )
            standardWepSpinner.adapter = adapter
        }
        if(standardItemsSpinner != null){
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, itemsArray
            )
            standardItemsSpinner.adapter = adapter
        }
        fun displayGear (){
            val ItemsListTextView : TextView = findViewById(R.id.ItemsListTextView)
            var tempString : String = ""
            for(i in gearArray){
                tempString += i + "\n"
            }
            //ItemsListTextView.text = tempString
        }

        /**
         * Buttons
         */
        backToStepSixButton.setOnClickListener(){
            val intent = Intent(this@Create1920sPlayerStepSixActivity, Create1920sPlayerStepFiveActivity::class.java)
            startActivity(intent)
        }
        addItemButton.setOnClickListener(){
            if(standardItemsSpinner.selectedItem != null){
                if(!gearArray.contains(standardItemsSpinner.selectedItem.toString())) {
                    gearArray.add(standardItemsSpinner.selectedItem.toString())
                }
            }
            displayGear()
            saveCharacter(newCharacter)
        }
        customItemAddButton.setOnClickListener(){
            val customItemNameEditTextText : EditText = findViewById(R.id.customItemNameEditTextText)
            if(customItemNameEditTextText.text.toString() != null){
                if(!gearArray.contains(customItemNameEditTextText.text.toString())) {
                    gearArray.add(customItemNameEditTextText.text.toString())
                }
            }
            displayGear()
            saveCharacter(newCharacter)
        }
        addStandardWepButton.setOnClickListener(){

            //this is very wrong
            var index : Int = 0
            for(i in weaponNameArray.indices){
                if(weaponNameArray[i] == standardWepSpinner.selectedItem.toString()){
                    index = i
                }
            }
            if(standardWepSpinner.selectedItem != null) {
                if(!gearArray.contains(standardWepSpinner.selectedItem.toString())) {
                    gearArray.add(standardWepSpinner.selectedItem.toString())
                    weaponNameArray.add(weaponNameArray[index])
                    playerAttackName.add(weaponNameArray[index])
                    playerDamage.add(weaponDamageArray[index])
                    playerNumOfA.add(weaponNumOfAttacksArray[index])
                    playerRange.add(weaponRangeArray[index])
                    playerAmmo.add(weaponAmmoArray[index])
                    playerMalf.add(weaponMalfArray[index])
                }
            }
            displayGear()
            saveCharacter(newCharacter)
        }
        addCustomWeaponButton.setOnClickListener(){
            val customWeaponNameEditTextText : EditText = findViewById(R.id.customWeaponNameEditTextText)
            val customWeponDamageEditTextText : EditText = findViewById(R.id.customWeponDamageEditTextText)
            val customWeaponNumberOfAttahksEditTextText : EditText = findViewById(R.id.customWeaponNumberOfAttahksEditTextText)
            val customWeaponMalfEditTextText : EditText = findViewById(R.id.customWeaponMalfEditTextText)
            val customWeaponAmmoEditTextText : EditText = findViewById(R.id.customWeaponAmmoEditTextText)
            val customWeaponRangeEditTextText : EditText = findViewById(R.id.customWeaponRangeEditTextText)
            if(customWeaponNameEditTextText != null) {
                if (!gearArray.contains(customWeaponNameEditTextText.text.toString())) {
                    gearArray.add(customWeaponNameEditTextText.text.toString())
                    playerAttackName.add(customWeaponNameEditTextText.text.toString())
                    playerDamage.add(customWeponDamageEditTextText.text.toString())
                    playerNumOfA.add(customWeaponNumberOfAttahksEditTextText.text.toString())
                    playerRange.add(customWeaponRangeEditTextText.text.toString())
                    playerAmmo.add(customWeaponAmmoEditTextText.text.toString())
                    playerMalf.add(customWeaponMalfEditTextText.text.toString())
                }
            }
            displayGear()
            saveCharacter(newCharacter)
        }
        ContinuetoStepSevenButton.setOnClickListener(){
            saveCharacter(newCharacter)
            val intent = Intent(this@Create1920sPlayerStepSixActivity, Create1920sPlayerStepSevenActivity::class.java)
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