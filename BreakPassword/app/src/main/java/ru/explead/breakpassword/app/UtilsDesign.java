package ru.explead.breakpassword.app;

import android.content.res.Resources;

import ru.explead.breakpassword.MainActivity;
import ru.explead.breakpassword.R;
import ru.explead.breakpassword.logic.Controller;

/**
 * Created by develop on 17.01.2017.
 */

public class UtilsDesign {

    private int textSize;
    private int marginCells;


    public float getTextSize(int level) {
        Resources res = MainActivity.getRes();
        if(level == Controller.EASY)
            return res.getDimension(R.dimen.text_level_easy);
        if(level  == Controller.HARD)
            return res.getDimension(R.dimen.text_level_hard);
        if(level  == Controller.VERY_HARD)
            return res.getDimension(R.dimen.text_level_very_hard);

        return res.getDimension(R.dimen.text_level_medium);
    }

    public float getMarginCells(int level) {
        Resources res = MainActivity.getRes();
        if(level == Controller.EASY)
            return res.getDimension(R.dimen.level_margin_easy);
        if(level  == Controller.HARD)
            return res.getDimension(R.dimen.level_margin_hard);
        if(level  == Controller.VERY_HARD)
            return res.getDimension(R.dimen.level_margin_very_hard);

        return res.getDimension(R.dimen.level_margin_medium);
    }

}
