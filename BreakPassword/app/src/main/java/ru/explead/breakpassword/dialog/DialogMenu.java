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

/**
 * Created by develop on 16.01.2017.
 */

public class DialogMenu extends Dialog {

    private Activity activity;

    public DialogMenu(Activity activity) {
        super(activity);
        this.activity = activity;
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

    }
}