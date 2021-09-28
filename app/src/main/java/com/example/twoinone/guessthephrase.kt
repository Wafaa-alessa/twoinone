package com.example.twoinone

import android.app.Activity
import android.content.DialogInterface
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_guessthepharse.*
import kotlin.random.Random
import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem

class guessthephrase : AppCompatActivity() {
    private lateinit var guessText: EditText
    private lateinit var guessbutton: Button
    private lateinit var messages: ArrayList<String>
    private lateinit var Text: TextView
    private lateinit var Text2: TextView
    private lateinit var clMain: ConstraintLayout
    private val input = "wafaa alessa"
    private val myinputDictionary = mutableMapOf<Int, Char>()
    private var myinput = ""
    private var guessedLetters = ""
    private var count = 0
    private var guessPhrase = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guessthepharse)

        for(i in input.indices){
            if(input[i] == ' '){
                myinputDictionary[i] = ' '
                myinput += ' '
            }else{
                myinputDictionary[i] = '*'
                myinput += '*'
            }
        }
        clMain = findViewById(R.id.clMain)
        messages = ArrayList()
        recyc.adapter = MessageAdapter(this, messages)
        recyc.layoutManager = LinearLayoutManager(this)
        guessText = findViewById(R.id.guesstext)
        guessbutton = findViewById(R.id.button)
        guessbutton.setOnClickListener { addMessage() }

        Text = findViewById(R.id.Text)
        Text2 = findViewById(R.id.Text2)
        updateText()
    }

    private fun addMessage(){

        val msg = guessText.text.toString()
        if(guessPhrase){
            if(msg == input){
                disableEntry()
                showAlertDialog("You win!\n\nPlay again?")
            }else{
                messages.add("Wrong guess: $msg")
                guessPhrase = false
                updateText()
            }
        }else{
            if(msg.isNotEmpty() && msg.length==1){
                myinput = ""
                guessPhrase = true
                checkLetters(msg[0])
            }else{
                Snackbar.make(clMain, "Please enter only one letter", Snackbar.LENGTH_LONG).show()
            }
        }
        guessText.text.clear()
        guessText.clearFocus()
        recyc.adapter?.notifyDataSetChanged()
    }
    private fun disableEntry(){
        guessbutton.isEnabled = false
        guessbutton.isClickable = false
        guessText.isEnabled = false
        guessText.isClickable = false
    }
    private fun updateText(){
        Text.text = "Phrase:  " + myinput.toUpperCase()
        Text2.text = "Guessed Letters:  " + guessedLetters
        if(guessPhrase){
            guessText.hint = "Guess the full phrase"
        }else{
            guessText.hint = "Guess a letter"
        }
    }
    private fun checkLetters(guessedLetter: Char){
        var found = 0
        for(i in input.indices){
            if(input[i] == guessedLetter){
                myinputDictionary[i] = guessedLetter
                found++
            }
        }
        for(i in myinputDictionary){myinput += myinputDictionary[i.key]}
        if(myinput==input){
            disableEntry()
            showAlertDialog("You win!\n\nPlay again?")
        }
        if(guessedLetters.isEmpty()){guessedLetters+=guessedLetter}else{guessedLetters+=", "+guessedLetter}
        if(found>0){
            messages.add("Found $found ${guessedLetter.toUpperCase()}(s)")
        }else{
            messages.add("No ${guessedLetter.toUpperCase()}s found")
        }
        count++
        val guessesLeft = 10 - count
        if(count<10){messages.add("$guessesLeft guesses remaining")}
        updateText()
        recyc.scrollToPosition(messages.size - 1)
    }

    private fun showAlertDialog(title: String) {
        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder.setMessage(title)
            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener {
                    dialog, id -> this.recreate()
            })
            .setNegativeButton("No", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Game Over")
        alert.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_game, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val item: MenuItem = menu!!.getItem(1)
        if(item.title == "Other Game"){ item.title = "Numbers Game" }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.new_game -> {
                showAlertDialog("did you want to start new game?")
                return true
            }
            R.id.other_game -> {
                changeScreen(numbersgame())
                return true
            }
            R.id.back -> {
                changeScreen(MainActivity())
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun changeScreen(activity: Activity){
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
    }
}
