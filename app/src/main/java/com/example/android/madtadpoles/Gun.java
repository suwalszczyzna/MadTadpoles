package com.example.android.madtadpoles;

/**
 * //Created by Damian on 03/12/2017.
 */

class Gun {

    int damage;
    int icon;

    public Gun(){
        damage = 0;
        icon = R.drawable.ic_miecz;
    }

    public Gun(int damage, int icon) {
        this.damage = damage;
        this.icon = icon;
    }
}
