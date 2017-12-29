package com.example.android.madtadpoles;

/**
 * Created by MielcarekA on 12/26/2017.
 */

public class Recovery {
    /** Index */
    private int mIndex;

    /** Icon */
    private int mIcon;

    /**
     * Constructs a new Recovery with initial values.
     */
    public Recovery(int index, int icon){
        mIndex = index;
        mIcon = icon;
    }

    /**
     * Gets the index.
     *
     * @return index.
     */
    public int getIndex() {
        return mIndex;
    }

    /**
     * Gets the icon.
     *
     * @return icon.
     */
    public int getIcon() {
        return mIcon;
    }
}
