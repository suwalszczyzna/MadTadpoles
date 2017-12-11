package com.example.android.madtadpoles;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Michał Jura on 11.12.2017.
 */

public class Tadpole {

    ImageButton attackButton;
    View counter;
    Button startCount;
    TextView name;
    TextView AttackPoints;
    ProgressBar progressBar;

    // Tadpole stats
    private int hitPoints;
    private int health;
    private int mainCounter;
    private int attackValue;
    private int id;

    public Tadpole() {
    }

    public Tadpole(ImageButton attackButton, View counter, Button startCount, TextView name, TextView AttackPoints, ProgressBar progressBar) {
        this.attackButton = attackButton;
        this.counter = counter;
        this.startCount = startCount;
        this.name = name;
        this.AttackPoints = AttackPoints;
        this.progressBar = progressBar;

    }

    public void setAttackButton(ImageButton attackButton) {
        this.attackButton = attackButton;
    }

    public void setCounter(View counter) {
        this.counter = counter;
    }

    public void setStartCount(Button startCount) {
        this.startCount = startCount;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public void setAttackPoints(TextView attackPoints) {
        AttackPoints = attackPoints;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getMainCounter() {
        return mainCounter;
    }

    public void setMainCounter(int mainCounter) {
        this.mainCounter = mainCounter;
    }

    public int getAttackValue() {
        return attackValue;
    }

    public void setAttackValue(int attackValue) {
        this.attackValue = attackValue;
    }


}
