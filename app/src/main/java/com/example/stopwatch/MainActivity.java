package com.example.stopwatch;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.StaticLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.io.UncheckedIOException;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    EditText editText;
    Button button;
    Button unit_selector;
    CountDownTimer new_timer;
    boolean is_running = false;
    long input_time = 0;
    int time_unit = 1; // S = 1 , M = 2, H = 3

    public void change_unit(View view){

        if(String.valueOf(unit_selector.getText()).equals("S"))
        {
            unit_selector.setText(R.string.m);
            time_unit = 2;
        }
        else if(String.valueOf(unit_selector.getText()).equals("M"))
        {
            unit_selector.setText(R.string.h);
            time_unit = 3;
        }
        else
        {
            unit_selector.setText(R.string.s);
            time_unit = 1;
        }
    }

    //THIS METHOD IS CALLED BY START BUTTON
    public void start_timer(View view) {

        if (is_running == false) {
            before_timer_starts();
            is_running = true;
        }
        else
        {
            new_timer.cancel();
            is_running = false;
            after_timer_ends();
        }
    }

    // TO START TIMER
    protected void timer(long millis){

        new_timer = new CountDownTimer(millis,100) {
            public void onTick(long millisUntilFinished) {
                progressBar.incrementProgressBy(100);
            }

            public void onFinish() {
                after_timer_ends();
                is_running = false;
            }
        }.start();

    }

    // BEFORE SETTING TIMER - CALLED FROM START TIMER()
    protected void before_timer_starts(){
        progressBar.setAlpha(0.0f);
        if(!String.valueOf(editText.getText()) .equals("")) {
            input_time = Long.parseLong(String.valueOf(editText.getText()));
            editText.setAlpha(0.0f);
            unit_selector.setAlpha(0.0f);
            button.setText(R.string.stop);
            button.animate().translationX(-270f);
            progressBar.setAlpha(1.0f);
            if(time_unit == 1)
                input_time *= 1000;
            else if(time_unit == 2)
                input_time = input_time*60*1000;
            else
                input_time = input_time*60*60*1000;
            progressBar.setMax((int) input_time);
            timer(input_time);
        }
    }

    //AFTER TIMER ENDS
    protected void after_timer_ends()
    {
        progressBar.setProgress(0);
        progressBar.setAlpha(0.0f);
        editText.setAlpha(1.0f);
        unit_selector.setAlpha(1.0f);
        button.setText(R.string.start);
        button.animate().translationX(0.0f);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        editText = (EditText) findViewById(R.id.input_time);
        button = (Button) findViewById(R.id.start_stop_button);
        unit_selector = (Button)findViewById(R.id.unit_selector);
        //Drawable d =
        //progressBar.setProgressDrawable();
    }
}