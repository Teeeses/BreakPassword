package ru.explead.breakpassword.logic;

import android.widget.TextView;

/**
 * Created by develop on 13.01.2017.
 */

public class Cell {

    public static int NO_ACTIVE = -1;
    private int value;
    private TextView tvCell;

    public Cell(TextView tvCell) {
        this.tvCell = tvCell;
        this.value = NO_ACTIVE;
    }

    public TextView getTvCell() {
        return tvCell;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        setText();
    }

    public void setText() {
        tvCell.setText((value != NO_ACTIVE) ? Integer.toString(value) : "");
    }

    public void plusOne() {
        if(value != NO_ACTIVE) {
            value = (value == 9) ? 0 : value + 1;
        } else {
            value = 1;
        }
        setText();
    }
}
