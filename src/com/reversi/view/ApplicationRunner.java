package com.reversi.view;

import com.reversi.client.Client;
import com.reversi.controller.ClientController;
import com.reversi.controller.UserController;
import com.reversi.model.GameModel;
import javafx.application.Application;
import javafx.stage.Stage;

public class ApplicationRunner extends Application {

    public static boolean running;

    GameModel model = new GameModel();


    public void init(){

        // Give model to controllers because they must have a model
        ClientController clientController = new ClientController(model);
        UserController userController = new UserController(model);

        // Make a client that connects to the server
        Client client = new Client(clientController);

        // Make view and add a reference to controller
        // also pass args for javaFX
        GameView view = new GameView(userController);

        // Add the view references to the model
        model.setView(view);
        model.setClient(client);

        Thread viewThread = new Thread(view);

        viewThread.start();
    }



    public void start(Stage primaryStage) {
        UserController userController = new UserController(model);

        System.out.println(userController);

		LogIn login = new LogIn(userController);
        login.Start(primaryStage);
        primaryStage.show();



    }
    
    public static void main(String[] args) {
		launch(args);
	}
}