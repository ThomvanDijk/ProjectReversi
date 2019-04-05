package com.reversi.view;

import javafx.application.Application;
import javafx.stage.Stage;

public class Window extends Application {

    public void rmain(String[] args){
        launch(args);
    }

    public void start(Stage primaryStage){

        logIn login = new logIn();
        login.start(primaryStage);
    }



}



