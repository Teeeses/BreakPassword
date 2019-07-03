package ru.explead.breakpassword.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import ru.explead.breakpassword.MainActivity;
import ru.explead.breakpassword.R;
import ru.explead.breakpassword.app.Utils;
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

    private int changeLevel;


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
        btnRestart.setTypeface(Utils.getTypeFaceLevel(context));
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.setLevel(changeLevel);
                ((GameFragment)MainActivity.getFragment()).onRestart();

                String str = "";
                if(changeLevel == Controller.EASY)
                    str = "easy";
                if(changeLevel == Controller.MEDIUM)
                    str = "medium";
                if(changeLevel == Controller.HARD)
                    str = "hard";
                if(changeLevel == Controller.VERY_HARD)
                    str = "very hard";
                dismiss();
            }
        });

        btnEasy = findViewById(R.id.btnEasy);
        btnEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLevel = Controller.EASY;
                returnBackgroundColor();
                setBackgroundColor();
            }
        });

        btnMedium = findViewById(R.id.btnMedium);
        btnMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLevel = Controller.MEDIUM;
                returnBackgroundColor();
                setBackgroundColor();

            }
        });

        btnHard = findViewById(R.id.btnHard);
        btnHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLevel = Controller.HARD;
                returnBackgroundColor();
                setBackgroundColor();
            }
        });

        btnVeryHard = findViewById(R.id.btnVeryHard);
        btnVeryHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLevel = Controller.VERY_HARD;
                returnBackgroundColor();
                setBackgroundColor();
            }
        });

        Button btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setTypeface(Utils.getTypeFaceLevel(context));
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        Button btnResults = findViewById(R.id.btnResults);
        btnResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogResultGame dialog = new DialogResultGame(context);
                dialog.show();
            }
        });

        setBackgroundColor();
        setTypeFace();

    }

    private void setTypeFace() {
        btnEasy.setTypeface(Utils.getTypeFaceLevel(context));
        btnMedium.setTypeface(Utils.getTypeFaceLevel(context));
        btnHard.setTypeface(Utils.getTypeFaceLevel(context));
        btnVeryHard.setTypeface(Utils.getTypeFaceLevel(context));
    }

    private void setBackgroundColor() {
        if(changeLevel == Controller.EASY) {
            btnEasy.setBackgroundColor(MainActivity.getRes().getColor(android.R.color.holo_green_dark));
        }
        if(changeLevel == Controller.MEDIUM) {
            btnMedium.setBackgroundColor(MainActivity.getRes().getColor(android.R.color.holo_green_dark));
        }
        if(changeLevel == Controller.HARD) {
            btnHard.setBackgroundColor(MainActivity.getRes().getColor(android.R.color.holo_green_dark));
        }
        if(changeLevel == Controller.VERY_HARD) {
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