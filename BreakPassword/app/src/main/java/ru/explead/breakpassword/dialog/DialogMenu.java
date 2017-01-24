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

                String str = "";
                if(changeLevel == controller.EASY)
                    str = "easy";
                if(changeLevel == controller.MEDIUM)
                    str = "medium";
                if(changeLevel == controller.HARD)
                    str = "hard";
                if(changeLevel == controller.VERY_HARD)
                    str = "very hard";
                ((MainActivity)activity).sendAction("New Game " + str);
                dismiss();
            }
        });

        btnEasy = (Button) findViewById(R.id.btnEasy);
        btnEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLevel = controller.EASY;
                returnBackgroundColor();
                setBackgroundColor();
            }
        });

        btnMedium = (Button) findViewById(R.id.btnMedium);
        btnMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLevel = controller.MEDIUM;
                returnBackgroundColor();
                setBackgroundColor();

            }
        });

        btnHard = (Button) findViewById(R.id.btnHard);
        btnHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLevel = controller.HARD;
                returnBackgroundColor();
                setBackgroundColor();
            }
        });

        btnVeryHard = (Button) findViewById(R.id.btnVeryHard);
        btnVeryHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLevel = controller.VERY_HARD;
                returnBackgroundColor();
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

        Button btnResults = (Button) findViewById(R.id.btnResults);
        btnResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogResultGame dialog = new DialogResultGame(activity);
                dialog.show();
            }
        });

        setBackgroundColor();

    }

    private void setBackgroundColor() {
        if(changeLevel == controller.EASY) {
            btnEasy.setBackgroundColor(MainActivity.getRes().getColor(android.R.color.holo_green_dark));
        }
        if(changeLevel == controller.MEDIUM) {
            btnMedium.setBackgroundColor(MainActivity.getRes().getColor(android.R.color.holo_green_dark));
        }
        if(changeLevel == controller.HARD) {
            btnHard.setBackgroundColor(MainActivity.getRes().getColor(android.R.color.holo_green_dark));
        }
        if(changeLevel == controller.VERY_HARD) {
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