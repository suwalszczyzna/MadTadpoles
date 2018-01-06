package com.example.android.madtadpoles;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class RecoveryActivity extends AppCompatActivity {
    public int mainCounter = 4;
    public int clickNmb = 0;
    public boolean recovered = false;
    private CountDownTimer countDownTimerRec, countDownTimerMem;
    final ArrayList<Integer> iconClicked = new ArrayList<Integer>();
    final ArrayList<Integer> memory = new ArrayList<Integer>();

    // Array of recovery icons -> TEMPORARY
    private final Recovery miecz = new Recovery(0, R.drawable.ic_miecz);
    private final Recovery arc = new Recovery(1, R.drawable.ic_arc);
    private final Recovery sickle = new Recovery(2, R.drawable.ic_sickle);
    private final Recovery baseball = new Recovery(3, R.drawable.ic_baseball);
    private final Recovery bomb = new Recovery(4, R.drawable.ic_bomb);
    private final Recovery bigBomb = new Recovery(5, R.drawable.ic_bigbomb);
    private final Recovery[] recoveries = {miecz, arc, sickle, baseball, bomb, bigBomb};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);

        final TextView recoveryText = (TextView) findViewById(R.id.tv_recovery);
        final Button recoveryStartButton = (Button) findViewById(R.id.btn_recoveryStart);
        final ImageView recoveryIcon = (ImageView) findViewById(R.id.iv_recoveryIcon);
        final Button memoryStartButton = (Button) findViewById(R.id.btn_memoryStart);
        final TextView counter = (TextView) findViewById(R.id.tv_recoveryCounter);
        final ImageView recoveryIcon_0 = (ImageView) findViewById(R.id.iv_recoveryIcon_0);
        final ImageView recoveryIcon_1 = (ImageView) findViewById(R.id.iv_recoveryIcon_1);
        final ImageView recoveryIcon_2 = (ImageView) findViewById(R.id.iv_recoveryIcon_2);
        final ImageView recoveryIcon_3 = (ImageView) findViewById(R.id.iv_recoveryIcon_3);
        final ImageView recoveryIcon_4 = (ImageView) findViewById(R.id.iv_recoveryIcon_4);
        final ImageView recoveryIcon_5 = (ImageView) findViewById(R.id.iv_recoveryIcon_5);
        final TextView healthPoints = (TextView) findViewById(R.id.healthPoints);
        final ProgressBar pb = (ProgressBar) findViewById(R.id.progress);

        // Set views visibility
        recoveryIcon.setVisibility(View.INVISIBLE);
        memoryStartButton.setVisibility(View.INVISIBLE);
        counter.setVisibility(View.INVISIBLE);
        recoveryIcon_0.setVisibility(View.INVISIBLE);
        recoveryIcon_1.setVisibility(View.INVISIBLE);
        recoveryIcon_2.setVisibility(View.INVISIBLE);
        recoveryIcon_3.setVisibility(View.INVISIBLE);
        recoveryIcon_4.setVisibility(View.INVISIBLE);
        recoveryIcon_5.setVisibility(View.INVISIBLE);

        // Fill in memory array list
        for (int i = 0; i < 6; i++)
            memory.add(i);
        // Randomized memory array list
        Collections.shuffle(memory);

        Intent recoveryActivity = getIntent();
        int health = recoveryActivity.getIntExtra("currentPlayerHealth", 0);
        String name = recoveryActivity.getStringExtra("currentPlayerName");
        recoveryText.setText(name + getString(R.string.RecoveryInfo));
        healthPoints.setText(String.valueOf(health));
        pb.setProgress(health);

        // Animation color changing
        if (health >= 60){
            pb.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        } else if ( health >= 30) {
            pb.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
        } else {
            pb.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }

        // Start recovery
        if (recoveryStartButton != null) {
            // Set a click listener on that View
            recoveryStartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Set visibilities
                    recoveryIcon.setVisibility(View.VISIBLE);
                    recoveryStartButton.setBackgroundResource(R.drawable.my_button_grey);
                    recoveryStartButton.setTextColor(getResources().getColor(R.color.creme_text));
                    recoveryStartButton.setEnabled(false);

                    // Start counter
                    countDownTimerRec = new CountDownTimer(2500, 800) {
                        int i = 0;

                        @Override
                        public void onTick(long millisUntilFinished) {
                            recoveryIcon.setImageResource(recoveries[memory.get(i)].getIcon());
                            i++;
                            if (i > 2)
                                i = 2;
                        }

                        @Override
                        public void onFinish() {
                            finishCounterRec();
                        }
                    }.start();
                }
            });
        }

        // Start memory quiz
        if (memoryStartButton != null) {
            // Set a click listener on that View
            memoryStartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Set visibilities
                    memoryStartButton.setBackgroundResource(R.drawable.my_button_grey);
                    memoryStartButton.setTextColor(getResources().getColor(R.color.creme_text));
                    memoryStartButton.setEnabled(false);
                    counter.setVisibility(View.VISIBLE);
                    recoveryIcon_0.setVisibility(View.VISIBLE);
                    recoveryIcon_1.setVisibility(View.VISIBLE);
                    recoveryIcon_2.setVisibility(View.VISIBLE);
                    recoveryIcon_3.setVisibility(View.VISIBLE);
                    recoveryIcon_4.setVisibility(View.VISIBLE);
                    recoveryIcon_5.setVisibility(View.VISIBLE);

                    // Start counter
                    countDownTimerMem = new CountDownTimer(5000, 100) {

                        @Override
                        public void onTick(long millisUntilFinished) {
                            mainCounter = (int) millisUntilFinished / 1000;
                            counter.setText(String.valueOf(mainCounter));
                            IconClicked();
                            if(clickNmb == 3)
                                finishCounterMem();
                        }
                        @Override
                        public void onFinish() {
                            finishCounterMem();
                        }
                    }.start();
                }
            });
        }
    }

    public void finishCounterRec() {
        cancelTimerRec();
        Button recoveryStartButton = (Button) findViewById(R.id.btn_recoveryStart);
        ImageView recoveryIcon = (ImageView) findViewById(R.id.iv_recoveryIcon);
        Button memoryStartButton = (Button) findViewById(R.id.btn_memoryStart);
        recoveryStartButton.setVisibility(View.GONE);
        recoveryIcon.setVisibility(View.GONE);
        memoryStartButton.setVisibility(View.VISIBLE);
    }

    public void finishCounterMem() {
        cancelTimerMem();
        // Compare icons and selected icons
        if(iconClicked.size() > 0 && memory.get(0) == iconClicked.get(0) && memory.get(1) == iconClicked.get(1) && memory.get(2) == iconClicked.get(2)) {
            Log.e("Result", "Recovered");
            recovered = true;
        } else
            recovered = false;
        Log.e("Result", "Not recovered");
        Intent startActivity = new Intent (getApplicationContext(), MainActivity.class);
        startActivity.putExtra("healthRecovered", recovered);
        setResult(Activity.RESULT_OK,startActivity);
        finish();
    }


    public void cancelTimerRec() {
        if (countDownTimerRec != null)
            countDownTimerRec.cancel();
        countDownTimerRec = null;
    }



    public void cancelTimerMem() {
        if (countDownTimerMem != null)
            countDownTimerMem.cancel();
        countDownTimerMem = null;
    }

    public void IconClicked(){
        final ImageView recoveryIcon_0 = (ImageView) findViewById(R.id.iv_recoveryIcon_0);
        final ImageView recoveryIcon_1 = (ImageView) findViewById(R.id.iv_recoveryIcon_1);
        final ImageView recoveryIcon_2 = (ImageView) findViewById(R.id.iv_recoveryIcon_2);
        final ImageView recoveryIcon_3 = (ImageView) findViewById(R.id.iv_recoveryIcon_3);
        final ImageView recoveryIcon_4 = (ImageView) findViewById(R.id.iv_recoveryIcon_4);
        final ImageView recoveryIcon_5 = (ImageView) findViewById(R.id.iv_recoveryIcon_5);

        if(clickNmb < 4) {

            if (recoveryIcon_0 != null) {
                // Set a click listener on that View
                recoveryIcon_0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickNmb++;
                        iconClicked.add(0);
                        recoveryIcon_0.setBackgroundResource(R.drawable.my_button_grey);
                        recoveryIcon_0.setEnabled(false);

                    }
                });
            }

            if (recoveryIcon_1 != null) {
                // Set a click listener on that View
                recoveryIcon_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickNmb++;
                        iconClicked.add(1);
                        recoveryIcon_1.setBackgroundResource(R.drawable.my_button_grey);
                        recoveryIcon_1.setEnabled(false);
                    }
                });
            }

            if (recoveryIcon_2 != null) {
                // Set a click listener on that View
                recoveryIcon_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickNmb++;
                        iconClicked.add(2);
                        recoveryIcon_2.setBackgroundResource(R.drawable.my_button_grey);
                        recoveryIcon_2.setEnabled(false);
                    }
                });
            }

            if (recoveryIcon_3 != null) {
                // Set a click listener on that View
                recoveryIcon_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickNmb++;
                        iconClicked.add(3);
                        recoveryIcon_3.setBackgroundResource(R.drawable.my_button_grey);
                        recoveryIcon_3.setEnabled(false);
                    }
                });
            }

            if (recoveryIcon_4 != null) {
                // Set a click listener on that View
                recoveryIcon_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickNmb++;
                        iconClicked.add(4);
                        recoveryIcon_4.setBackgroundResource(R.drawable.my_button_grey);
                        recoveryIcon_4.setEnabled(false);
                    }
                });
            }

            if (recoveryIcon_5 != null) {
                // Set a click listener on that View
                recoveryIcon_5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickNmb++;
                        iconClicked.add(5);
                        recoveryIcon_5.setBackgroundResource(R.drawable.my_button_grey);
                        recoveryIcon_5.setEnabled(false);
                    }
                });
            }
        }
    }

    /*
    Start HelpActivity
     */
    public void openHelpActivity(View view){
        Intent helpActivity = new Intent (getApplicationContext(), HelpActivity.class);
        startActivity(helpActivity);
    }

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
     * Progress bar animation
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void progressbar(Tadpole tadpole) {
        // Progress bar animation
        setProgressMax(tadpole.getProgressBar(),tadpole.getHitPoints());
        setProgressAnimate(tadpole.getProgressBar(),tadpole.getHealth());
        tadpole.getProgressBar().setProgress(tadpole.getHealth());

        // Animation color changing
        if (tadpole.getHealth() >= tadpole.getHitPoints()*0.6){
            tadpole.getProgressBar().setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        } else if (tadpole.getHealth() >= tadpole.getHitPoints()*0.3) {
            tadpole.getProgressBar().setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
        } else {
            tadpole.getProgressBar().setProgressTintList(ColorStateList.valueOf(Color.RED));
        }
    }
}
