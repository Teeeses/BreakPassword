package ru.explead.breakpassword.app;

import android.content.res.Resources;

import ru.explead.breakpassword.MainActivity;
import ru.explead.breakpassword.R;
import ru.explead.breakpassword.beans.Complexity;

/**
 * Created by develop on 17.01.2017.
 */

public class UtilsDesign {

    public float getTextSize(Complexity level) {
        Resources res = MainActivity.getRes();
        if(level == Complexity.EASY)
            return res.getDimension(R.dimen.more_standard_text);
        if(level == Complexity.MEDIUM)
            return res.getDimension(R.dimen.standard_text);
        if(level  == Complexity.HARD)
            return res.getDimension(R.dimen.more_mini_text);
        if(level  == Complexity.VERY_HARD)
            return res.getDimension(R.dimen.mini_text);

        return res.getDimension(R.dimen.giant_text);
    }
}
