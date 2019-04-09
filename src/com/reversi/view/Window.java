package com.reversi.view;

import javafx.application.Application;
import javafx.stage.Stage;

public class Window extends Application {
	
    Login login;
    GameView gameView;

	public Window(GameView gameView) {
		this.gameView = gameView;
		
	}

    public void rmain(String[] args){
        launch(args);
    }

    public void start(Stage primaryStage){
		login = new Login(gameView.getUserController());
        login.start(primaryStage);
    }



}



