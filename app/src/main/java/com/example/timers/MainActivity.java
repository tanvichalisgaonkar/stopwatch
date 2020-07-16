package com.example.timers;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar timerSeekBar;
    TextView timertextView;
    Button gobutton;
    CountDownTimer countDownTimer;
    boolean counterActive = false;
    public void updateTimer(int secleft){
        int minutes = (int) secleft/60;
        int seconds = secleft - (minutes*60);

        String secondString;
        if(seconds == 0)
            secondString = "00";
        else if(seconds<=9) {
            secondString = "0";
            secondString += Integer.toString(seconds);
        }else{
            secondString = Integer.toString(seconds);
        }
        timertextView.setText(Integer.toString(minutes) + ":" + secondString);
    }
    public void resetTimer(){
        timertextView.setText("0:30");
        timerSeekBar.setProgress(30);
        countDownTimer.cancel();
        gobutton.setText("GO");
        timerSeekBar.setEnabled(true);
        counterActive = false;
    }

    public void controlTimer(View view){
        if(counterActive == false)
        {
            counterActive = true;
            timerSeekBar.setEnabled(false);
            gobutton.setText("Stop");
           countDownTimer = new CountDownTimer(timerSeekBar.getProgress()*1000 +100,1000){
               @Override
               public void onTick(long l) {
                    updateTimer((int)l/1000);
               }

               @Override
               public void onFinish() {
                   timertextView.setText("0:00");
                   MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.alarm);
                   mediaPlayer.start();
                   resetTimer();
               }
           }.start();
        }else {
           resetTimer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar  = findViewById(R.id.timerSeekBar);
        timertextView = findViewById(R.id.timer);
        gobutton = findViewById(R.id.goButton);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);
        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
               updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}