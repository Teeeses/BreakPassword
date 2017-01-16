package ru.explead.breakpassword.logic;

import java.util.ArrayList;
import java.util.Random;

import ru.explead.breakpassword.beans.Cell;

/**
 * Created by develop on 13.01.2017.
 */

public class Controller {

    /**
     * Количество цифр в пароле
     */
    private int numberCells;
    /**
     * Пароль
     */
    private ArrayList<Integer> password = new ArrayList<>();
    /**
     * Массив клеток
     */
    private ArrayList<Cell> cells = new ArrayList<>();

    private Random random = new Random();



    public Controller() {
        numberCells = 4;
        generatePassword(numberCells);
    }

    /**
     * Генерируем пароль
     * @param numberCells - количество цифр
     */
    public void generatePassword(int numberCells) {
        password.clear();
        this.numberCells = numberCells;
        for(int i = 0; i < numberCells; i++) {
            int number;
            do {
                number = random.nextInt(10);
            } while (password.contains(number));
            password.add(number);
        }
        System.out.println(toStringPassword());
    }

    /**
     * Метод для вывода пароля
     * @return - строка(пароль)
     */
    public String toStringPassword() {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < numberCells; i++) {
            builder.append(password.get(i));
        }
        return builder.toString();
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
