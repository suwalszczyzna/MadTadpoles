package com.example.android.madtadpoles;


import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Michał Jura on 11.12.2017.
 */

class Tadpole {
    //Tagpole views
   private ImageButton attackButton;
   private View counter;
   private Button startCount;
   private TextView name;
   private TextView AttackPoints;
   private ProgressBar progressBar;
   private TextView healthPoints;
   private TextView labelCounter;

    // Tadpole stats
    private int hitPoints;
    private int health;
    private int mainCounter;
    private int id;

    public Tadpole() {
    }

    public Tadpole(int hitPoints, int mainCounter, int id) {
        this.hitPoints = hitPoints;
        this.mainCounter = mainCounter;
        this.id = id;
        this.health = hitPoints;
    }

    public void attack(Tadpole tadpole, Gun gun) {

        int attackValue;
        attackValue = gun.damage;
        tadpole.takeDamage(attackValue);
    }

    private void takeDamage(int damage) {
        if (health <= 0)
            this.health -= damage;

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

    public TextView getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(TextView healthPoints) {
        this.healthPoints = healthPoints;
    }

    public ImageButton getAttackButton() {
        return attackButton;
    }

    public View getCounter() {
        return counter;
    }

    public Button getStartCount() {
        return startCount;
    }

    public TextView getName() {
        return name;
    }

    public TextView getAttackPoints() {
        return AttackPoints;
    }

    public ProgressBar getProgressBar() {
        return progressBar;

    }

    public TextView getLabelCounter() {
        return labelCounter;
    }

    public void setLabelCounter(TextView labelCounter) {
        this.labelCounter = labelCounter;
    }
}
