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

    public DialogMenu(Activity activity, Controller controller) {
        super(activity);
        this.activity = activity;
        this.controller = controller;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_menu);

        Button btnRestart = (Button) findViewById(R.id.btnRestart);
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((GameFragment)MainActivity.getFragment()).onRestart();
                dismiss();
            }
        });

        Button btnEasy = (Button) findViewById(R.id.btnEasy);
        btnEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.setLevel(3);
            }
        });

        Button btnMedium = (Button) findViewById(R.id.btnMedium);
        btnMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.setLevel(4);
            }
        });

        Button btnHard = (Button) findViewById(R.id.btnHard);
        btnHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.setLevel(5);
            }
        });

        Button btnVeryHard = (Button) findViewById(R.id.btnVeryHard);
        btnVeryHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.setLevel(6);
            }
        });

    }
}