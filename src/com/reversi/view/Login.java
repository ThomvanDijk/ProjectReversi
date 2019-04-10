package com.reversi.view;

import com.reversi.controller.Controller;
import com.reversi.controller.UserController;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

<<<<<<< HEAD:src/com/reversi/view/Login.java
public class Login  {

	private UserController userController;
=======

public class logIn {

    // can be any Parent subclass:
    private Stage stage;
    private GridPane view;



    public logIn() {

        view = new GridPane();
        this.stage = stage;
>>>>>>> master:src/com/reversi/view/logIn.java

	public Login(UserController userController) {
		this.userController = userController;
	}

<<<<<<< HEAD:src/com/reversi/view/Login.java
	public void start(Stage logIn) {

        /** daag uit en join lobby knoppen voor reversi */
=======
        Button buttonLogIN = new Button("log in");
        buttonLogIN.setStyle("-fx-padding: 10 20 10 20");
        buttonLogIN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Parent view1 = new MainMenu().getView();
                Scene scenemenu = new Scene(view1, 400, 400);
                Stage stage = new Stage();
                stage.setTitle("Mainmenu");
                stage.initOwner(buttonLogIN.getScene().getWindow());
                stage.setScene(scenemenu);
                stage.show();


                //view.getChildren().clear();

                //logIn login = new logIn();
                //login.getStage().close();

            }
        });
>>>>>>> master:src/com/reversi/view/logIn.java


        Button buttonBoterKaas = new Button("Boter Kaas en eieren");
        buttonBoterKaas.setStyle("-fx-padding: 10 20 10 20");
        buttonBoterKaas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Parent view = new boterKaas().getView();
                Scene scenemenu = new Scene(view, 400, 400);
                Stage stage1 = new Stage();
                stage1.setTitle("Tic tac toe");
                stage1.initOwner(buttonBoterKaas.getScene().getWindow());
                stage1.setScene(scenemenu);
                stage1.show();

<<<<<<< HEAD:src/com/reversi/view/Login.java
                BoterKaas boterkaas = new BoterKaas();
                boterkaas.start(logIn);
=======
>>>>>>> master:src/com/reversi/view/logIn.java
            }
        });


        Button buttonReversie = new Button("Reversi");
        buttonReversie.setStyle("-fx-padding: 10 20 10 20");
        buttonReversie.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

<<<<<<< HEAD:src/com/reversi/view/Login.java
                Reversi reversi = new Reversi();
                reversi.start(logIn);
=======
                Parent view = new Reversi().getView();
                Scene scenemenu = new Scene(view, 400, 400);
                Stage stage1 = new Stage();
                stage1.setTitle("Reversi");
                stage1.initOwner(buttonReversie.getScene().getWindow());
                stage1.setScene(scenemenu);
                stage1.show();

>>>>>>> master:src/com/reversi/view/logIn.java
            }
        });


        
        // buttons
        Button lgb = new Button("Inloggen");
        
        //label
        Label login = new Label("Vul je ip in om in te loggen");
        Label speeloffline = new Label("speel offline");

        //textfield
        TextField ia = new TextField("");
        ia.setPromptText("IP Adres");
        TextField gn = new TextField("");
        gn.setPromptText("Gebruikersnaam");

        //create gridpane
<<<<<<< HEAD:src/com/reversi/view/Login.java
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(60));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.add(ia, 0, 1 );
        gridPane.add(lgb, 0, 2);
        gridPane.add(gn, 1, 1 );
        gridPane.add(login, 0,0);
        gridPane.add(speeloffline, 0, 11);
        gridPane.add(buttonBoterKaas, 0, 12);
        gridPane.add(buttonReversie, 2, 12);

        //Create scene
        Scene scene = new Scene(gridPane, 500, 350);


        //Setting title to the Stage
        logIn.setTitle("Welcome");
        logIn.setScene(scene);

        //Displaying the contents of the stage
        logIn.show();
        
        // Styling      
        lgb.setStyle("-fx-padding: 5 10 5 10");
        lgb.setOnAction(e -> {
        	String username = gn.getText();
        	String ipAdres = ia.getText();
        	
    		userController.notifyModel(Controller.LOG_IN, new String[]{username, ipAdres});
        });
    }
    
}
=======
        view.setPadding(new Insets(60));
        view.setVgap(5);
        view.setHgap(5);
        view.add(buttonLogIN, 0, 2);
        view.add(text, 0, 1 );
        view.add(login, 0,0);
        view.add(speeloffline, 0, 11);
        view.add(buttonBoterKaas, 0, 12);
        view.add(buttonReversie, 2, 12);


    }


    public Parent getView() {
        return view ;
    }

    public Stage getStage(){
        return stage;
    }


}
>>>>>>> master:src/com/reversi/view/logIn.java
