package com.example.android.madtadpoles;

import android.animation.ObjectAnimator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements Dialog.DialogListener{

    public int mainCounterKM = 3;
    public int mainCounterKT = 3;
    boolean isCounterKMworking, isCounterKTworking;

    Button helpButton;
    TextView textViewKM, textViewKT;
    Gun[] guns = new Gun[3];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tadpoles);
        // ********************************** Damian's code start

        // Left Tadpole: i = 0; Right Tadpole: i = 1;
        whoseTurn(0);
        // ************* //


        final TextView labelCounterKM = (TextView) findViewById(R.id.labelCounterKM);
        final TextView labelCounterKT = (TextView) findViewById(R.id.labelCounterKT);
        final Button startCountKM = (Button) findViewById(R.id.startCountKM);
        final Button startCountKT = (Button) findViewById(R.id.startCountKT);


        updateLabels();

        textViewKM = (TextView) findViewById(R.id.KMName);
        textViewKT = (TextView) findViewById(R.id.KTName);


        //Click listener to open Change Names dialog
        helpButton = (Button) findViewById(R.id.helpButton);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        //************************ Main counter for KM tadpole
        startCountKM.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                updateLabels();
                disabledKMCounterStart(true);
                disabledKMBtnAttack(false);




                new CountDownTimer(4200,1000){
                    @Override
                    public void onTick(long l) {

                        // tutaj piszemy co się dzieje w trakcie odliczania
                        mainCounterKM = (int)(l/1000)-1;
                        updateLabels();

                    }

                    @Override
                    public void onFinish() {

                        // tutaj piszemy co sie ma wydazyc po zakonczeniu odliczania
                        disabledKMBtnAttack(true);
                        disabledKMCounterStart(false);
                        mainCounterKM = 3;
                        updateLabels();

                        whoseTurn(1);

                    }
                }.start();
            }
        });


        //********************* Main counter for KT tadpole
        startCountKT.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                updateLabels();
                disabledKTCounterStart(true);
                disabledKTBtnAttack(false);

                new CountDownTimer(4200,1000){
                    @Override
                    public void onTick(long l) {
                        // tutaj piszemy co się dzieje w trakcie odliczania

                        mainCounterKT = (int)(l/1000)-1;
                        updateLabels();

                    }

                    @Override
                    public void onFinish() {
                        // tutaj piszemy co sie ma wydazyc po zakonczeniu odliczania

                        disabledKTCounterStart(false);
                        mainCounterKT = 3;
                        updateLabels();
                        disabledKTBtnAttack(true);
                        whoseTurn(0);
                    }
                }.start();
            }
        });

    }

    public void updateLabels(){
        TextView labelCounterKM = (TextView) findViewById(R.id.labelCounterKM);
        TextView labelCounterKT = (TextView) findViewById(R.id.labelCounterKT);
        labelCounterKM.setText(String.valueOf(mainCounterKM));
        labelCounterKT.setText(String.valueOf(mainCounterKT));
    }
    public void openDialog(){
        Dialog dialog = new Dialog();
        dialog.show(getSupportFragmentManager(),"dialog");
    }

    @Override
    public void applyTexts(String km, String kt) {
        textViewKM.setText(km);
        textViewKT.setText(kt);

        textViewKM.getText();
    }

    public void whoseTurn(int i){

        if(i == 0){
            changePlayerColors(0);
            disabledKMBtnAttack(true);
            disabledKTBtnAttack(true);
        }else if (i == 1){
            changePlayerColors(1);
            disabledKMBtnAttack(true);
            disabledKTBtnAttack(true);
        }

    }


    public void disabledKMBtnAttack(boolean disabled){

        ImageButton KMBtnAttack = (ImageButton) findViewById(R.id.KMBtnAttack);

        if (disabled){
            KMBtnAttack.setBackgroundResource(R.drawable.my_button_grey);
            KMBtnAttack.setEnabled(false);
            KMBtnAttack.setImageResource(R.drawable.ic_unnactive_miecz);
        }else {

            KMBtnAttack.setEnabled(true);
            KMBtnAttack.setBackgroundResource(R.drawable.my_button);
            KMBtnAttack.setImageResource(R.drawable.ic_miecz);
        }
    }

    public void disabledKTBtnAttack(boolean disabled){

        ImageButton KTBtnAttack = (ImageButton) findViewById(R.id.KTBtnAttack);

        if (disabled){
            KTBtnAttack.setBackgroundResource(R.drawable.my_button_grey);
            KTBtnAttack.setEnabled(false);
            KTBtnAttack.setImageResource(R.drawable.ic_unnactive_miecz);
        }else if (!disabled) {

            KTBtnAttack.setEnabled(true);
            KTBtnAttack.setBackgroundResource(R.drawable.my_button);
            KTBtnAttack.setImageResource(R.drawable.ic_miecz);
        }
    }

    public void disabledKMCounterStart(boolean disabled){

        Button startCountKM = (Button) findViewById(R.id.startCountKM);

        if (disabled){
            startCountKM.setBackgroundResource(R.drawable.my_button_grey);
            startCountKM.setEnabled(false);
            startCountKM.setTextColor(getResources().getColor(R.color.unactive_white_icon));
        } else {

            startCountKM.setBackgroundResource(R.drawable.my_button);
            startCountKM.setEnabled(true);
            startCountKM.setTextColor(getResources().getColor(R.color.creme_text));

        }
    }

    public void disabledKTCounterStart(boolean disabled){

        Button startCountKT = (Button) findViewById(R.id.startCountKT);

        if (disabled){
            startCountKT.setBackgroundResource(R.drawable.my_button_grey);
            startCountKT.setEnabled(false);
            startCountKT.setTextColor(getResources().getColor(R.color.unactive_white_icon));
        } else {

            startCountKT.setBackgroundResource(R.drawable.my_button);
            startCountKT.setEnabled(true);
            startCountKT.setTextColor(getResources().getColor(R.color.creme_text));

        }
    }

    public void createGuns(){

        guns[0] = new Gun(15,R.drawable.ic_miecz);
        guns[1] = new Gun(25,R.drawable.ic_arc);
        guns[2] = new Gun(35, R.drawable.ic_sickle);


    }

    // ********************************** Damian's code end




    /// ********************************** Mateusz's code start

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

// mnożymy max HP *100 żeby animacja zawsze była widoczna

    private void setProgressMax(ProgressBar pb, int max) {
        pb.setMax(max * 100);
    }
    // ustawiamy wartośc animacji
    private void setProgressAnimate(ProgressBar pb, int progressTo)
    {
        ObjectAnimator animation = ObjectAnimator.ofInt(pb, "progress", pb.getProgress(), progressTo * 100);
        animation.setDuration(500);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }

    /*
    ustawiamy paski zycia graczy A I B ()w każdym dałem ifa żeby
    po spadku ponizej 0 paski się odnawiały - dla testów :D )
    */

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void progressbarA() {

        ProgressBar playerA = findViewById(R.id.progressA);
        healthKM -= randomNumber();

        // włączamy animację dla gracza A

        setProgressMax(playerA,100);
        setProgressAnimate(playerA,healthKM);
        if (healthKM < 0) {
            healthKM = 100;
        }

        playerA.setProgress(healthKM);

        //zmieniamy kolory

        if (healthKM>=60){
            playerA.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        } else if (healthKM<60&&healthKM>=30) {
            playerA.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
        } else if (healthKM<30){
            playerA.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void progressbarB() {

        ProgressBar playerA = findViewById(R.id.progressB);
        healthKT -= randomNumber();

        // włączamy animację dla gracza B

        setProgressMax(playerA,100);
        setProgressAnimate(playerA, healthKT);

        if (healthKT < 0) {
            healthKT = 100;
        }

        playerA.setProgress(healthKT);


        //zmiana kolorów

        if (healthKT>=60){
            playerA.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        } else if (healthKT<60&&healthKT>=30) {
            playerA.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
        } else if (healthKT<30){
            playerA.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }
    }

    // dajemy metodę do wyświetlania HP przy kijanie miecz

    public void kijanaMiecz (int aktualneHP){

        TextView miecz = findViewById(R.id.kijankaMieczHP);
        miecz.setText(""+aktualneHP);
        if (healthKM<=0){
            miecz.setText(""+0);
        }
    }

    // dajemy metodę do wyświetlania HP przy kijanie toporek

    public void kijanaTopor (int aktualneHP){

        TextView miecz = findViewById(R.id.kijankaTasakHP);
        miecz.setText(""+aktualneHP);
        if (healthKT<=0){
            miecz.setText(""+0);
        }
    }

    // wciskanie przycisków ataku dla obu graczy

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void attackPlayerA(View view) {

        progressbarB();
        kijanaTopor(healthKT);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void attackPlayerB(View view) {

        progressbarA();
        kijanaMiecz(healthKM);
    }

    // ********************************** Mateusz's code end



    // **********************************Cezary's code start


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

        ProgressBar progressSword = (ProgressBar) findViewById(R.id.progressA);
        View swordBack = findViewById(R.id.swordTadBack);
        ImageView swordTadPole = (ImageView) findViewById(R.id.kijankaMiecz);
        TextView turnDisplaySword = (TextView) findViewById(R.id.KMRound);
        Button countDownStartSword = (Button) findViewById(R.id.startCountKM);
        TextView powerAttackSword = (TextView) findViewById(R.id.labelCounterKM);
        ImageButton btnAttackSword = (ImageButton) findViewById(R.id.KMBtnAttack);
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
            countDownStartSword.setEnabled(false);
            countDownStartSword.setTextColor(getResources().getColor(R.color.unactive_white_icon));

            // zmieniamy kolor tekstu powerAttack TextView kijanki z mieczem na szary

            powerAttackSword.setTextColor(getResources().getColor(R.color.unactive_green));

            // zmienamy attackButton kijanki z mieczem na nieaktywny

            btnAttackSword.setBackgroundResource(R.drawable.my_button_grey);
            btnAttackSword.setEnabled(false);
            // zmieniamy grafike attackButton kijanki z mieczem na nieaktywna

            btnAttackSword.setImageResource(R.drawable.ic_unnactive_miecz);


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
            countDownStartSword.setEnabled(true);
            countDownStartSword.setTextColor(getResources().getColor(R.color.creme_text));

            // zmieniamy kolor tekstu powerAttack TextView kijanki z mieczem na bialy

            powerAttackSword.setTextColor(getResources().getColor(R.color.creme_text));

            // zmienamy attackButton kijanki z mieczem na aktywny
            btnAttackSword.setEnabled(true);
            btnAttackSword.setBackgroundResource(R.drawable.my_button);

            // zmieniamy grafike attackButton kijanki z mieczem na aktywna

            btnAttackSword.setImageResource(R.drawable.ic_miecz);



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

        Button countDownStartAxe = (Button) findViewById(R.id.startCountKT);

        // TextView powerAttack dla kijanki z toporem

        TextView powerAttackAxe = (TextView) findViewById(R.id.labelCounterKT);

        // ImageButton kijanki z toporem

        ImageButton btnAttackAxe = (ImageButton) findViewById(R.id.KTBtnAttack);



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
            countDownStartAxe.setEnabled(false);
            countDownStartAxe.setBackgroundResource(R.drawable.my_button_grey);
            countDownStartAxe.setTextColor(getResources().getColor(R.color.unactive_white_icon));

            // zmieniamy kolor tekstu powerAttack TextView kijanki z toporem na szary

            powerAttackAxe.setTextColor(getResources().getColor(R.color.unactive_green));

            // zmienamy attackButton kijanki z toporem na nieaktywny
            // zmieniamy grafike attackButton kijanki z toporem na nieaktywna
            btnAttackAxe.setEnabled(false);
            btnAttackAxe.setBackgroundResource(R.drawable.my_button_grey);
            btnAttackAxe.setImageResource(R.drawable.ic_unnactive_miecz);


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
            countDownStartAxe.setEnabled(true);
            countDownStartAxe.setBackgroundResource(R.drawable.my_button);
            countDownStartAxe.setTextColor(getResources().getColor(R.color.creme_text));

            // zmieniamy kolor tekstu powerAttack TextView kijanki z toporem na bialy

            powerAttackAxe.setTextColor(getResources().getColor(R.color.creme_text));

            // zmienamy attackButton kijanki z toporem na aktywny
            btnAttackAxe.setEnabled(true);
            btnAttackAxe.setBackgroundResource(R.drawable.my_button);

            // zmieniamy grafike attackButton kijanki z toporem na aktywna

            btnAttackAxe.setImageResource(R.drawable.ic_miecz);


            // zmieniamy kolor imienia na aktywny biały

            nameAxe.setTextColor(getResources().getColor(R.color.creme_text));

            // zmieniamy background dla kijanki z toporem

            axeBack.setVisibility(View.INVISIBLE);

            // zmieniamy kolory progress baru kijanki z toporem na aktywne
            progressAxe.setProgressDrawable(getResources().getDrawable(R.drawable.progress_horizontal));
        }
    }
    // ********************************** Cezary's code end
}
