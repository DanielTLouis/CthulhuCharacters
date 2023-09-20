package com.example.cthulhucharacter

import android.content.Context
import androidx.core.text.isDigitsOnly
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.OutputStream

class Character(){
    /**
     * Add a note section that can add breaks / new entries for each session, maybe a tab with the character to get
     * to the stats or the notes. also add an edit section for  character
     * also make easy inserts for line breaks and chapter / charater breaks to better organize what is going on.
     * add the fellow player for the charater breaks
     */

    /**
     * variables
     */
    //descriptors
    var name : String = ""
    var occupation : String = ""
    var gender : Int = 0
    var residence : String = ""
    var age : Int = 0

    //Stats
    var strength : Int = 0
    var constitution : Int = 0
    var dexterity : Int = 0
    var intelligence : Int = 0
    var size : Int = 0
    var power : Int = 0
    var appearance : Int = 0
    var education : Int = 0

    //Health
    var maxHitPoints : Int = 0
    var hitPoints : Int = 0
    var maxMagicPoints : Int = 0
    var magicPoints : Int = 0
    var startingLuck : Int = 0
    var luck : Int = 0
    var startingSanity : Int = 0
    var sanity : Int = 0

    //conditions
    var temporarySanity : Boolean = false
    var indefiniteInsanity : Boolean = false
    var majorWound : Boolean = false
    var unconscious : Boolean = false
    var dying : Boolean = false

    //skills
    //could ust first digit to show if it is checked or not (1-99) + 0r - 100
    var accounting : Int = 5 //105 would be checked
    var anthropology : Int = 1 // 1 would be unchecked
    var appraise : Int = 5
    var archaeology : Int = 1
    var artAndCraft : Int = 5
    var arts : String = ""
    var creditRating : Int = 0
    var charm : Int = 15
    var climb : Int = 20
    var cthulhuMythos : Int = 0
    var disguise : Int = 5
    var driveAuto : Int = 20
    var elcRepair : Int = 10
    var fastTalk : Int = 5
    var fightingBrawl : Int = 25
    var firearmsHandgun : Int = 20
    var firearmsRifle : Int = 25
    var firstAid : Int = 30
    var history : Int = 5
    var intimidate : Int = 15
    var jump : Int = 20
    var languageOther : Int = 1
    var languages : String = ""
    var languageOwn : Int = 0
    var law : Int = 5
    var libraryUse : Int = 20
    var listen : Int = 20
    var locksmith : Int = 1
    var mechRepair : Int = 10
    var medicine : Int = 1
    var naturalWorld : Int = 10
    var navigate : Int = 10
    var occult : Int = 5
    var persuade : Int = 10
    var pilot : Int = 1
    var psychoanalysis : Int = 1
    var psychology : Int = 10
    var ride : Int = 5
    var science : Int = 1
    var sciences : String = ""
    var sleightOfHand : Int = 10
    var spotHidden : Int = 25
    var stealth : Int = 20
    var survival : Int = 10
    var swim : Int = 20
    var thro : Int = 20
    var track : Int = 10

    //build
    var move : Int = 0
    var build : Int = 0
    var dodge : Int = 0
    var damageBonus : String = "0"

    //combat
    var attackName : String = ""
    var damage : String = ""
    var numOfAttacks : String = ""
    var range : String = ""
    var ammo : String = ""
    var malf : String = ""

    //Background
    var myStory : String = ""
    var personalDescription : String = ""
    var traits : String = ""
    var ideologyAndBeliefs : String = ""
    var injuriesAndScars : String = ""
    var significantPeople : String = ""
    var phobiasAndManias : String = ""
    var meaningfulLocations : String = ""
    var arcaneTomesAndSpells : String = ""
    var treasuredPossessions : String = ""
    var encountersWithStrangeEntities : String = ""

    //Gear and Possessions
    var gearAndPossessions : String = ""

    //Wealth
    var spendingLevel : Int = 0
    var cash : Int = 0
    var assets : String = ""

    //fellow investigators
    var characterNames : String = ""
    var playerNames : String = ""

    var test : String = ""

    /**
     * functions
     */

    fun loadCharacter(json : String) : Character{
        try {
            val obj = JSONObject(json)
            val characterArray = obj.getJSONArray("Character")
            val characterDetail = characterArray.getJSONObject(0)

            this.name = (characterDetail.getString("name"))
            this.occupation = (characterDetail.getString("occupation"))
            this.gender = (characterDetail.getInt("gender"))
            this.residence = (characterDetail.getString("residence"))
            this.age = (characterDetail.getInt("age"))
            this.strength = (characterDetail.getInt("strength"))
            this.constitution = (characterDetail.getInt("constitution"))
            this.dexterity = (characterDetail.getInt("dexterity"))
            this.intelligence = (characterDetail.getInt("intelligence"))
            this.size = (characterDetail.getInt("size"))
            this.power = (characterDetail.getInt("power"))
            this.appearance = (characterDetail.getInt("appearance"))
            this.education = (characterDetail.getInt("education"))
            this.maxHitPoints = (characterDetail.getInt("maxHitPoints"))
            this.hitPoints = (characterDetail.getInt("hitPoints"))
            this.maxMagicPoints = (characterDetail.getInt("maxMagicPoints"))
            this.magicPoints = (characterDetail.getInt("magicPoints"))
            this.startingLuck = (characterDetail.getInt("startingLuck"))
            this.luck = (characterDetail.getInt("luck"))
            this.startingSanity = (characterDetail.getInt("startingSanity"))
            this.sanity = (characterDetail.getInt("sanity"))
            this.temporarySanity = (characterDetail.getBoolean("temporarySanity"))
            this.indefiniteInsanity = (characterDetail.getBoolean("indefiniteInsanity"))
            this.majorWound = (characterDetail.getBoolean("majorWound"))
            this.unconscious = (characterDetail.getBoolean("unconscious"))
            this.dying = (characterDetail.getBoolean("dying"))
            this.accounting = (characterDetail.getInt("accounting"))
            this.anthropology = (characterDetail.getInt("anthropology"))
            this.appraise = (characterDetail.getInt("appraise"))
            this.archaeology = (characterDetail.getInt("archaeology"))
            this.artAndCraft = (characterDetail.getInt("artAndCraft"))
            this.arts = (characterDetail.getString("ArtandCraftArray"))
            this.charm = (characterDetail.getInt("charm"))
            this.climb = (characterDetail.getInt("climb"))
            this.cthulhuMythos = (characterDetail.getInt("cthulhuMythos"))
            this.disguise = (characterDetail.getInt("disguise"))
            this.driveAuto = (characterDetail.getInt("driveAuto"))
            this.elcRepair = (characterDetail.getInt("elcRepair"))
            this.fastTalk = (characterDetail.getInt("fastTalk"))
            this.fightingBrawl = (characterDetail.getInt("fightingBrawl"))
            this.firearmsHandgun = (characterDetail.getInt("firearmsHandgun"))
            this.firearmsRifle = (characterDetail.getInt("firearmsRifle"))
            this.firstAid = (characterDetail.getInt("firstAid"))
            this.intimidate = (characterDetail.getInt("intimidate"))
            this.jump = (characterDetail.getInt("jump"))
            this.languageOther = (characterDetail.getInt("languageOther"))
            this.languages = (characterDetail.getString("LanguageArray"))
            this.law = (characterDetail.getInt("law"))
            this.libraryUse = (characterDetail.getInt("libraryUse"))
            this.listen = (characterDetail.getInt("listen"))
            this.locksmith = (characterDetail.getInt("locksmith"))
            this.mechRepair = (characterDetail.getInt("mechRepair"))
            this.medicine = (characterDetail.getInt("medicine"))
            this.naturalWorld = (characterDetail.getInt("naturalWorld"))
            this.navigate = (characterDetail.getInt("navigate"))
            this.occult = (characterDetail.getInt("occult"))
            this.persuade = (characterDetail.getInt("persuade"))
            this.pilot = (characterDetail.getInt("pilot"))
            this.psychoanalysis = (characterDetail.getInt("psychoanalysis"))
            this.psychology = (characterDetail.getInt("psychology"))
            this.ride = (characterDetail.getInt("ride"))
            this.science = (characterDetail.getInt("science"))
            this.sciences = (characterDetail.getString("ScienceArray"))
            this.sleightOfHand = (characterDetail.getInt("sleightOfHand"))
            this.spotHidden = (characterDetail.getInt("spotHidden"))
            this.stealth = (characterDetail.getInt("spotHidden"))
            this.survival = (characterDetail.getInt("survival"))
            this.swim = (characterDetail.getInt("swim"))
            this.thro = (characterDetail.getInt("thro"))
            this.track = (characterDetail.getInt("track"))
            this.move = (characterDetail.getInt("move"))
            this.build = (characterDetail.getInt("build"))
            this.dodge = (characterDetail.getInt("dodge"))
            this.damageBonus = (characterDetail.getString("damageBonus"))
            this.attackName = (characterDetail.getString("attackName"))
            this.damage = (characterDetail.getString("damage"))
            this.numOfAttacks = (characterDetail.getString("numOfAttacks"))
            this.range = (characterDetail.getString("range"))
            this.ammo = (characterDetail.getString("ammo"))
            this.malf = (characterDetail.getString("malf"))
            this.myStory = (characterDetail.getString("myStory"))
            this.personalDescription = (characterDetail.getString("personalDescription"))
            this.traits = (characterDetail.getString("traits"))
            this.ideologyAndBeliefs = (characterDetail.getString("ideologyAndBeliefs"))
            this.injuriesAndScars = (characterDetail.getString("injuriesAndScars"))
            this.significantPeople = (characterDetail.getString("significantPeople"))
            this.phobiasAndManias = (characterDetail.getString("phobiasAndManias"))
            this.meaningfulLocations = (characterDetail.getString("meaningfulLocations"))
            this.arcaneTomesAndSpells = (characterDetail.getString("arcaneTomesAndSpells"))
            this.treasuredPossessions = (characterDetail.getString("treasuredPossessions"))
            this.encountersWithStrangeEntities = (characterDetail.getString("encountersWithStrangeEntities"))
            this.gearAndPossessions = (characterDetail.getString("gearAndPossessions"))
            this.spendingLevel = (characterDetail.getInt("spendingLevel"))
            this.cash = (characterDetail.getInt("cash"))
            this.assets = (characterDetail.getString("assets"))
            this.characterNames = (characterDetail.getString("characterNames"))
            this.playerNames = (characterDetail.getString("playerNames"))
        }
        catch(e: IOException){
            this.name = "Did not find file to open"
        }

        return this
    }

    //print character class to TextView this will change to save json file
    fun createJson() : String {
        //add items
        val json = "{\"Character\" :\n" +
                "[\n" +
                "  {\"name\": \"${this.name}\",\n" +
                "   \"occupation\" : \"${this.occupation}\",\n" +
                "    \"gender\" : ${this.gender},\n" +
                "    \"residence\" : \"${this.residence}\",\n" +
                "    \"age\" : ${this.age},\n" +
                "    \"strength\" : ${this.strength},\n" +
                "    \"constitution\" : ${this.constitution},\n" +
                "    \"dexterity\" : ${this.dexterity},\n" +
                "    \"intelligence\" : ${this.intelligence},\n" +
                "    \"size\" : ${this.size},\n" +
                "    \"power\" : ${this.power},\n" +
                "    \"appearance\" : ${this.appearance},\n" +
                "    \"education\" : ${this.education},\n" +
                "    \"maxHitPoints\" : ${this.maxHitPoints},\n" +
                "    \"hitPoints\" : ${this.hitPoints},\n" +
                "    \"maxMagicPoints\" : ${this.maxMagicPoints},\n" +
                "    \"magicPoints\" : ${this.magicPoints},\n" +
                "    \"startingLuck\" : ${this.startingLuck},\n" +
                "    \"luck\" : ${this.luck},\n" +
                "    \"startingSanity\" : ${this.startingSanity},\n" +
                "    \"sanity\" : ${this.sanity},\n" +
                "    \"temporarySanity\" : ${this.temporarySanity},\n" +
                "    \"indefiniteInsanity\" : ${this.indefiniteInsanity},\n" +
                "    \"majorWound\" : ${this.majorWound},\n" +
                "    \"unconscious\" : ${this.unconscious},\n" +
                "    \"dying\" : ${this.dying},\n" +
                "    \"accounting\" : ${this.accounting},\n" +
                "    \"anthropology\" : ${this.anthropology},\n" +
                "    \"appraise\" : ${this.appraise},\n" +
                "    \"archaeology\" : ${this.archaeology},\n" +
                "    \"artAndCraft\" : ${this.artAndCraft},\n" +
                "    \"ArtandCraftArray\": \"${this.arts}\",\n" +
                "    \"charm\" : ${this.charm},\n" +
                "    \"climb\" : ${this.climb},\n" +
                "    \"cthulhuMythos\" : ${this.cthulhuMythos},\n" +
                "    \"disguise\" : ${this.disguise},\n" +
                "    \"driveAuto\" : ${this.driveAuto},\n" +
                "    \"elcRepair\" :${this.elcRepair},\n" +
                "    \"fastTalk\" : ${this.fastTalk},\n" +
                "    \"fightingBrawl\" : ${this.fightingBrawl},\n" +
                "    \"firearmsHandgun\" : ${this.firearmsHandgun},\n" +
                "    \"firearmsRifle\" : ${this.firearmsRifle},\n" +
                "    \"firstAid\" : ${this.firstAid},\n" +
                "    \"intimidate\" : ${this.intimidate},\n" +
                "    \"jump\" : ${this.jump},\n" +
                "    \"languageOther\" : ${this.languageOther},\n" +
                "    \"LanguageArray\" : \"${this.languages}\",\n" +
                "    \"law\" : ${this.law},\n" +
                "    \"libraryUse\" : ${this.libraryUse},\n" +
                "    \"listen\" : ${this.listen},\n" +
                "    \"locksmith\" : ${this.locksmith},\n" +
                "    \"mechRepair\" : ${this.mechRepair},\n" +
                "    \"medicine\" : ${this.medicine},\n" +
                "    \"naturalWorld\" : ${this.naturalWorld},\n" +
                "    \"navigate\" : ${this.navigate},\n" +
                "    \"occult\" : ${this.occult},\n" +
                "    \"persuade\" : ${this.persuade},\n" +
                "    \"pilot\" : ${this.pilot},\n" +
                "    \"psychoanalysis\" : ${this.psychoanalysis},\n" +
                "    \"psychology\" : ${this.psychology},\n" +
                "    \"ride\" : ${this.ride},\n" +
                "    \"science\" : ${this.science},\n" +
                "    \"ScienceArray\" : \"${this.sciences}\",\n" +
                "    \"sleightOfHand\" : ${this.sleightOfHand},\n" +
                "    \"spotHidden\" : ${this.spotHidden},\n" +
                "    \"stealth\" : ${this.stealth},\n" +
                "    \"survival\" : ${this.survival},\n" +
                "    \"swim\" : ${this.swim},\n" +
                "    \"thro\" : ${this.thro},\n" +
                "    \"track\" : ${this.track},\n" +
                "    \"move\" : ${this.move},\n" +
                "    \"build\" : ${this.build},\n" +
                "    \"dodge\" : ${this.dodge},\n" +
                "    \"damageBonus\" : \"${this.damageBonus}\",\n" +
                "    \"attackName\" : \"${this.attackName}\",\n" +
                "    \"damage\" : \"${this.damage}\",\n" +
                "    \"numOfAttacks\" : \"${this.numOfAttacks}\",\n" +
                "    \"range\" : \"${this.range}\",\n" +
                "    \"ammo\" : \"${this.ammo}\",\n" +
                "    \"malf\" : \"${this.malf}\",\n" +
                "    \"myStory\" : \"${this.myStory}\",\n" +
                "    \"personalDescription\" : \"${this.personalDescription}\",\n" +
                "    \"traits\" : \"${this.traits}\",\n" +
                "    \"ideologyAndBeliefs\" : \"${this.ideologyAndBeliefs}\",\n" +
                "    \"injuriesAndScars\" : \"${this.injuriesAndScars}\",\n" +
                "    \"significantPeople\" : \"${this.significantPeople}\",\n" +
                "    \"phobiasAndManias\" : \"${this.phobiasAndManias}\",\n" +
                "    \"meaningfulLocations\" : \"${this.meaningfulLocations}\",\n" +
                "    \"arcaneTomesAndSpells\" : \"${this.arcaneTomesAndSpells}\",\n" +
                "    \"treasuredPossessions\" : \"${this.treasuredPossessions}\",\n" +
                "    \"encountersWithStrangeEntities\" : \"${this.encountersWithStrangeEntities}\",\n" +
                "    \"gearAndPossessions\" : \"${this.gearAndPossessions}\",\n" +
                "    \"spendingLevel\" : ${this.spendingLevel},\n" +
                "    \"cash\" : ${this.cash},\n" +
                "    \"assets\" : \"${this.assets}\",\n" +
                "    \"characterNames\" : \"${this.characterNames}\",\n" +
                "    \"playerNames\" : \"${this.playerNames}\"\n" +
                "  }\n" +
                "]}"
        return json
    }

    fun skillPicker(text : String) : Int{
        if(this.arts.contains(text)) {
            var tempArray : List<String> = listOf()
            tempArray = this.arts.split(';')
            for(i in tempArray){
                if(i.contains(text)){
                    var tempArray2 : List<String> = i.split('.')
                    tempArray2[1].dropLast(1)
                    return tempArray2[1].toInt()
                }
            }
            return -2
        }else if(this.languages.contains(text)) {
            var tempArray : List<String> = listOf()
            tempArray = this.languages.split(';')
            for(i in tempArray){
                if(i.contains(text)){
                    var tempArray2 : List<String> = i.split('.')
                    tempArray2[1].dropLast(1)
                    return tempArray2[1].toInt()
                }
            }
            return -2
        }else if(this.sciences.contains(text)) {
            var tempArray : List<String> = listOf()
            tempArray = this.sciences.split(';')
            for(i in tempArray){
                if(i.contains(text)){
                    var tempArray2 : List<String> = i.split('.')
                    tempArray2[1].dropLast(1)
                    return tempArray2[1].toInt()
                }
            }
            return -2
        }
        when(text){
            "Accounting"->return this.accounting
            "Anthropology"->return this.anthropology
            "Appraise"->return this.appraise
            "Archaeology"->return this.archaeology
            "ArtandCraft"->return this.artAndCraft
            "Charm"->return this.charm
            "Climb"->return this.climb
            "Cthulhu Mythos"->return this.cthulhuMythos
            "Disguise"->return this.disguise
            "Drive Auto"->return this.driveAuto
            "Elec Repair"->return this.elcRepair
            "Fast Talk"->return this.fastTalk
            "Fighting(Brawl)"->return this.fightingBrawl
            "Firearms(Handgun)"->return this.firearmsHandgun
            "Firearms(Rifle)"->return this.firearmsRifle
            "First Aid"->return this.firstAid
            "History"->return this.history
            "Intimidate"->return this.intimidate
            "Jump"->return this.jump
            "Language Other"->return this.languageOther
            "Language Own"->return this.languageOwn
            "Law"->return this.law
            "Library Use"->return this.libraryUse
            "Listen"->return this.listen
            "Locksmith"->return this.locksmith
            "Mechanical"->return this.mechRepair
            "Medicine"->return this.medicine
            "Natural World"->return this.naturalWorld
            "Navigate"->return this.navigate
            "Occult"->return this.occult
            "Persuade"->return this.persuade
            "Pilot"->return this.pilot
            "Psychoanalysis"->return this.psychoanalysis
            "Psychology"->return this.psychology
            "Ride"->return this.ride
            "Science"->return this.science
            "Sleight of Hand"->return this.sleightOfHand
            "Spot Hidden"->return this.spotHidden
            "Stealth"->return this.stealth
            "Survival"->return this.survival
            "Throw"->return this.thro
            "Track"->return this.track
            else -> return -1
        }
    }
}