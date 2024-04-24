package com.example.rockpaperscissor

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.FlowPreview



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

       val startGame = findViewById<Button>(R.id.StartGame)
        startGame.setOnClickListener {
            val intent = Intent (this, StartGame::class.java)
            startActivity(intent)
        }
    }
}


