package com.example.twoinone


import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_numbersgame.*
import kotlin.random.Random

class numbersgame : AppCompatActivity() {

    private lateinit var guesstext: EditText
    private lateinit var guessbutton: Button
    private lateinit var messages: ArrayList<String>
    private lateinit var Text: TextView
    private lateinit var clMain: ConstraintLayout
    private var input = 2
    private var guesses = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_numbersgame)

        input = Random.nextInt(10)
        clMain = findViewById(R.id.clMain)
        messages = ArrayList()
        Text = findViewById(R.id.Text)

        recyc.adapter = MessageAdapter(this, messages)
        recyc.layoutManager = LinearLayoutManager(this)

        guesstext = findViewById(R.id.Edit)
        guessbutton = findViewById(R.id.button)
        guessbutton.setOnClickListener { addMessage() }
    }

    private fun addMessage(){
        val msg = guesstext.text.toString()
        if(msg.isNotEmpty()){
            if(guesses>0){
                if(msg.toInt() == input){
                    disableEntry()
                    showAlert("You win!\n\nPlay again?")
                }else{
                    guesses--
                    messages.add("You guess $msg")
                    messages.add("You have $guesses guesses left")
                }
                if(guesses==0){
                    disableEntry()
                    messages.add("You lose - The correct answer was $input")
                    messages.add("Game Over")
                    showAlert("You lose...\nThe correct answer was $input.\n\nPlay again?")
                }
            }
            guesstext.text.clear()
            guesstext.clearFocus()
            recyc.adapter?.notifyDataSetChanged()
        }else{
            Snackbar.make(clMain, "Please enter number", Snackbar.LENGTH_LONG).show()
        }
    }
    private fun disableEntry(){
        guessbutton.isEnabled = false
        guessbutton.isClickable = false
        guesstext.isEnabled = false
        guesstext.isClickable = false
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_game, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val item: MenuItem = menu!!.getItem(1)
        if(item.title == "Other Game"){ item.title = "Guess The Phrase" }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.new_game -> {
                showAlert("did you want to start new game??")
                return true
            }
            R.id.other_game -> {
                changeScreen(guessthephrase())
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
    private fun showAlert(title: String) {
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
}