package ru.explead.breakpassword.beans;

import android.widget.Button;

/**
 * Created by develop on 17.01.2017.
 */

public class KeyButton {

    private int value;
    private Button button;

    public KeyButton(int value, Button button) {
        this.value = value;
        this.button = button;
    }

    public int getValue() {
        return value;
    }

    public Button getButton() {
        return button;
    }
}
