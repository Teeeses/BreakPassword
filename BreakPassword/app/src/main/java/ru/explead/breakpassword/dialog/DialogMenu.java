package ru.explead.breakpassword.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import ru.explead.breakpassword.MainActivity;
import ru.explead.breakpassword.R;
import ru.explead.breakpassword.fragments.GameFragment;
import ru.explead.breakpassword.logic.Controller;

/**
 * Created by develop on 16.01.2017.
 */

public class DialogMenu extends Dialog {

    private Activity activity;
    private Controller controller;

    private Button btnEasy;
    private Button btnMedium;
    private Button btnHard;
    private Button btnVeryHard;

    private int changeLevel;


    public DialogMenu(Activity activity, Controller controller) {
        super(activity);
        this.activity = activity;
        this.controller = controller;
        changeLevel = controller.getLevel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_menu);
        setCancelable(false);

        Button btnRestart = (Button) findViewById(R.id.btnRestart);
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.setLevel(changeLevel);
                ((GameFragment)MainActivity.getFragment()).onRestart();
                dismiss();
            }
        });

        btnEasy = (Button) findViewById(R.id.btnEasy);
        btnEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLevel = controller.EASY;
                returnBackgrounfColor();
                setBackgroundColor();
            }
        });

        btnMedium = (Button) findViewById(R.id.btnMedium);
        btnMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLevel = controller.MEDIUM;
                returnBackgrounfColor();
                setBackgroundColor();

            }
        });

        btnHard = (Button) findViewById(R.id.btnHard);
        btnHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLevel = controller.HARD;
                returnBackgrounfColor();
                setBackgroundColor();
            }
        });

        btnVeryHard = (Button) findViewById(R.id.btnVeryHard);
        btnVeryHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLevel = controller.VERY_HARD;
                returnBackgrounfColor();
                setBackgroundColor();
            }
        });

        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        setBackgroundColor();

    }

    private void setBackgroundColor() {
        if(controller.getLevel() == controller.EASY) {
            btnEasy.setBackgroundColor(MainActivity.getRes().getColor(android.R.color.holo_green_dark));
        }
        if(controller.getLevel() == controller.MEDIUM) {
            btnMedium.setBackgroundColor(MainActivity.getRes().getColor(android.R.color.holo_green_dark));
        }
        if(controller.getLevel() == controller.HARD) {
            btnHard.setBackgroundColor(MainActivity.getRes().getColor(android.R.color.holo_green_dark));
        }
        if(controller.getLevel() == controller.VERY_HARD) {
            btnVeryHard.setBackgroundColor(MainActivity.getRes().getColor(android.R.color.holo_green_dark));
        }
    }

    private void returnBackgrounfColor() {
        btnEasy.setBackgroundColor(MainActivity.getRes().getColor(R.color.colorPrimary));
        btnMedium.setBackgroundColor(MainActivity.getRes().getColor(R.color.colorPrimary));
        btnHard.setBackgroundColor(MainActivity.getRes().getColor(R.color.colorPrimary));
        btnVeryHard.setBackgroundColor(MainActivity.getRes().getColor(R.color.colorPrimary));
    }
}