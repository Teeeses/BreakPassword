package ru.explead.breakpassword.logic;

import android.content.res.Resources;

import java.util.Random;

import ru.explead.breakpassword.MainActivity;
import ru.explead.breakpassword.R;

/**
 * Created by develop on 18.01.2017.
 */

public class UtilsWinText {


    public static String getWinText(int level, int times) {
        Random rand = new Random();
        Resources res = MainActivity.getRes();

        if(level == Controller.EASY) {
            if(times == 1) {
                return res.getString(R.string.win_1);
            }
            if(times == 2 || times == 3) {
                return res.getString(R.string.win_2);
            }
            if(times >= 4 && times <= 8) {
                int count = rand.nextInt(2);
                if(count == 0)
                    return res.getString(R.string.win_3);
                else
                    return res.getString(R.string.win_4);

            }
            if(times > 8) {
                int count = rand.nextInt(3);
                if(count == 0)
                    return res.getString(R.string.win_8);
                else if(count == 1)
                    return res.getString(R.string.win_9);
                else
                    return res.getString(R.string.win_10);
            }
        }


        if(level == Controller.MEDIUM) {
            if(times == 1) {
                return res.getString(R.string.win_1);
            }
            if(times == 2 || times == 3) {
                return res.getString(R.string.win_2);
            }
            if(times >= 4 && times <= 8) {
                int count = rand.nextInt(2);
                if(count == 0)
                    return res.getString(R.string.win_3);
                else
                    return res.getString(R.string.win_4);

            }
            if(times > 8) {
                int count = rand.nextInt(3);
                if(count == 0)
                    return res.getString(R.string.win_8);
                else if(count == 1)
                    return res.getString(R.string.win_9);
                else
                    return res.getString(R.string.win_10);
            }
        }


        if(level == Controller.HARD) {
            if(times == 1) {
                return res.getString(R.string.win_1);
            }
            if(times == 2 || times == 3) {
                return String.format(res.getString(R.string.win_2));
            }
            if(times >= 4 && times <= 8) {
                int count = rand.nextInt(2);
                if(count == 0)
                    return res.getString(R.string.win_3);
                else
                    return res.getString(R.string.win_4);

            }
            if(times > 8) {
                int count = rand.nextInt(3);
                if(count == 0)
                    return res.getString(R.string.win_8);
                else if(count == 1)
                    return res.getString(R.string.win_9);
                else
                    return res.getString(R.string.win_10);
            }
        }


        if(level == Controller.VERY_HARD) {
            if(times == 1) {
                return res.getString(R.string.win_1);
            }
            if(times == 2 || times == 3) {
                return res.getString(R.string.win_2);
            }
            if(times >= 4 && times <= 10) {
                int count = rand.nextInt(2);
                if(count == 0)
                    return res.getString(R.string.win_3);
                else
                    return res.getString(R.string.win_4);

            }
            if(times > 10) {
                int count = rand.nextInt(3);
                if(count == 0)
                    return res.getString(R.string.win_8);
                else if(count == 1)
                    return res.getString(R.string.win_9);
                else
                    return res.getString(R.string.win_10);
            }
        }
        return res.getString(R.string.win_0);
    }
}
