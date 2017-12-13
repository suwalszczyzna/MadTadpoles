package com.example.android.madtadpoles;

import android.animation.ObjectAnimator;
import android.content.Intent;
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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;
import android.os.Handler; // Ola's new code


public class MainActivity extends AppCompatActivity implements Dialog.DialogListener{

    private boolean isAttackHitted = false;
    private int i = 0;
    private int healthKM = 100;
    private int healthKT = 100;
    private int attackValue = 0;
    private CountDownTimer countDownTimer; // Ola's new code


    private final Gun miecz = new Gun(3,R.drawable.ic_miecz);
    private final Gun arc = new Gun(6,R.drawable.ic_arc);
    private final Gun sickle = new Gun(15, R.drawable.ic_sickle);
    private final Gun axe = new Gun(10,R.drawable.ic_axe);
    private final Gun baseball = new Gun(5,R.drawable.ic_baseball);
    private final Gun bomb = new Gun(30,R.drawable.ic_bomb);
    private final Gun bigBomb = new Gun(50,R.drawable.ic_bigbomb);
    private final Gun[] guns = {miecz, arc, sickle, axe, baseball, bomb, bigBomb};


    // creating tadpoles
    Tadpole KM = new Tadpole(100, 4, 0);
    Tadpole KT = new Tadpole(100, 4, 1);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tadpoles);

        // Assign Views corresponding to tadpoles
        KM.setHealthPoints((TextView) findViewById(R.id.kijankaMieczHP));
        KM.setAttackButton((ImageButton) findViewById(R.id.KMBtnAttack));
        KM.setStartCount((Button) findViewById(R.id.startCountKM));
        KM.setName((TextView) findViewById(R.id.KMName));
        KM.setLabelCounter((TextView) findViewById(R.id.labelCounterKM));

        KT.setHealthPoints((TextView) findViewById(R.id.kijankaTasakHP));
        KT.setAttackButton((ImageButton) findViewById(R.id.KTBtnAttack));
        KT.setStartCount((Button) findViewById(R.id.startCountKT));
        KT.setName((TextView) findViewById(R.id.KTName));
        KT.setLabelCounter((TextView) findViewById(R.id.labelCounterKT));




        // ******************************************************
        // ********************************** Damian's code start
        // ******************************************************

        // Left Tadpole: i = 0; Right Tadpole: i = 1;
        whoseTurn(0);
        // ************* //

        // After starting activity take previous value of whoseTurn -> winner starts new game
        Bundle extras = getIntent().getExtras(); // --> Ola's new code
        if (extras != null){
            int turn = extras.getInt("whoseTurn");
            whoseTurn(turn);
        }

        updateLabels();
        progressbarA();  // --> Ola's new code
        progressbarB();  // --> Ola's new code


       // KM Attack button ClickListener
        // On attack button click set isAttackHitted = true
        KM.getAttackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAttackHitted = true;
            }
        });

        // KT Attack button ClickListener
        // On attack button click set isAttackHitted = true
        KT.getAttackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAttackHitted = true;
            }
        });

        //************************ Main counter for KM tadpole (left)
        // On Start button enable Attack button and disable other players buttons
        // Create guns
        KM.getStartCount().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                updateLabels();
                disabledKMCounterStart(true);
                disabledKMBtnAttack(false);

                countDownTimer = new CountDownTimer(4000,100){ // Ola's new code: countDownTimerKM + modifications
                    @Override
                    // On each counter tick..
                    public void onTick(long millisUntilFinished) {
                        // .. display remaining time
                        int counter = (int)(millisUntilFinished / 1000);
                        updateLabels(KM, counter);
                        // .. display gun
                        KM.getAttackButton().setImageResource(guns[i].icon);
                        // Check if Attack button is pressed
                        // If yes, disable it, show chosen gun, update progress bar, reset counter
                        if (isAttackHitted) {
                            disabledKMBtnAttack(true);
                            attackValue = guns[i].damage;
                            KM.getAttackButton().setImageResource(guns[i].icon);
                            attackPlayerA();
                            onFinishKM();
                        }
                        // .. increment index to show new gun
                        i++;
                        if ( i > 6)
                            i = 0;
                    }
                    @Override
                    // Attack not pressed and countdown finished - reset counter
                    public void onFinish() {
                        onFinishKM();

                    }
                }.start();
            }
        });

        //********************* Main counter for KT tadpole
        // On Start button enable Attack button and disable other players buttons
        // Create guns
        KT.getStartCount().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                updateLabels();
                disabledKTCounterStart(true);
                disabledKTBtnAttack(false);

               countDownTimer = new CountDownTimer(4000,100){ // Ola's code: countDownTimerKT =
                    @Override
                    // On each counter tick..
                    public void onTick(long millisUntilFinished) {
                        // .. display remaining time
                        int counter = (int)(millisUntilFinished/1000);
                        updateLabels(KT, counter);
                        // .. display gun
                        KT.getAttackButton().setImageResource(guns[i].icon);
                        // Check if Attack button is pressed
                        // If yes, disable it, show chosen gun, update progress bar, reset counter
                        if (isAttackHitted){
                            disabledKTBtnAttack(true);
                            attackValue = guns[i].damage;
                            KT.getAttackButton().setImageResource(guns[i].icon);
                            attackPlayerB();
                            onFinishKT();
                        }
                        // .. increment index to show new gun
                        i++;
                        if ( i > 6)
                            i = 0;
                    }
                    @Override
                    public void onFinish() {
                        // Attack not pressed and countdown finished - reset counter
                        onFinishKT();
                    }
                }.start();
            }
        });

    }

    /*
    * Finish KM countdown - Attack button pressed or countdown is finished
    */
    private void onFinishKM(){
        // Reset countdown timer
        cancelTimer();
        final TextView AttackPoints =  findViewById(R.id.kijankaTasakPts); // NOWY KOD
        int delay = 0;
        // If Attack button was pressed introduce 1s delay and display attack points
        if (isAttackHitted) { // Ola's new code
            delay = 1000;
            AttackPoints.setVisibility(View.VISIBLE);
            AttackPoints.setAlpha(0f); // Damian
            AttackPoints.animate().alpha(1f).setDuration(300); // Damian
            AttackPoints.setText("-"+ String.valueOf(attackValue));
        }

        // Ola's new code ..
        // Update labels, enable 2nd player Start button, disable this player attack button..
        // .. change player, reset isAttackHitted
       new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                updateLabels();
                disabledKMCounterStart(false);
                disabledKMBtnAttack(true);
                whoseTurn(1);
                isAttackHitted = false;
                AttackPoints.setVisibility(View.INVISIBLE);
                AttackPoints.setAlpha(0f); // Damian
            }
        }, delay);
        // .. Ola's new code
    }

    /*
    * Finish KT countdown - Attack button pressed or countdown is finished
    */
    private void onFinishKT(){
        // Reset countdown timer
        cancelTimer();
        final TextView AttackPoints = findViewById(R.id.kijankaMieczPts);
        int delay = 0;
        // If Attack button was pressed introduce 1s delay and display attack points
        if (isAttackHitted) {  // Ola's new code
            delay = 1000;
            AttackPoints.setVisibility(View.VISIBLE);
            AttackPoints.setAlpha(0f); // Damian
            AttackPoints.animate().alpha(1f).setDuration(300); // Damian
            AttackPoints.setText("-"+ String.valueOf(attackValue));
        }
        // Ola's new code ..
       new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                updateLabels();
                disabledKTCounterStart(false);
                disabledKTBtnAttack(true);
                whoseTurn(0);
                isAttackHitted = false;
                AttackPoints.setVisibility(View.INVISIBLE); // Ola's new code
                AttackPoints.setAlpha(0f); // Damian
            }
        }, delay);
        // .. Ola's new code
    }

    // Ola's code
    /*
    * KM Countdown timer reset
    */
    private void cancelTimer() {
        if(countDownTimer!=null)
            countDownTimer.cancel();
        countDownTimer = null;

    }


    /*
    * Display KM and KT counters value
    */
    private void updateLabels(){

        KM.getLabelCounter().setText(String.valueOf(KM.getMainCounter()));
        KT.getLabelCounter().setText(String.valueOf(KT.getMainCounter()));
    }
    private void updateLabels(Tadpole tadpole, int counter){
        tadpole.getLabelCounter().setText(""+counter);
    }

    /*
    * Open change name dialog
    */
    public void openDialog(View view){
        Dialog dialog = new Dialog();
        dialog.show(getSupportFragmentManager(),"dialog");
    }

    /*
    Start HelpActivity
     */
    public void openHelpActivity(View view){
        Intent helpActivity = new Intent (getApplicationContext(), HelpActivity.class);
        startActivity(helpActivity);
    }
    /*
    Start InfoActivity
     */
    public void openInfoActivity(View view){
        Intent infoActivity = new Intent (getApplicationContext(), InfoActivity.class);
        startActivity(infoActivity);
    }

    /**
     * Change player name
     * @param km is 1st (left) player's name
     * @param kt is 1st (left) player's name
     */
    @Override
    public void applyTexts(String km, String kt) {
        KM.getName().setText(km);
        KT.getName().setText(kt);

        KM.getName().getText();
    }

    /**
     * Change player
     * @param i : 0 for 1st (left) player, 1 for 2nd (right) player
     */
    private void whoseTurn(int i) {
        if (healthKT <= 0 || healthKM <= 0) {
            changePlayerColors(3);
        } else if (i == 0) {
            changePlayerColors(0);
            disabledKMBtnAttack(true);
            disabledKTBtnAttack(true);
        } else if (i == 1) {
            changePlayerColors(1);
            disabledKMBtnAttack(true);
            disabledKTBtnAttack(true);
        }
    }

    /**
     * Enabling/disabling KM Attack button, change icon
     * @param disabled true false
     */
    private void disabledKMBtnAttack(boolean disabled){
        ImageButton KMBtnAttack = findViewById(R.id.KMBtnAttack);
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

    /**
     * Enabling/disabling KM Attack button, change icon
     * @param disabled true false
     */
    private void disabledKTBtnAttack(boolean disabled){
        ImageButton KTBtnAttack = findViewById(R.id.KTBtnAttack);
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

    /**
     * Enabling/disabling KM Start button, change icon
     * @param disabled true false
     */
    private void disabledKMCounterStart(boolean disabled){
        Button startCountKM = findViewById(R.id.startCountKM);
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

    /**
     * Enabling/disabling KT Start button, change icon
     * @param disabled true false
     */
    private void disabledKTCounterStart(boolean disabled){
        Button startCountKT = findViewById(R.id.startCountKT);
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



    // ********************************** Damian's code end

    // *******************************************************
    // ********************************** MisQLak's code start
    // *******************************************************

    // Alert about winner, game restart or finish
    private void winnerKM () {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setTitle("The winner is " + KM.getName().getText() + "!");

        alertDialogBuilder.setMessage(R.string.playAgain);
        alertDialogBuilder.setPositiveButton(R.string.yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(MainActivity.this, R.string.newGameToast, Toast.LENGTH_LONG).show();
                        Intent startIntent = new Intent(MainActivity.this, MainActivity.class);  // --> Ola's new code
                        startIntent.putExtra("whoseTurn", 0);
                        startActivity(startIntent);
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    // Alert about winner, game restart or finish
    private void winnerKT () {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setTitle("The winner is " + KT.getName().getText() + "!");

        alertDialogBuilder.setMessage(R.string.playAgain);
        alertDialogBuilder.setPositiveButton(R.string.yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(MainActivity.this, R.string.newGameToast, Toast.LENGTH_LONG).show();
                        //recreate();
                        Intent startIntent = new Intent(MainActivity.this, MainActivity.class);  // --> Ola's new code
                        startIntent.putExtra("whoseTurn", 1);
                        startActivity(startIntent);
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    // ********************************** MisQLak's code end

    // *******************************************************
    // ********************************** Mateusz's code start
    // *******************************************************

    /**
     * Set progress bar max
     * @param pb progressbar
     * @param max maximum value
     */
    private void setProgressMax(ProgressBar pb, int max) {
        pb.setMax(max * 100);
    }

    /**
     * Set progress bar animation values
     * @param pb progressbar
     * @param progressTo progress bar animation value
     */
    private void setProgressAnimate(ProgressBar pb, int progressTo)
    {
        ObjectAnimator animation = ObjectAnimator.ofInt(pb, "progress", pb.getProgress(), progressTo * 100);
        animation.setDuration(500);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }

    /**
     * Left progress bar animation
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void progressbarA() {
        ProgressBar playerA = findViewById(R.id.progressA);
        healthKM -= attackValue;
        // Progress bar animation
        setProgressMax(playerA,100);
        setProgressAnimate(playerA,healthKM);
        playerA.setProgress(healthKM);

        // Animation color changing
        if (healthKM >= 60){
            playerA.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        } else if (healthKM < 60 && healthKM >= 30) {
            playerA.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
        } else if (healthKM < 30){
            playerA.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }
    }

    /**
     * Right progress bar animation
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void progressbarB() {

        ProgressBar playerA = findViewById(R.id.progressB);
        healthKT -= attackValue;

        // Progress bar animation
        setProgressMax(playerA,100);
        setProgressAnimate(playerA, healthKT);
        playerA.setProgress(healthKT);

        // Animation color changing
        if (healthKT >= 60){
            playerA.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        } else if (healthKT < 60 && healthKT >= 30) {
            playerA.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
        } else if (healthKT < 30){
            playerA.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }
    }

    /**
     * Display actual health point
     * @param actualHP actual health point
     */
    private void kijanaMiecz(int actualHP){
        TextView healthPoints = findViewById(R.id.kijankaMieczHP);
        healthPoints.setText("" + actualHP);
        if (healthKM <= 0){
            winnerKT();
            healthPoints.setText("" + 0);
        }
    }

    /**
     * Display actual health point
     * @param actualHP actual health point
     */
    private void kijanaTopor(int actualHP){
        TextView healthPoints = findViewById(R.id.kijankaTasakHP);
        healthPoints.setText("" + actualHP);
        if (healthKT <= 0){
            winnerKM();
            healthPoints.setText("" + 0);
        }
    }

    /**
     * On attack button press fill in progress bar and update health points
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void attackPlayerA() {
        progressbarB();
        kijanaTopor(healthKT);
    }

    /**
     * On attack button press fill in progress bar and update health points
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void attackPlayerB() {
        progressbarA();
        kijanaMiecz(healthKM);
    }

    // ********************************** Mateusz's code end


    // ******************************************************
    // ********************************** Cezary's code start
    // ******************************************************

    /**
     * Display/hide player depending on turn
     * @param parameter 0-3
     */
    private void changePlayerColors(int parameter) {
        // Left KM player's turn
        if (parameter == 0){
            // Display left KM player
            swordTadpoleVisibility(1);
            // Hide right KT player
            axeTadpoleVisibility(0);
        }
        // Right KT player's turn
        else if (parameter == 1){
            // Hide left KM player
            swordTadpoleVisibility(0);
            // Display right KT player
            axeTadpoleVisibility(1);
        }
        // Display both players
        else if (parameter == 2){
            // Display left KM player
            swordTadpoleVisibility(1);
            // Display right KT player
            axeTadpoleVisibility(1);
        }
        // Hide both players
        else if (parameter == 3){
            // Hide left KM player
            swordTadpoleVisibility(0);
            // Hide right KT player
            axeTadpoleVisibility(0);
        }
    }

    /**
     * Left KM player visibility
     * @param value hide = 0, display = 1
     */
    private void swordTadpoleVisibility(int value) {

        ProgressBar progressSword = findViewById(R.id.progressA);     // Progress bar
        View swordBack = findViewById(R.id.swordTadBack);                           // Icon background
        ImageView swordTadPole = findViewById(R.id.kijankaMiecz);       // Player's icon
        TextView turnDisplaySword = findViewById(R.id.KMRound);          // Turn display text
        Button countDownStartSword = findViewById(R.id.startCountKM);      // Start button
        TextView powerAttackSword = findViewById(R.id.labelCounterKM);   // Countdown value
        ImageButton btnAttackSword = findViewById(R.id.KMBtnAttack);  // Attack button
        TextView nameSword = findViewById(R.id.KMName);                  // Player's name

        // Hide left KM player
        if (value == 0) {
            // Change player's icon
            swordTadPole.setImageResource(R.drawable.tadpole_non_active);

            // Change turnDisplay TextView text, color and font color
            turnDisplaySword.setText(R.string.notYourTurn);
            turnDisplaySword.setBackgroundColor(getResources().getColor(R.color.unactive_green));
            turnDisplaySword.setTextColor(getResources().getColor(R.color.unactive_white_icon));

            // Disable Start button, change color and font color
            countDownStartSword.setEnabled(false);
            countDownStartSword.setBackgroundResource(R.drawable.my_button_grey);
            countDownStartSword.setTextColor(getResources().getColor(R.color.unactive_white_icon));

            // Change countdown value color
            powerAttackSword.setTextColor(getResources().getColor(R.color.unactive_green));

            // Disable Attack button, change color and icon
            btnAttackSword.setEnabled(false);
            btnAttackSword.setBackgroundResource(R.drawable.my_button_grey);
            btnAttackSword.setImageResource(R.drawable.ic_unnactive_miecz);

            // Change name color
            nameSword.setTextColor(getResources().getColor(R.color.unactive_white_icon));

            // Change background of player's main icon
            swordBack.setVisibility(View.VISIBLE);
            swordBack.setBackgroundResource(R.drawable.background_unnactive_view);

            // Change progress bar background color
            progressSword.setProgressDrawable(getResources().getDrawable(R.drawable.progress_horizontal_unactive));
        }
        // Display left KM player
        else if (value == 1) {
            // Change player's icon
            swordTadPole.setImageResource(R.drawable.tadpole);

            // Change turnDisplay TextView text, color and font color
            turnDisplaySword.setText(R.string.YourTurn);
            turnDisplaySword.setBackgroundColor(getResources().getColor(R.color.main_green));
            turnDisplaySword.setTextColor(getResources().getColor(R.color.creme_text));

            // Enable Start button, change color and font color
            countDownStartSword.setEnabled(true);
            countDownStartSword.setBackgroundResource(R.drawable.my_button);
            countDownStartSword.setTextColor(getResources().getColor(R.color.creme_text));

            // Change countdown value color
            powerAttackSword.setTextColor(getResources().getColor(R.color.creme_text));

            // Enable Attack button, change color and icon
            btnAttackSword.setEnabled(true);
            btnAttackSword.setBackgroundResource(R.drawable.my_button);
            btnAttackSword.setImageResource(R.drawable.ic_miecz);

            // Change name color
            nameSword.setTextColor(getResources().getColor(R.color.creme_text));

            // Change background of player's main icon
            swordBack.setVisibility(View.INVISIBLE);

            // Change progress bar background color
            progressSword.setProgressDrawable(getResources().getDrawable(R.drawable.progress_horizontal));
        }
    }
    /**
     * Right KT player visibility
     * @param value hide = 0, display = 1
     */
    private void axeTadpoleVisibility(int value) {
        ProgressBar progressAxe = findViewById(R.id.progressB);    // Progress bar
        View axeBack = findViewById(R.id.axeTadBack);                            // Icon background
        ImageView axeTadPole = findViewById(R.id.kijankaTasak);      // Player's icon
        TextView turnDisplayAxe = findViewById(R.id.KTRound);         // Turn display text
        Button countDownStartAxe = findViewById(R.id.startCountKT);     // Start button
        TextView powerAttackAxe = findViewById(R.id.labelCounterKT);  // Countdown value
        ImageButton btnAttackAxe = findViewById(R.id.KTBtnAttack); // Attack button
        TextView nameAxe = findViewById(R.id.KTName);                 // Player's name

        // Hide right KT player
        if (value == 0) {
            // Change player's icon
            axeTadPole.setImageResource(R.drawable.tadpole_2_non_active);

            // Change turnDisplay TextView text, color and font color
            turnDisplayAxe.setText(R.string.notYourTurn);
            turnDisplayAxe.setBackgroundColor(getResources().getColor(R.color.unactive_green));
            turnDisplayAxe.setTextColor(getResources().getColor(R.color.unactive_white_icon));

            // Disable Start button, change color and font color
            countDownStartAxe.setEnabled(false);
            countDownStartAxe.setBackgroundResource(R.drawable.my_button_grey);
            countDownStartAxe.setTextColor(getResources().getColor(R.color.unactive_white_icon));

            // Change countdown value color
            powerAttackAxe.setTextColor(getResources().getColor(R.color.unactive_green));

            // Disable Attack button, change color and icon
            btnAttackAxe.setEnabled(false);
            btnAttackAxe.setBackgroundResource(R.drawable.my_button_grey);
            btnAttackAxe.setImageResource(R.drawable.ic_unnactive_miecz);

            // Change name color
            nameAxe.setTextColor(getResources().getColor(R.color.unactive_white_icon));

            // Change background of player's main icon
            axeBack.setVisibility(View.VISIBLE);
            axeBack.setBackgroundResource(R.drawable.background_unnactive_view);

            // Change progress bar background color
            progressAxe.setProgressDrawable(getResources().getDrawable(R.drawable.progress_horizontal_unactive));

        }
        // Display right KT player
        else if (value == 1) {
            // Change player's icon
            axeTadPole.setImageResource(R.drawable.tadpole_2);

            // Change turnDisplay TextView text, color and font color
            turnDisplayAxe.setText(R.string.YourTurn);
            turnDisplayAxe.setBackgroundColor(getResources().getColor(R.color.main_green));
            turnDisplayAxe.setTextColor(getResources().getColor(R.color.creme_text));

            // Enable Start button, change color and font color
            countDownStartAxe.setEnabled(true);
            countDownStartAxe.setBackgroundResource(R.drawable.my_button);
            countDownStartAxe.setTextColor(getResources().getColor(R.color.creme_text));

            // Change countdown value color
            powerAttackAxe.setTextColor(getResources().getColor(R.color.creme_text));

            // Enable Attack button, change color and icon
            btnAttackAxe.setEnabled(true);
            btnAttackAxe.setBackgroundResource(R.drawable.my_button);
            btnAttackAxe.setImageResource(R.drawable.ic_miecz);

            // Change countdown value color
            nameAxe.setTextColor(getResources().getColor(R.color.creme_text));

            // Change background of player's main icon
            axeBack.setVisibility(View.INVISIBLE);

            // Change progress bar background color
            progressAxe.setProgressDrawable(getResources().getDrawable(R.drawable.progress_horizontal));
        }
    }

    // ********************************** Cezary's code end
}
