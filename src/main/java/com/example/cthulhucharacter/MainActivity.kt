package com.example.cthulhucharacter

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cthulhucharacter.ui.theme.CthulhuCharacterTheme

/**
 * MainActivity Class
 * * Is the first activity that will load on the start of the applicaiton
 * * This activity will have 2 options,
 * ** One to create a new character
 * ** Two to view existing characters.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Buttons
         */

        /**
         * Button createCharacterButton
         * * Will load the activity Create1920sPlayerStepOneActivity which will be the first step in creating a new character
         */
        val createCharacterButton : Button = findViewById(R.id.createCharacterButton)
        createCharacterButton.setOnClickListener{
            //val intent = Intent(this@MainActivity, Create1920sPlayerStepOneActivity::class.java)
            val intent = Intent(this@MainActivity, Create1920sPlayerStepOneActivity::class.java)
            startActivity(intent)
        }

        /**
         * Button charactersButton
         * * Will load the activity DisplayCharactersActivity which will load the character select screen for exsiting character
         * ** the loaded activity is the first step to display the already made characters stats
         */
        val charactersButton : Button = findViewById(R.id.charactersButton)
        charactersButton.setOnClickListener(){
            val intent = Intent(this@MainActivity, DisplayCharactersActivity::class.java)
            startActivity(intent)
        }
    }
}
