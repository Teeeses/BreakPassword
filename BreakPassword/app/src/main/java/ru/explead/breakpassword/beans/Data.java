package ru.explead.breakpassword.beans;

/**
 * Created by develop on 16.01.2017.
 */

public class Data {

    /**
     * Количество цифр на своих местах
     */
    private int onPlace;
    /**
     * Колоичество цифр которые совпали
     */
    private int matches;

    private String probable;


    public Data(int onPlace, int matches, String probable) {
        this.onPlace = onPlace;
        this.matches = matches;
        this.probable = probable;
    }

    public int getOnPlace() {
        return onPlace;
    }

    public int getMatches() {
        return matches;
    }

    public String getProbable() {
        return probable;
    }
}
