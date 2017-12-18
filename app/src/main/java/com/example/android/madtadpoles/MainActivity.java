package com.example.android.madtadpoles;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
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


    private boolean mIsBound = false;
    private BackgroundMusic mServ;
    private ServiceConnection Scon =new ServiceConnection(){

        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ = ((BackgroundMusic.ServiceBinder)binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService(){
        bindService(new Intent(this,BackgroundMusic.class),
                Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService()
    {
        if(mIsBound)
        {
            unbindService(Scon);
            mIsBound = false;
        }
    }


    // creating tadpoles
    private Tadpole KM = new Tadpole(100, 4, 0);
    private Tadpole KT = new Tadpole(100, 4, 1);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tadpoles);

        // Assign Views corresponding to tadpoles
        KM.setHealthPoints((TextView) findViewById(R.id.kijankaMieczHP));
        KM.setAttackButton((ImageButton) findViewById(R.id.KMBtnAttack));
        KM.setStartCount((Button) findViewById(R.id.startCountKM));
        KM.setName((TextView) findViewById(R.id.KMName));
        KM.setLabelCounter((TextView) findViewById(R.id.labelCounterKM));
        KM.setAttackPoints((TextView) findViewById(R.id.kijankaMieczPts));
        KM.setProgressBar((ProgressBar) findViewById(R.id.progressA));
        KM.setHealthPoints((TextView) findViewById(R.id.kijankaMieczHP));

        KT.setHealthPoints((TextView) findViewById(R.id.kijankaTasakHP));
        KT.setAttackButton((ImageButton) findViewById(R.id.KTBtnAttack));
        KT.setStartCount((Button) findViewById(R.id.startCountKT));
        KT.setName((TextView) findViewById(R.id.KTName));
        KT.setLabelCounter((TextView) findViewById(R.id.labelCounterKT));
        KT.setAttackPoints((TextView) findViewById(R.id.kijankaTasakPts));
        KT.setProgressBar((ProgressBar) findViewById(R.id.progressB));
        KT.setHealthPoints((TextView) findViewById(R.id.kijankaTasakHP));

        if(!mIsBound) {
            doBindService();
            Intent music = new Intent();
            music.setClass(this, BackgroundMusic.class);
            startService(music);
        }
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
        progressbar(KM);  // --> Ola's new code
        progressbar(KT);  // --> Ola's new code


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
                disabledCounterStart(KM, true);
                disabledBtnAttack(KM, false);

                countDownTimer = new CountDownTimer(4000,100){ // Ola's new code: countDownTimerKM + modifications
                    @SuppressLint("SetTextI18n")
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
                            disabledBtnAttack(KM, true);
                            attackValue = guns[i].damage;
                            KM.getAttackButton().setImageResource(guns[i].icon);
                            KT.getHealthPoints().setText("" + KM.attack(KT, guns[i]));
                            if(KT.getHealth()<=0) {
                                winner(KM);
                            }
                            progressbar(KT);
                            afterAttack(KT);
                        }
                        // .. increment index to show new gun
                        i++;
                        if ( i > 6)
                            i = 0;
                    }
                    @Override
                    // Attack not pressed and countdown finished - reset counter
                    public void onFinish() {
                        afterAttack(KT);

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
                disabledCounterStart(KT, true);
                disabledBtnAttack(KT, false);

               countDownTimer = new CountDownTimer(4000,100){ // Ola's code: countDownTimerKT =
                    @SuppressLint("SetTextI18n")
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
                            disabledBtnAttack(KT, true);
                            attackValue = guns[i].damage;
                            KT.getAttackButton().setImageResource(guns[i].icon);
                            KM.getHealthPoints().setText("" + KT.attack(KM, guns[i]));
                            if(KM.getHealth()<=0){
                                winner(KT);
                            }
                            progressbar(KM);
                            afterAttack(KM);
                        }
                        // .. increment index to show new gun
                        i++;
                        if ( i > 6)
                            i = 0;
                    }
                    @Override
                    public void onFinish() {
                        // Attack not pressed and countdown finished - reset counter
                        afterAttack(KM);
                    }
                }.start();
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService();
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mServ!=null)
        mServ.pauseMusic();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mServ!=null)
            mServ.resumeMusic();

    }

    /*
            @Override
            protected void onStop() {
                super.onStop();
        mServ.pauseMusic();
                doUnbindService();
            }
        */
    /*
            * Finish KM countdown - Attack button pressed or countdown is finished
            * give attacked tadpole as parameter
            */
    @SuppressLint("SetTextI18n")
    private void afterAttack(final Tadpole tadpole ){
        // Reset countdown timer
        cancelTimer();
       int delay = 0;
        // If Attack button was pressed introduce 1s delay and display attack points
        if (isAttackHitted) { // Ola's new code
            delay = 1000;
            tadpole.getAttackPoints().setVisibility(View.VISIBLE);
            tadpole.getAttackPoints().setAlpha(0f); // Damian
            tadpole.getAttackPoints().animate().alpha(1f).setDuration(300); // Damian
            tadpole.getAttackPoints().setText("-"+ String.valueOf(attackValue));
        }

        // Ola's new code ..
        // Update labels, enable 2nd player Start button, disable this player attack button..
        // .. change player, reset isAttackHitted
       new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                updateLabels();
                if (tadpole.getId()==0) {
                    disabledCounterStart(KM, false);
                    disabledBtnAttack(KM, true);
                }else {
                    disabledCounterStart(KT, false);
                    disabledBtnAttack(KT, true);
                }
                whoseTurn(tadpole.getId());
                isAttackHitted = false;
                tadpole.getAttackPoints().setVisibility(View.INVISIBLE);
                tadpole.getAttackPoints().setAlpha(0f); // Damian
            }
        }, delay);
        // .. Ola's new code
    }

    // Ola's code
    /*
    *  Countdown timer reset
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
    @SuppressLint("SetTextI18n")
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
        if (KT.getHealth() <= 0 || KM.getHealth() <= 0) {
            changePlayerColors(3);
        } else if (i == 0) {
            changePlayerColors(0);
            disabledBtnAttack(KM, true);
            disabledBtnAttack(KT, true);
        } else if (i == 1) {
            changePlayerColors(1);
            disabledBtnAttack(KM, true);
            disabledBtnAttack(KT, true);
        }
    }
    /**
     * Enabling/disabling KM Attack button, change icon
     * @param disabled true false
     */
    private void disabledBtnAttack(Tadpole tadpole, boolean disabled){

        if (disabled){
            tadpole.getAttackButton().setBackgroundResource(R.drawable.my_button_grey);
            tadpole.getAttackButton().setEnabled(false);
            tadpole.getAttackButton().setImageResource(R.drawable.ic_unnactive_miecz);
        }else {
            tadpole.getAttackButton().setEnabled(true);
            tadpole.getAttackButton().setBackgroundResource(R.drawable.my_button);
            tadpole.getAttackButton().setImageResource(R.drawable.ic_miecz);
        }
    }

    /**
     * Enabling/disabling KM Start button, change icon
     * @param disabled true false
     */
    private void disabledCounterStart(Tadpole tadpole, boolean disabled){
            if (disabled){
            tadpole.getStartCount().setBackgroundResource(R.drawable.my_button_grey);
            tadpole.getStartCount().setEnabled(false);
            tadpole.getStartCount().setTextColor(getResources().getColor(R.color.unactive_white_icon));
        } else {
            tadpole.getStartCount().setBackgroundResource(R.drawable.my_button);
            tadpole.getStartCount().setEnabled(true);
            tadpole.getStartCount().setTextColor(getResources().getColor(R.color.creme_text));
        }
    }

    // ********************************** Damian's code end

    // *******************************************************
    // ********************************** MisQLak's code start
    // *******************************************************
    // Alert about winner, game restart or finish
    private void winner(final Tadpole tadpole) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setTitle(getString(R.string.winnerIs) + " " + tadpole.getName().getText() + "!");

        alertDialogBuilder.setMessage(R.string.playAgain);
        alertDialogBuilder.setPositiveButton(R.string.yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(MainActivity.this, R.string.newGameToast, Toast.LENGTH_LONG).show();
                        Intent startIntent = new Intent(MainActivity.this, MainActivity.class);  // --> Ola's new code
                        startIntent.putExtra("whoseTurn", tadpole.getId());
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
    private void progressbar(Tadpole tadpole) {
        // Progress bar animation
        setProgressMax(tadpole.getProgressBar(),100);
        setProgressAnimate(tadpole.getProgressBar(),tadpole.getHealth());
        tadpole.getProgressBar().setProgress(tadpole.getHealth());

        // Animation color changing
        if (tadpole.getHealth() >= 60){
            tadpole.getProgressBar().setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        } else if (tadpole.getHealth() < 60 && tadpole.getHealth() >= 30) {
            tadpole.getProgressBar().setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
        } else if (tadpole.getHealth() < 30){
            tadpole.getProgressBar().setProgressTintList(ColorStateList.valueOf(Color.RED));
        }
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
