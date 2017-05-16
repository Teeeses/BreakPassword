package ru.explead.breakpassword.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import ru.explead.breakpassword.MainActivity;
import ru.explead.breakpassword.R;
import ru.explead.breakpassword.app.Utils;

/**
 * Created by develop on 18.01.2017.
 */

public class DialogResultGame extends Dialog {

    private Context context;

    private TextView tvEasyResult;
    private TextView tvMediumResult;
    private TextView tvHardResult;
    private TextView tvVeryHardResult;

    public DialogResultGame(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_result);

        tvEasyResult = (TextView) findViewById(R.id.tvEasyResult);
        tvMediumResult = (TextView) findViewById(R.id.tvMediumResult);
        tvHardResult = (TextView) findViewById(R.id.tvHardResult);
        tvVeryHardResult = (TextView) findViewById(R.id.tvVeryHardResult);

        int easy = MainActivity.getPref().getInt(Utils.BEST_EASY, 0);
        int medium = MainActivity.getPref().getInt(Utils.BEST_MEDIUM, 0);
        int hard = MainActivity.getPref().getInt(Utils.BEST_HARD, 0);
        int veryHard = MainActivity.getPref().getInt(Utils.BEST_VERY_HARD, 0);

        if(easy != 0) {
            tvEasyResult.setText(String.format(MainActivity.getRes().getString(R.string.easy_level), easy));
        } else {
            tvEasyResult.setText(MainActivity.getRes().getString(R.string.empty_easy_level));
        }
        if(medium != 0) {
            tvMediumResult.setText(String.format(MainActivity.getRes().getString(R.string.medium_level), medium));
        } else {
            tvMediumResult.setText(MainActivity.getRes().getString(R.string.empty_medium_level));
        }
        if(hard != 0) {
            tvHardResult.setText(String.format(MainActivity.getRes().getString(R.string.hard_level), hard));
        } else {
            tvHardResult.setText(MainActivity.getRes().getString(R.string.empty_hard_level));
        }
        if(veryHard != 0) {
            tvVeryHardResult.setText(String.format(MainActivity.getRes().getString(R.string.very_hard_level), veryHard));
        } else {
            tvVeryHardResult.setText(MainActivity.getRes().getString(R.string.empty_very_hard_level));
        }

    }
}
