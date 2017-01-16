package ru.explead.breakpassword.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;

import ru.explead.breakpassword.R;

/**
 * Created by develop on 16.01.2017.
 */

public class DialogSettings extends Dialog {

    private Activity activity;

    public DialogSettings(Activity activity) {
        super(activity);
        this.activity = activity;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_settings);
        setCancelable(false);

    }
}