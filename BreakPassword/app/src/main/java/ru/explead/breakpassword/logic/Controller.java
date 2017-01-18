package ru.explead.breakpassword.logic;

import java.util.ArrayList;
import java.util.Random;

import ru.explead.breakpassword.beans.Cell;
import ru.explead.breakpassword.beans.Data;

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

    /**
     * Список информации по попыткам
     */
    private ArrayList<Data> data = new ArrayList<>();
    private int numberAttempts = 0;
    private Random random = new Random();

    public static int EASY = 3, MEDIUM = 4, HARD = 5, VERY_HARD = 6;
    private int level;

    private Cell focusCell;

    public static int NO_ACTIVE = 0, ACTIVE = 1, FINISH = 2;
    private int status;


    public Controller() {
        level = EASY;
        status = ACTIVE;
        numberCells = level;
        generatePassword(numberCells);
    }

    public void restart() {
        status = ACTIVE;
        numberCells = level;
        numberAttempts = 0;
        password.clear();
        data.clear();
        cells.clear();
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
        System.out.println(arrayToString(password));
    }

    /**
     * Совершить попытку взлома
     * @return - объект Data
     */
    public Data toAttempt() {
        numberAttempts++;

        ArrayList<Integer> resultOnPlace = getNumberOnPlace();
        ArrayList<Integer> resultMatches = new ArrayList<>();

        for(int i = 0; i < numberCells; i++) {
            for(int j = 0; j < numberCells; j++) {
                if(!resultOnPlace.contains(i) && !resultMatches.contains(cells.get(j).getValue())) {
                    if(password.get(i) == cells.get(j).getValue()) {
                        resultMatches.add(cells.get(j).getValue());
                    }
                }
            }
        }
        Data resultData = new Data(resultOnPlace.size(), resultMatches.size(), toStringPasswordProbable());
        data.add(0, resultData);

        checkWin();

        return resultData;
    }

    public void checkWin() {
        if(numberCells == data.get(0).getOnPlace()) {
            status = FINISH;
        }
    }

    /**
     * Находим цифры которые стоят на своих местах
     * @return - массив индексов цифр на своих местах
     */
    public ArrayList<Integer> getNumberOnPlace() {
        ArrayList<Integer> result = new ArrayList<>();
        for(int i = 0; i < numberCells; i++) {
            if(password.get(i) == cells.get(i).getValue()) {
               result.add(i);
            }
        }
        return result;
    }

    /**
     * Метод для вывода пароля
     * @return - строка(пароль)
     */
    public String arrayToString(ArrayList<Integer> array) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < array.size(); i++) {
            builder.append(array.get(i));
        }
        return builder.toString();
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

    /**
     * Метод для вывода подобранного пароля
     * @return - строка(пароль)
     */
    public String toStringPasswordProbable() {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < numberCells; i++) {
            builder.append(cells.get(i).getValue());
        }
        return builder.toString();
    }

    /**
     * Проврка на пустые клетки
     * @return - true -если все заполнены
     */
    public boolean isEmptyCells() {
        for(int i = 0; i < numberCells; i++) {
            if(cells.get(i).getValue() == Cell.NO_ACTIVE) {
                return false;
            }
        }
        return true;
    }

    public void setFocusCell(Cell focusCell) {
        this.focusCell = focusCell;
        focusCell.setFocus();
    }

    public Cell getFocusCell() {
        return focusCell;
    }

    public void removeAllFocusCells() {
        for(int i = 0; i < numberCells; i++) {
            cells.get(i).removeFocus();
        }
    }

    public int getStatus() {
        return status;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getNumberAttempts() {
        return numberAttempts;
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
