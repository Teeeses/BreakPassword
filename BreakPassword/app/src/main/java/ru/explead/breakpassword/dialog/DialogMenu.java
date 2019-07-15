package ru.explead.breakpassword.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import ru.explead.breakpassword.MainActivity;
import ru.explead.breakpassword.R;
import ru.explead.breakpassword.beans.Complexity;
import ru.explead.breakpassword.fragments.GameFragment;
import ru.explead.breakpassword.logic.Controller;

/**
 * Created by develop on 16.01.2017.
 */

public class DialogMenu extends Dialog {

    private Context context;
    private Controller controller;

    private Button btnEasy;
    private Button btnMedium;
    private Button btnHard;
    private Button btnVeryHard;

    private Complexity changeLevel;


    public DialogMenu(Activity context, Controller controller) {
        super(context);
        this.context = context;
        this.controller = controller;
        changeLevel = controller.getLevel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_menu);
        setCancelable(false);

        Button btnRestart = findViewById(R.id.btnRestart);
        btnRestart.setOnClickListener(view -> {
            controller.setLevel(changeLevel);
            ((GameFragment)MainActivity.getFragment()).onRestart();
            dismiss();
        });

        btnEasy = findViewById(R.id.btnEasy);
        btnEasy.setOnClickListener(view -> {
            changeLevel = Complexity.EASY;
            returnBackgroundColor();
            setBackgroundColor();
        });

        btnMedium = findViewById(R.id.btnMedium);
        btnMedium.setOnClickListener(view -> {
            changeLevel = Complexity.MEDIUM;
            returnBackgroundColor();
            setBackgroundColor();

        });

        btnHard = findViewById(R.id.btnHard);
        btnHard.setOnClickListener(view -> {
            changeLevel = Complexity.HARD;
            returnBackgroundColor();
            setBackgroundColor();
        });

        btnVeryHard = findViewById(R.id.btnVeryHard);
        btnVeryHard.setOnClickListener(view -> {
            changeLevel = Complexity.VERY_HARD;
            returnBackgroundColor();
            setBackgroundColor();
        });

        Button btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(view -> dismiss());

        Button btnResults = findViewById(R.id.btnResults);
        btnResults.setOnClickListener(view -> {
            DialogResultGame dialog = new DialogResultGame(context);
            dialog.show();
        });

        setBackgroundColor();
    }

    private void setBackgroundColor() {
        if(changeLevel == Complexity.EASY) {
            btnEasy.setBackgroundColor(MainActivity.getRes().getColor(android.R.color.holo_green_dark));
        }
        if(changeLevel == Complexity.MEDIUM) {
            btnMedium.setBackgroundColor(MainActivity.getRes().getColor(android.R.color.holo_green_dark));
        }
        if(changeLevel == Complexity.HARD) {
            btnHard.setBackgroundColor(MainActivity.getRes().getColor(android.R.color.holo_green_dark));
        }
        if(changeLevel == Complexity.VERY_HARD) {
            btnVeryHard.setBackgroundColor(MainActivity.getRes().getColor(android.R.color.holo_green_dark));
        }
    }

    private void returnBackgroundColor() {
        btnEasy.setBackgroundColor(MainActivity.getRes().getColor(R.color.colorPrimary));
        btnMedium.setBackgroundColor(MainActivity.getRes().getColor(R.color.colorPrimary));
        btnHard.setBackgroundColor(MainActivity.getRes().getColor(R.color.colorPrimary));
        btnVeryHard.setBackgroundColor(MainActivity.getRes().getColor(R.color.colorPrimary));
    }
}