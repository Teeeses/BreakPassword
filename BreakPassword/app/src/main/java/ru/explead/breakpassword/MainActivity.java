package ru.explead.breakpassword;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import ru.explead.breakpassword.app.App;
import ru.explead.breakpassword.fragments.GameFragment;
import ru.explead.breakpassword.logic.Controller;

public class MainActivity extends AppCompatActivity {


    private static Activity activity;
    private static Fragment fragment;

    private static Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;
        res = this.getResources();

        DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
        App.setWidthScreen(displaymetrics.widthPixels);
        App.setHeightScreen(displaymetrics.heightPixels);

        App.setController(new Controller());

        openGameFragment();
    }

    public void openGameFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment = new GameFragment();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {

        if(GameFragment.isShowKeyboard) {
            if(fragment instanceof GameFragment) {
                ((GameFragment)fragment).closeKeyboard();
            }
        } else {
            if (fragment instanceof GameFragment) {
                //Диалог подтверждения выхода
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Действительно выйти из игры?");
                builder.setPositiveButton("ДА", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
                builder.setNegativeButton("НЕТ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            } else {
                super.onBackPressed();
            }
        }

    }

    public static Activity getActivity() {
        return activity;
    }

    public static Fragment getFragment() {
        return fragment;
    }

    public static Resources getRes() {
        return res;
    }
}