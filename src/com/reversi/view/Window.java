package com.reversi.view;

import javafx.application.Application;
import javafx.stage.Stage;

public class Window extends Application {

    public void start(Stage primaryStage){

        MainMenu menu = new MainMenu();
        menu.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}



