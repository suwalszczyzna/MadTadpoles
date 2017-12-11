package com.example.android.madtadpoles;

import android.view.View;

/**
 * Created by Michał Jura on 11.12.2017.
 */

public class Tadpole {

    View attackButton;
    View counter;



    // Tadpole stats
    private int hitPoints;
    private int mainCounter;
    private int attackValue;

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
