package ru.explead.breakpassword.logic;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by develop on 13.01.2017.
 */

public class Controller {


    private int numberCells;
    private ArrayList<Integer> password = new ArrayList<>();
    private Random random = new Random();

    public Controller() {
        numberCells = 100;
        generatePassword(numberCells);
        for(int i = 0; i < password.size(); i++) {
            Log.d("TAG", Integer.toString(password.get(i)));
        }
    }

    public void generatePassword(int numberCells) {
        this.numberCells = numberCells;
        for(int i = 0; i < numberCells; i++) {
            int number;
            do {
                number = random.nextInt(9);
            } while (password.contains(number));
            password.add(number);
        }
    }

    public ArrayList<Integer> getPassword() {
        return password;
    }

    public int getNumberCells() {
        return numberCells;
    }

    public void setNumberCells(int numberCells) {
        this.numberCells = numberCells;
    }
}
