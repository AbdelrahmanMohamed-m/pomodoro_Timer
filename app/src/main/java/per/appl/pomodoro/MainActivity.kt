package per.appl.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import java.text.Format

class MainActivity : AppCompatActivity() {
    var timers: CountDownTimer? = null
    val startTimer: Long = 25 * 60 * 1000
    var remainingTime: Long = startTimer
    var isTimerRunning = false
    lateinit var timer: TextView
    lateinit var TVpromo: TextView
    private lateinit var Reset: TextView
    private lateinit var BtnStart: Button
    private lateinit var PB:ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        timer = findViewById(R.id.TimerYV)
        Reset = findViewById(R.id.ResetTV)
        BtnStart = findViewById(R.id.btn_start)
        TVpromo = findViewById(R.id.TVpromo)
        PB=findViewById(R.id.pb)
        BtnStart.setOnClickListener {
            //second way to do it through object from virtual class not existent
            if(!isTimerRunning)
            {
            starttimer(startTimer)
            TVpromo.text = resources.getText(R.string.keep)
        }}
        Reset.setOnClickListener {
            resetTimer()
        }
    }

    private fun starttimer(starttime:Long) {
        timers = object : CountDownTimer(starttime, 1 * 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished
                updateTimerText()
                PB.progress = remainingTime.toDouble().div(startTimer.toDouble()).times(100).toInt()
            }

            override fun onFinish() {
                Toast.makeText(this@MainActivity, "finish", Toast.LENGTH_SHORT).show()
                isTimerRunning=false
            }


        }.start()
        isTimerRunning=true
    }

    private fun resetTimer() {
        timers?.cancel()
        remainingTime = startTimer
        updateTimerText()
        TVpromo.text = resources.getText(R.string.app_name)
        isTimerRunning=false
        PB.setProgress(100)

    }

    private fun updateTimerText() {
        val minute = remainingTime.div(1000).div(60)
        val seconds = remainingTime.div(1000) % 60
        val format = String.format("%02d:%02d", minute, seconds)
        timer.text = format
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("remain time",remainingTime)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val savedtime=savedInstanceState.getLong("remain time")
        if(savedtime!= startTimer)
        starttimer(savedtime)
    }
}


// first way to get function from an abstract class
//class objto:CountDownTimer(1000,1000)
//{
//    override fun onTick(millisUntilFinished: Long) {
//    }
//
//    override fun onFinish() {
//    }
