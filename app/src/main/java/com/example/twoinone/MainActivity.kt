package com.example.twoinone

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var numberbutton: Button
    private lateinit var guessbutton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberbutton = findViewById(R.id.btnnumber)
        numberbutton.setOnClickListener { startGame(numbersgame()) }
        guessbutton = findViewById(R.id.btnguess)
        guessbutton.setOnClickListener { startGame(guessthephrase()) }

        title = "Main Activity"
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.numbers_game -> {
                startGame(numbersgame())
                return true
            }
            R.id.guess_the_phrase -> {
                startGame(guessthephrase())
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startGame(activity: Activity){
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
    }
}