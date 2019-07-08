package ru.explead.breakpassword;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;

import com.appodeal.ads.Appodeal;

import ru.explead.breakpassword.app.App;
import ru.explead.breakpassword.app.Utils;
import ru.explead.breakpassword.fragments.BannerFragment;
import ru.explead.breakpassword.fragments.GameFragment;
import ru.explead.breakpassword.logic.Controller;

public class MainActivity extends AppCompatActivity {

    private static Fragment fragment;

    private static Resources res;
    private static SharedPreferences sPref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Appodeal.disableNetwork(this, "cheetah");
        String appKey = "4b89eb3c54472eb7feb0577f0a463c6fc72415bd402aab9f";
        Appodeal.initialize(this, appKey, Appodeal.REWARDED_VIDEO);

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
    public static void saveSettings(int level, int times) {
        SharedPreferences.Editor editor = sPref.edit();
        if(level == Controller.EASY && times < sPref.getInt(Utils.BEST_EASY, 999999)) {
            editor.putInt(Utils.BEST_EASY, times);
            editor.apply();
        }
        if(level == Controller.MEDIUM && times < sPref.getInt(Utils.BEST_MEDIUM, 999999)) {
            editor.putInt(Utils.BEST_MEDIUM, times);
            editor.apply();
        }
        if(level == Controller.HARD && times < sPref.getInt(Utils.BEST_HARD, 999999)) {
            editor.putInt(Utils.BEST_HARD, times);
            editor.apply();
        }
        if(level == Controller.VERY_HARD && times < sPref.getInt(Utils.BEST_VERY_HARD, 999999)) {
            editor.putInt(Utils.BEST_VERY_HARD, times);
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