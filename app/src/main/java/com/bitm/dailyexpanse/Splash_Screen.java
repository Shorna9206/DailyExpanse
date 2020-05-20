package com.bitm.dailyexpanse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

public class Splash_Screen extends AppCompatActivity {

    private ProgressBar progressBar;
    private int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);

        progressBar= findViewById (R.id.splash);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
                startapp();
            }
        });

        thread.start();
    }
    public void doWork(){

        for(progress=20;progress<=100;progress=progress+20){
            try {


                Thread.sleep(1000);
                progressBar.setProgress(progress);

            }
            catch (InterruptedException e){
                e.printStackTrace();
            }

        }

    }
    public void startapp(){

        Intent intent = new Intent(Splash_Screen.this,MainActivity.class);
        startActivity(intent);
        finish();
    }


}


