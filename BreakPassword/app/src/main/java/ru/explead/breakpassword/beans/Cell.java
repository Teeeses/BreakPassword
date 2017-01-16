package ru.explead.breakpassword.beans;

import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by develop on 13.01.2017.
 */

public class Cell {

    /**
     * Цифра не введена
     */
    public static int NO_ACTIVE = -1;
    /**
     * Значение в клетке
     */
    private int value;
    /**
     * TextView под клетку
     */
    private TextView tvCell;
    /**
     * Layout под клетку
     */
    private RelativeLayout layout;

    public Cell(RelativeLayout layout, TextView tvCell) {
        this.layout = layout;
        this.tvCell = tvCell;
        this.value = NO_ACTIVE;
    }

    public TextView getTvCell() {
        return tvCell;
    }

    public int getValue() {
        return value;
    }

    public RelativeLayout getLayout() {
        return layout;
    }

    public void setValue(int value) {
        this.value = value;
        setText();
    }

    /**
     * Устанавливаем текст внутри клетки
     */
    public void setText() {
        tvCell.setText((value != NO_ACTIVE) ? Integer.toString(value) : "");
    }

    /**
     * Ппибавляем единичку в значение клетки
     */
    public void plusOne() {
        if(value != NO_ACTIVE) {
            value = (value == 9) ? 0 : value + 1;
        } else {
            value = 1;
        }
        setText();
    }
}
