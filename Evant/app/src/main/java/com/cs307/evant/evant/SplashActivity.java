package com.cs307.evant.evant;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler();
        final ProgressBar bar = (ProgressBar) findViewById(R.id.progressBar3);
        bar.setProgress(0);
        CountDownTimer mCountDownTimer=new CountDownTimer(3000,10) {
            int i = 0;
            @Override
            public void onTick(long millisUntilFinished) {
                i++;
                bar.setProgress((int)i*100/(3000/10));
            }

            @Override
            public void onFinish() {
                //Do what you want
                i++;
                bar.setProgress(100);
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            }
        };
        mCountDownTimer.start();
        /*handler.postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            }
        }, 3000);*/
    }
}
