package no.uia.ikt205.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime

class MainActivity : AppCompatActivity() {

    lateinit var timer:CountDownTimer
    lateinit var startButton:Button
    lateinit var coutdownDisplay:TextView
    lateinit var coutdownDisplay2:TextView
    lateinit var repetitionsDisplay:EditText


    var timeToCountDownInMs = 900000L
    var timeToCountDownInMs2 = 0L
    val timeTicks = 1000L
    var bool = false
    var bool2 = false
    var repetition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val seek = findViewById<SeekBar>(R.id.sB)
        val seek2 =  findViewById<SeekBar>(R.id.sB2)

        seek?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar?, progress: Int, fromUser: Boolean) {
                var time:Long = progress.toLong()
                timeToCountDownInMs = progress.toLong()
                updateCountDownDisplay(time)

            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                //Cheese
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
                Toast.makeText(this@MainActivity,
                    "Progress is: " + seek.progress + "ms",
                    Toast.LENGTH_SHORT).show()
            }

        })

        seek2?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek2: SeekBar?, progress: Int, fromUser: Boolean) {
                var time:Long = progress.toLong()
                timeToCountDownInMs2 = progress.toLong()
                updateCountDownDisplay2(time)
            }


            override fun onStartTrackingTouch(seek: SeekBar) {
                //Cheese
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
                Toast.makeText(this@MainActivity,
                        "Progress is: " + seek.progress + "ms",
                        Toast.LENGTH_SHORT).show()
            }

        })

        repetitionsDisplay = findViewById<EditText>(R.id.repetitions)

        repetitionsDisplay.setOnClickListener(){
            repetition  = repetitionsDisplay.text.toString().toInt()
            Toast.makeText(this@MainActivity,
                    "Reps set",
                    Toast.LENGTH_SHORT).show()
        }

       startButton = findViewById<Button>(R.id.startCountdownButton)

       startButton.setOnClickListener(){
           startCountDown(it)
       }
       coutdownDisplay = findViewById<TextView>(R.id.countDownView)

        coutdownDisplay2 = findViewById<TextView>(R.id.countDownView2)


    }

    fun startPauseCountDown(){
        if(bool2 == true){
            timer.cancel()
        }

        bool2 = true

        timer = object : CountDownTimer(timeToCountDownInMs2,timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity,"Pause er ferdig", Toast.LENGTH_SHORT).show()
                bool2 = false
                if(repetition > 0){
                    bool = true
                    startCountDown(coutdownDisplay)
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                updateCountDownDisplay2(millisUntilFinished)
            }
        }

        timer.start()
    }

    fun startCountDown(v: View){
        if(bool){
            timer.cancel()
            repetition--
            repetitionsDisplay.setText(repetition.toString())
        }

        bool = true

        timer = object : CountDownTimer(timeToCountDownInMs,timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity,"Arbeidsøkt er ferdig", Toast.LENGTH_SHORT).show()
                bool = false
                startPauseCountDown()
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

    fun updateCountDownDisplay2(timeInMs:Long){
        coutdownDisplay2.text = millisecondsToDescriptiveTime(timeInMs)
    }

}