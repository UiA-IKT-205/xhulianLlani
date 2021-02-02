package no.uia.ikt205.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime

class MainActivity : AppCompatActivity() {

    lateinit var timer:CountDownTimer
    lateinit var startButton:Button
    lateinit var ThirtyButton:Button
    lateinit var SixtyButton:Button
    lateinit var NinetyButton:Button
    lateinit var HnTwentyButton:Button
    lateinit var coutdownDisplay:TextView

    var timeToCountDownInMs = 5000L
    val timeTicks = 1000L
    var bool = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       startButton = findViewById<Button>(R.id.startCountdownButton)
        ThirtyButton = findViewById<Button>(R.id.button_30)
        SixtyButton = findViewById<Button>(R.id.button_60)
        NinetyButton = findViewById<Button>(R.id.button_90)
        HnTwentyButton = findViewById<Button>(R.id.button_120)

        ThirtyButton.setOnClickListener(){
            timeToCountDownInMs = 1800000L
            Toast.makeText(this@MainActivity,"Endret tid til 30 min", Toast.LENGTH_SHORT).show()
        }

        SixtyButton.setOnClickListener(){
            timeToCountDownInMs = 3600000L
            Toast.makeText(this@MainActivity,"Endret tid til 60 min", Toast.LENGTH_SHORT).show()
        }

        NinetyButton.setOnClickListener(){
            timeToCountDownInMs = 5400000L
            Toast.makeText(this@MainActivity,"Endret tid til 90 min", Toast.LENGTH_SHORT).show()
        }

        HnTwentyButton.setOnClickListener(){
            timeToCountDownInMs = 7200000L
            Toast.makeText(this@MainActivity,"Endret tid til 120 min", Toast.LENGTH_SHORT).show()
        }

       startButton.setOnClickListener(){
           startCountDown(it)
       }
       coutdownDisplay = findViewById<TextView>(R.id.countDownView)

    }

    fun startCountDown(v: View){
        if(bool == true){
            timer.cancel()
        }

        bool = true

        timer = object : CountDownTimer(timeToCountDownInMs,timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity,"Arbeidsøkt er ferdig", Toast.LENGTH_SHORT).show()
                bool = false
            }

            override fun onTick(millisUntilFinished: Long) {
               updateCountDownDisplay(millisUntilFinished)
            }
        }

        timer.start()
    }

    fun updateCountDownDisplay(timeInMs:Long){
        coutdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)
    }

}