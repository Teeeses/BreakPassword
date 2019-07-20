package ru.explead.breakpassword.app;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;

import ru.explead.breakpassword.MainActivity;

/**
 * Created by develop on 18.01.2017.
 */

public class Utils {

    public final static String BEST_EASY = "best_easy";
    public final static String BEST_MEDIUM = "best_medium";
    public final static String BEST_HARD ="best_hard";
    public final static String BEST_VERY_HARD = "best_very_hard";

    public static final String APP_PREFERENCES = "settings";

    public static Typeface getTypeFaceLevel(Context context) {
        return Typeface.createFromAsset(context.getAssets(),"fonts/level_personal.ttf");
    }
}
