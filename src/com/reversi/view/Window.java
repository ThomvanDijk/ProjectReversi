package com.reversi.view;

import com.reversi.controller.UserController;

import javafx.application.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Window extends Application {

//   private Stage stage;
	GameView gameView;
	
	public Window(GameView gameView) { 
		this.gameView = gameView;
	}

	public Window() {

	}

	public void rmain(String[] args) {
		Application.launch(args);
	}

	public void start(Stage primaryStage) {

//      this.stage = primaryStage;

		Scene scene = new Scene(new Login(gameView.getUserController()).getView(), 400, 400);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	// public void removeStage(){
//        stage.hide();
//    }

}
