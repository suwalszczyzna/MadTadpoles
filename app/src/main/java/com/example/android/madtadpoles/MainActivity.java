package com.example.android.madtadpoles;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tadpoles);
    }

                            // zmienne globalne (ustawienie życia po 100 i losowe obrazenia)
    int healthKM = 100;
    int healthKT = 100;
    int randomNumber1;
                            //metoda losowanko liczba obrazen dla testów
    public int randomNumber (){
        Random rand = new Random();
        randomNumber1 = rand.nextInt(20);
        return randomNumber1;
    }

                            // ustawiamy paski zycia graczy A I B ()w każdym dałem ifa żeby po spadku ponizej 0 paski się odnawiały - dla testów :D )
    public void progressbarA (){
        ProgressBar playerA = findViewById(R.id.progressA);
        healthKM -=randomNumber();
        if (healthKM <0){
            healthKM =100;
        }
        playerA.setProgress(healthKM);
    }
    public void progressbarB (){
        ProgressBar playerA = findViewById(R.id.progressB);
        healthKT -=randomNumber();
        if (healthKT <0){
            healthKT =100;
        }
        playerA.setProgress(healthKT);
    }
                            // wciśkanie przycisków ataku dla bu graczy
    public void attackPlayerA (View view){
        progressbarB();
    }
    public void attackPlayerB (View view) {
        progressbarA();
    }
}
