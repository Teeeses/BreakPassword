package ru.explead.breakpassword.app;

import android.app.Application;

import ru.explead.breakpassword.logic.Controller;

/**
 * Created by develop on 13.01.2017.
 */

public class App extends Application {

    private static float widthScreen;
    private static float heightScreen;

    private static Controller controller;


    public static float getWidthScreen() {
        return widthScreen;
    }

    public static void setWidthScreen(float widthScreen) {
        App.widthScreen = widthScreen;
    }

    public static float getHeightScreen() {
        return heightScreen;
    }

    public static void setHeightScreen(float heightScreen) {
        App.heightScreen = heightScreen;
    }

    public static Controller getController() {
        return controller;
    }

    public static void setController(Controller controller) {
        App.controller = controller;
    }
}