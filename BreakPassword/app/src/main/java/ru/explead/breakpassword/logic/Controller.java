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
    private ArrayList<Cell> cells = new ArrayList<>();
    private Random random = new Random();
    private int widthCell;

    public Controller() {
        numberCells = 4;
        generatePassword(numberCells);
    }

    public void generatePassword(int numberCells) {
        this.numberCells = numberCells;
        for(int i = 0; i < numberCells; i++) {
            int number;
            do {
                number = random.nextInt(10);
            } while (password.contains(number));
            password.add(number);
        }
    }

    public ArrayList<Cell> getCells() {
        return cells;
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

    public void setCells(ArrayList<Cell> cells) {
        this.cells = cells;
    }
}
