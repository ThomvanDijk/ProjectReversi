package com.reversi.view;

import javafx.application.Application;
import javafx.stage.Stage;

public class Window extends Application {
	
	GameView gameView;
	LogIn login;

	public Window(GameView gameView) {
		this.gameView = gameView;

	}
	
	public Window() {
		
	}
	
    public void rmain(String[] args){
        launch(args);
    }

    public void start(Stage primaryStage) {

		login = new LogIn(gameView.getUserController());
        login.Start(primaryStage);



    }
}