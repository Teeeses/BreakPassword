package ru.explead.breakpassword;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import ru.explead.breakpassword.app.App;
import ru.explead.breakpassword.app.Utils;
import ru.explead.breakpassword.beans.Complexity;
import ru.explead.breakpassword.fragments.BannerFragment;
import ru.explead.breakpassword.fragments.GameFragment;

public class MainActivity extends AppCompatActivity {

    private static Fragment fragment;

    private static Resources res;
    private static SharedPreferences sPref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        res = this.getResources();
        sPref = getSharedPreferences(Utils.APP_PREFERENCES, MODE_PRIVATE);

        DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
        App.setWidthScreen(displaymetrics.widthPixels);
        App.setHeightScreen(displaymetrics.heightPixels);

        openBannerFragment();
    }

    public void openGameFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment = new GameFragment();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

    public void openBannerFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment = new BannerFragment();
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
                builder.setPositiveButton("ДА", (dialog, which) -> {
                    dialog.dismiss();
                    finish();
                });
                builder.setNegativeButton("НЕТ", (dialog, which) -> dialog.dismiss());
                AlertDialog alert = builder.create();
                alert.show();
            } else {
                super.onBackPressed();
                fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
            }
        }

    }

    /**
     * Сохранение настроек
     * @param level - уровень
     * @param times - количество попыток
     */
    public static void saveSettings(Complexity level, int times) {
        saveSettingsChoice(level, times, Utils.BEST_EASY, Complexity.EASY);
        saveSettingsChoice(level, times, Utils.BEST_EASY, Complexity.MEDIUM);
        saveSettingsChoice(level, times, Utils.BEST_EASY, Complexity.HARD);
        saveSettingsChoice(level, times, Utils.BEST_EASY, Complexity.VERY_HARD);
    }

    public static void saveSettingsChoice(Complexity level, int times, final String saver, Complexity forLevel) {
        if(level == forLevel && times < sPref.getInt(saver, Integer.MAX_VALUE)) {
            SharedPreferences.Editor editor = sPref.edit();
            editor.putInt(saver, times);
            editor.apply();
        }
    }

    public static SharedPreferences getPref() {
        return sPref;
    }

    public static Fragment getFragment() {
        return fragment;
    }

    public static Resources getRes() {
        return res;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}