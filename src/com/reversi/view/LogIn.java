package com.reversi.view;

import com.reversi.controller.Controller;
import com.reversi.controller.UserController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class LogIn {

    private UserController userController;

    public LogIn(UserController userController){
        this.userController = userController;
    }
	

    // can be any Parent subclass:

    public void Start (Stage menuStage) {



        //Buttons
        Button buttonJoinLobbyReversie = new Button("Join lobby");
        Button buttonDaaguitReversie = new Button("Daag uit");
        Button buttonJoinLobbyKaas = new Button("Join Lobby");
        Button buttonDaagUitKaas = new Button("Daag uit");

        //Grid Panes
        GridPane rootPane = new GridPane();
        Scene scene = new Scene(rootPane);
        GridPane gridPane = new GridPane();



        /** offline knoppen voor boter kaas  en reversi */
        Button buttonBoterKaas = new Button("Boter Kaas en eieren");
        buttonBoterKaas.setStyle("-fx-padding: 10 20 10 20");
        buttonBoterKaas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GridPane reversi = new Reversi().getView();
                reversi.setVisible(false);

                GridPane view = new BoterKaas().getView();
                view.setPrefSize(500, 500);
                view.setGridLinesVisible(true);
                rootPane.add(view, 1, 0, 50, 50);
            }
        });


        Button buttonReversie = new Button("Reversi");
        buttonReversie.setStyle("-fx-padding: 10 20 10 20");
        buttonReversie.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GridPane boterKaas = new BoterKaas().getView();
                boterKaas.setVisible(false);

                GridPane view = new Reversi().getView();
                view.setPrefSize(500, 500);
                view.setGridLinesVisible(true);
                rootPane.add(view, 1, 0, 50, 50);
            }
        });



/** daag uit en join lobby knoppen voor reversi */
        //Button join lobby reversie
        buttonJoinLobbyReversie.setStyle("-fx-padding: 10 20 10 20");
        buttonJoinLobbyReversie.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GridPane boterKaas = new BoterKaas().getView();
                boterKaas.setVisible(false);

                GridPane view = new Reversi().getView();
                view.setPrefSize(500, 500);
                view.setGridLinesVisible(true);
                rootPane.add(view, 1, 0, 50, 50);
            }
        });


        //Button daaguit reversie
        buttonDaaguitReversie.setStyle("-fx-padding: 10 20 10 20");
        buttonDaaguitReversie.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GridPane boterKaas = new BoterKaas().getView();
                boterKaas.setVisible(false);

                GridPane view = new Reversi().getView();
                view.setPrefSize(500, 500);
                view.setGridLinesVisible(true);
                rootPane.add(view, 1, 0, 50, 50);
            }
        });


/** daag uit en join lobby voor boterkaas */

        //button to start boter kaas en eieren
        buttonJoinLobbyKaas.setStyle("-fx-padding: 10 20 10 20");
        buttonJoinLobbyKaas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GridPane reversi = new Reversi().getView();
                reversi.setVisible(false);


                GridPane view = new BoterKaas().getView();


                view.setPrefSize(500, 500);
                view.setGridLinesVisible(true);
                rootPane.add(view, 1, 0, 50, 50);
            }
        });


        //button to start boter kaas en eieren
        buttonDaagUitKaas.setStyle("-fx-padding: 10 20 10 20");
        buttonDaagUitKaas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GridPane reversi = new Reversi().getView();
                reversi.setVisible(false);

                GridPane view = new BoterKaas().getView();
                view.setPrefSize(500, 500);
                view.setGridLinesVisible(true);
                rootPane.add(view, 1, 0, 50, 50);
            }
        });




        //Setting title to the Stage
        menuStage.setTitle("Mainmenu");
        menuStage.setScene(scene);

        //Displaying the contents of the stage
        menuStage.show();


        //label
        Label login = new Label("Vul je ip in en je gebruikernaam");
        Label login2 = new Label("om in te loggen");

        Label speeloffline = new Label("speel offline");
        Label Reversi = new Label("Reversi");
        Label Boterkaas = new Label("Tic tac toe");


        //textfield
        TextField ipadres = new TextField();
        ipadres.setMaxWidth(200);
        ipadres.setPromptText("IP Adres");

        TextField Gebruikersnaam = new TextField();
        Gebruikersnaam.setMaxWidth(200);
        Gebruikersnaam.setPromptText("Gebruikersnaam");

        Button buttonLogIN = new Button("login");
        
        //button log in
        buttonLogIN.setOnAction(e -> {
        	String username = Gebruikersnaam.getText();
        	String ipAdres = ipadres.getText();
            userController.notifyModel(Controller.LOG_IN, new String[] {username, ipAdres});

            buttonDaagUitKaas.setDisable(false);
            buttonJoinLobbyKaas.setDisable(false);
            buttonJoinLobbyReversie.setDisable(false);
            buttonDaaguitReversie.setDisable(false);

        });



        //speel online en log in
        gridPane.add(login, 0,0);
        gridPane.add(login2, 0,1);
        gridPane.add(ipadres, 0,3);
        gridPane.add(Gebruikersnaam, 0,4);
        gridPane.add(buttonLogIN, 0, 5 );

        //Reversi online knoppen
        gridPane.add(Reversi, 0, 15);
        gridPane.add(buttonDaaguitReversie, 0, 16);
        gridPane.add(buttonJoinLobbyReversie, 0, 17);
        buttonDaaguitReversie.setDisable(true);
        buttonJoinLobbyReversie.setDisable(true);

        //Tic tac toe online knoppen
        gridPane.add(Boterkaas, 1, 15);
        gridPane.add(buttonDaagUitKaas, 1, 16);
        gridPane.add(buttonJoinLobbyKaas, 1, 17);
        buttonDaagUitKaas.setDisable(true);
        buttonJoinLobbyKaas.setDisable(true);

        //Speel offline knoppen
        gridPane.add(speeloffline,0,45);
        gridPane.add(buttonBoterKaas, 0,46);
        gridPane.add(buttonReversie, 1, 46);
        gridPane.setPadding(new Insets(60));
        gridPane.setVgap(5);
        gridPane.setHgap(5);


        rootPane.getChildren().addAll(gridPane);

    }


}

