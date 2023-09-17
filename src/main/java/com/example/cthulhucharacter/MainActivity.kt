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
 * should have a tab menu to flip through different windows: menu / home, characters, about, about creator
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Buttons
         */
        val createCharacterButton : Button = findViewById(R.id.createCharacterButton)
        createCharacterButton.setOnClickListener{
            //val intent = Intent(this@MainActivity, Create1920sPlayerStepOneActivity::class.java)
            val intent = Intent(this@MainActivity, Create1920sPlayerStepOneActivity::class.java)
            startActivity(intent)
        }
    }
}
