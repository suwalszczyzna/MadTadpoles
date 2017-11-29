package com.example.android.madtadpoles;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    public int randomNumber() {
        Random rand = new Random();
        randomNumber1 = rand.nextInt(20);
        return randomNumber1;
    }

    /*
    ustawiamy paski zycia graczy A I B ()w każdym dałem ifa żeby
    po spadku ponizej 0 paski się odnawiały - dla testów :D )
    */
    public void progressbarA() {
        ProgressBar playerA = findViewById(R.id.progressA);
        healthKM -= randomNumber();
        if (healthKM < 0) {
            healthKM = 100;
        }
        playerA.setProgress(healthKM);
    }

    public void progressbarB() {
        ProgressBar playerA = findViewById(R.id.progressB);
        healthKT -= randomNumber();
        if (healthKT < 0) {
            healthKT = 100;
        }
        playerA.setProgress(healthKT);
    }

    // wciśkanie przycisków ataku dla obu graczy
    public void attackPlayerA(View view) {
        progressbarB();
        changePlayerColors(0);
    }

    public void attackPlayerB(View view) {

        progressbarA();
        changePlayerColors(1);
    }


    /*
    Cezary's changes - START
    metoda zmieniająca kolory nieaktywnego gracza (jeśli parameter == 0, tura należy do gracza
    grającego kijanka z toporem, jeśli parameter == 1, tura należy do gracza grającego kijanką
    z mieczem, jeśli parameter == 2, żaden z graczy nie ma swojej tury)
    */

    public void changePlayerColors(int parameter) {
        // jeśli tura należy do gracza grającego kijanką z mieczem:
        if (parameter == 0){
            //pojaw kijanke z mieczem
            swordTadpoleVisibility(1);

            //zniknij kijanke z toporem:
            axeTadpoleVisibility(0);
        }

        //jeśli tura należy do gracza grającego kijanką z toporem:
        else if (parameter == 1){
            //zniknij kijanke z mieczem
            swordTadpoleVisibility(0);

            //pojaw kijanke z toporem
            axeTadpoleVisibility(1);
        }
        //pojaw obie kijanki
        else if (parameter == 2){
            //pojaw kijanke z mieczem
            swordTadpoleVisibility(1);

            //pojaw kijanke z toporem
            axeTadpoleVisibility(1);
        }

        //zniknij obie kijanki
        else if (parameter == 3){
            swordTadpoleVisibility(0);
            axeTadpoleVisibility(0);
        }

    }

    public void swordTadpoleVisibility(int value) {
        // progress bar kijanki z mieczem
        ProgressBar progressSword = (ProgressBar) findViewById(R.id.progressA);

        // background View dla kijanki z mieczem
        View swordBack = findViewById(R.id.swordTadBack);

        // stworzenie ImageView dla kijanki z mieczem
        ImageView swordTadPole = (ImageView) findViewById(R.id.kijankaMiecz);

        //TextView "turnDisplay" dla kijanki z mieczem
        TextView turnDisplaySword = (TextView) findViewById(R.id.KMRound);

        // Button coundDownStart dla kijanki z mieczem

        Button countDownStartSword = (Button) findViewById(R.id.KMCountDS);

        // TextView powerAttack dla kijanki z mieczem

        TextView powerAttackSword = (TextView) findViewById(R.id.KMPowerAttack);

        // ImageButton kijanki z mieczem

        ImageButton btnAttackSword = (ImageButton) findViewById(R.id.KMBtnAttack);

        // info TextView dla kijanki z mieczem

        TextView infoSword = (TextView) findViewById(R.id.KMInfoTxt);

        // name TextView kijanki z mieczem

        TextView nameSword = (TextView) findViewById(R.id.KMName);

        // zniknij kijanke z mieczem
        if (value == 0) {

            // zmieniamy obraz kijanki z mieczem na nieaktywną kijankę z mieczem
            swordTadPole.setImageResource(R.drawable.tadpole_non_active);

            // zmieniamy napis na turnDisplay TextView na "Wait for your turn."
            turnDisplaySword.setText(R.string.notYourTurn);

            /*
            zmieniamy kolor turnDisplay TextView na nieaktywny zielony
            a kolor czcionki na nieaktywny tekst kolor
            */
            turnDisplaySword.setBackgroundColor(getResources().getColor(R.color.unactive_green));

            turnDisplaySword.setTextColor(getResources().getColor(R.color.unactive_white_icon));

            // zmieniamy guzik coundDownStartSword na nieaktywny i kolor tekstu na nieaktywny biały
            countDownStartSword.setBackgroundResource(R.drawable.my_button_grey);

            countDownStartSword.setTextColor(getResources().getColor(R.color.unactive_white_icon));

            // zmieniamy kolor tekstu powerAttack TextView kijanki z mieczem na szary

            powerAttackSword.setTextColor(getResources().getColor(R.color.unactive_green));

            // zmienamy attackButton kijanki z mieczem na nieaktywny

            btnAttackSword.setBackgroundResource(R.drawable.my_button_grey);

            // zmieniamy grafike attackButton kijanki z mieczem na nieaktywna

            btnAttackSword.setImageResource(R.drawable.ic_unnactive_miecz);

            // zmieniamy kolory info TextView kijanki z mieczem

            infoSword.setBackgroundResource(R.drawable.my_button_grey);
            infoSword.setTextColor(getResources().getColor(R.color.unactive_white_icon));

            // zmieniamy kolor imienia na nieaktywny biały

            nameSword.setTextColor(getResources().getColor(R.color.unactive_white_icon));

            // zmieniamy background dla kijanki z mieczem

            swordBack.setVisibility(View.VISIBLE);
            swordBack.setBackgroundResource(R.drawable.background_unnactive_view);

            //zmieiamy kolory progress baru kijanki z mieczem na nieaktywne
            progressSword.setProgressDrawable(getResources().getDrawable(R.drawable.progress_horizontal_unactive));

        }
        // pojaw kijanke z mieczem
        else if (value == 1) {
            // zmieniamy obraz kijanki z mieczem na aktywną kijankę z mieczem
            swordTadPole.setImageResource(R.drawable.tadpole);

            // zmieniamy napis na turnDisplay TextView na "Wait for your turn."
            turnDisplaySword.setText(R.string.YourTurn);

            /*
            zmieniamy kolor turnDisplay TextView na aktywny zielony
            a kolor czcionki na aktywny tekst kolor
            */
            turnDisplaySword.setBackgroundColor(getResources().getColor(R.color.main_green));

            turnDisplaySword.setTextColor(getResources().getColor(R.color.creme_text));

            // zmieniamy guzik coundDownStartSword na aktywny i kolor tekstu na aktywny biały
            countDownStartSword.setBackgroundResource(R.drawable.my_button);

            countDownStartSword.setTextColor(getResources().getColor(R.color.creme_text));

            // zmieniamy kolor tekstu powerAttack TextView kijanki z mieczem na bialy

            powerAttackSword.setTextColor(getResources().getColor(R.color.creme_text));

            // zmienamy attackButton kijanki z mieczem na aktywny

            btnAttackSword.setBackgroundResource(R.drawable.my_button);

            // zmieniamy grafike attackButton kijanki z mieczem na aktywna

            btnAttackSword.setImageResource(R.drawable.ic_miecz);

            // zmieniamy kolory info TextView kijanki z mieczem

            infoSword.setBackgroundResource(R.drawable.my_button_dark_green);
            infoSword.setTextColor(getResources().getColor(R.color.creme_text));

            // zmieniamy kolor imienia na aktywny biały

            nameSword.setTextColor(getResources().getColor(R.color.creme_text));

            // zmieniamy background dla kijanki z mieczem

            swordBack.setVisibility(View.INVISIBLE);

            // zmieniamy kolory progress baru kijanki z mieczem na aktywne
            progressSword.setProgressDrawable(getResources().getDrawable(R.drawable.progress_horizontal));
        }
    }

    public void axeTadpoleVisibility(int value) {
        // progress bar kijanki z toporem
        ProgressBar progressAxe = (ProgressBar) findViewById(R.id.progressB);

        // background View dla kijanki z toporem
        View axeBack = findViewById(R.id.axeTadBack);

        // stworzenie ImageView dla kijanki z toporem
        ImageView axeTadPole = (ImageView) findViewById(R.id.kijankaTasak);

        //TextView "turnDisplay" dla kijanki z toporem
        TextView turnDisplayAxe = (TextView) findViewById(R.id.KTRound);

        // Button coundDownStart dla kijanki z toporem

        Button countDownStartAxe = (Button) findViewById(R.id.KTCountDS);

        // TextView powerAttack dla kijanki z toporem

        TextView powerAttackAxe = (TextView) findViewById(R.id.KTPowerAttack);

        // ImageButton kijanki z toporem

        ImageButton btnAttackAxe = (ImageButton) findViewById(R.id.KTBtnAttack);

        // info TextView dla kijanki z toporem

        TextView infoAxe = (TextView) findViewById(R.id.KTInfoTxt);

        // name TextView kijanki z toporem

        TextView nameAxe = (TextView) findViewById(R.id.KTName);

        // zniknij kijanke z toporem
        if (value == 0) {

            // zmieniamy obraz kijanki z mieczem na nieaktywną kijankę z mieczem
            axeTadPole.setImageResource(R.drawable.tadpole_2_non_active);

            // zmieniamy napis na turnDisplay TextView na "Wait for your turn."
            turnDisplayAxe.setText(R.string.notYourTurn);

            /*
            zmieniamy kolor turnDisplay TextView na nieaktywny zielony
            a kolor czcionki na nieaktywny tekst kolor
            */
            turnDisplayAxe.setBackgroundColor(getResources().getColor(R.color.unactive_green));

            turnDisplayAxe.setTextColor(getResources().getColor(R.color.unactive_white_icon));

            // zmieniamy guzik coundDownStartSword na nieaktywny i kolor tekstu na nieaktywny biały
            countDownStartAxe.setBackgroundResource(R.drawable.my_button_grey);

            countDownStartAxe.setTextColor(getResources().getColor(R.color.unactive_white_icon));

            // zmieniamy kolor tekstu powerAttack TextView kijanki z toporem na szary

            powerAttackAxe.setTextColor(getResources().getColor(R.color.unactive_green));

            // zmienamy attackButton kijanki z toporem na nieaktywny

            btnAttackAxe.setBackgroundResource(R.drawable.my_button_grey);

            // zmieniamy grafike attackButton kijanki z toporem na nieaktywna

            btnAttackAxe.setImageResource(R.drawable.ic_unnactive_miecz);

            // zmieniamy kolory info TextView kijanki z toporem

            infoAxe.setBackgroundResource(R.drawable.my_button_grey);
            infoAxe.setTextColor(getResources().getColor(R.color.unactive_white_icon));

            // zmieniamy kolor imienia na nieaktywny biały

            nameAxe.setTextColor(getResources().getColor(R.color.unactive_white_icon));

            // zmieniamy background dla kijanki z toporem

            axeBack.setVisibility(View.VISIBLE);
            axeBack.setBackgroundResource(R.drawable.background_unnactive_view);

            //zmieiamy kolory progress baru kijanki z toporem na nieaktywne
            progressAxe.setProgressDrawable(getResources().getDrawable(R.drawable.progress_horizontal_unactive));

        }
        // pojaw kijanke z toporem
        else if (value == 1) {
            // zmieniamy obraz kijanki z toporem na aktywną kijankę z toporem
            axeTadPole.setImageResource(R.drawable.tadpole_2);

            // zmieniamy napis na turnDisplay TextView na "Wait for your turn."
            turnDisplayAxe.setText(R.string.YourTurn);

            /*
            zmieniamy kolor turnDisplay TextView na aktywny zielony
            a kolor czcionki na aktywny tekst kolor
            */
            turnDisplayAxe.setBackgroundColor(getResources().getColor(R.color.main_green));

            turnDisplayAxe.setTextColor(getResources().getColor(R.color.creme_text));

            // zmieniamy guzik coundDownStartSword na aktywny i kolor tekstu na aktywny biały
            countDownStartAxe.setBackgroundResource(R.drawable.my_button);

            countDownStartAxe.setTextColor(getResources().getColor(R.color.creme_text));

            // zmieniamy kolor tekstu powerAttack TextView kijanki z toporem na bialy

            powerAttackAxe.setTextColor(getResources().getColor(R.color.creme_text));

            // zmienamy attackButton kijanki z toporem na aktywny

            btnAttackAxe.setBackgroundResource(R.drawable.my_button);

            // zmieniamy grafike attackButton kijanki z toporem na aktywna

            btnAttackAxe.setImageResource(R.drawable.ic_miecz);

            // zmieniamy kolory info TextView kijanki z toporem

            infoAxe.setBackgroundResource(R.drawable.my_button_dark_green);
            infoAxe.setTextColor(getResources().getColor(R.color.creme_text));

            // zmieniamy kolor imienia na aktywny biały

            nameAxe.setTextColor(getResources().getColor(R.color.creme_text));

            // zmieniamy background dla kijanki z toporem

            axeBack.setVisibility(View.INVISIBLE);

            // zmieniamy kolory progress baru kijanki z toporem na aktywne
            progressAxe.setProgressDrawable(getResources().getDrawable(R.drawable.progress_horizontal));
        }
    }
    // Cezary's changes - END
}
