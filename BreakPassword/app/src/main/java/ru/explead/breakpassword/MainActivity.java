package ru.explead.breakpassword;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.appodeal.ads.Appodeal;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import ru.explead.breakpassword.app.App;
import ru.explead.breakpassword.app.Utils;
import ru.explead.breakpassword.fragments.AdvertiseFragment;
import ru.explead.breakpassword.fragments.BannerFragment;
import ru.explead.breakpassword.fragments.GameFragment;
import ru.explead.breakpassword.logic.Controller;

public class MainActivity extends AppCompatActivity {


    private static Activity activity;
    private static Fragment fragment;

    private static Resources res;
    private static SharedPreferences sPref;

    private Tracker mTracker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Appodeal.disableNetwork(this, "cheetah");
        String appKey = "4b89eb3c54472eb7feb0577f0a463c6fc72415bd402aab9f";
        Appodeal.initialize(this, appKey, Appodeal.INTERSTITIAL | Appodeal.REWARDED_VIDEO);

        activity = this;
        res = this.getResources();
        sPref = getSharedPreferences(Utils.APP_PREFERENCES, MODE_PRIVATE);

        // Получение экземпляра общедоступного счетчика.
        App application = (App) getApplication();
        mTracker = application.getDefaultTracker();

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

    public void openAdvertiseFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment = new AdvertiseFragment();
        transaction.add(R.id.fragmentContainer, fragment);
        transaction.setCustomAnimations(R.anim.scale_enter, R.anim.scale_exit);
        transaction.addToBackStack(null);
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

    public static Activity getActivity() {
        return activity;
    }

    public static Fragment getFragment() {
        return fragment;
    }

    public static Resources getRes() {
        return res;
    }

    @Override
    protected void onResume() {
        mTracker.setScreenName("Image~" + "MainActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        super.onResume();
    }

    public Tracker getTracker() {
        return mTracker;
    }

    public void sendAction(String str) {
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction(str)
                .build());
    }
}