package com.reversi.view;

import com.reversi.client.Client;
import com.reversi.controller.ClientController;
import com.reversi.controller.UserController;
import com.reversi.model.GameModel;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

	public static boolean running;
	
	public static final int SCREEN_WIDTH = 900;
	public static final int SCREEN_HEIGHT = 680;

	private GameModel model;
	private UserController userController;

	private HBox hBox;
	private StackPane gamePane;
	private StackPane controlPane;

	public void init() {
		model = new GameModel();

		// Give model to controllers because they must have a model
		ClientController clientController = new ClientController(model);
		userController = new UserController(model);

		// Make a client that connects to the server
		Client client = new Client(clientController);

		// Make view and add a reference to controller
		// also pass args for javaFX
		//GameView view = new GameView(userController);

		// Add the view references to the model
		//model.setView(view);
		model.setClient(client);

		//Thread viewThread = new Thread(view);

		//viewThread.start();
	}

	public void start(Stage stage) {
//    	if(!GameView.consoleInput) {
//    		LogIn login = new LogIn(userController);
//        	login.Start(primaryStage);
//
//        	primaryStage.show();
//        }
		
		

		// Create HBox to split the screen in two
		hBox = new HBox();
		gamePane = new StackPane();
		gamePane.setStyle("-fx-background-color: beige;");
		gamePane.setPadding(new Insets(10, 10, 10, 10));
		gamePane.setMinWidth(SCREEN_HEIGHT - 80); // Make squared
		
		controlPane = new StackPane();
		controlPane.setStyle("-fx-background-color: lightgray;");
		controlPane.setPadding(new Insets(10, 10, 10, 10));
		controlPane.prefWidthProperty().bind(hBox.widthProperty());

		GameView gameView = new GameView(userController, gamePane);
		ControlView controlView = new ControlView(userController, controlPane);
		
		// Add the view references to the model
		model.addView(gameView);
		model.addView(controlView);

//		HBox.setMargin(gamePane, new Insets(20, 20, 20, 20)); 
//		HBox.setMargin(controlsPane, new Insets(20, 20, 20, 20)); 

		// Add the gamePane	and controlsPane
		hBox.getChildren().addAll(gamePane, controlPane);

		// Create a scene and place it in the stage
		Scene scene = new Scene(hBox, SCREEN_WIDTH, SCREEN_HEIGHT);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Abstract strategy games");
		stage.show();
	}
	
	public UserController getUserController() {
		return userController;
	}

	public static void main(String[] args) {
		launch(args);
	}
}