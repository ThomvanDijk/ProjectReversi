package com.reversi.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainMenu {

    public void start(Stage mainMenu){


/** daag uit en join lobby knoppen voor reversi */

        Button buttonJoinLobbyReversie = new Button("Join lobby");
        buttonJoinLobbyReversie.setStyle("-fx-padding: 10 20 10 20");


        buttonJoinLobbyReversie.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Reversi reversi = new Reversi();
                reversi.start(mainMenu);
            }
        });


       Button buttonDaaguitReversie = new Button("Daag uit");
        buttonDaaguitReversie.setStyle("-fx-padding: 10 20 10 20");


        buttonDaaguitReversie.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Reversi reversi = new Reversi();
                reversi.start(mainMenu);
            }
        });


/** daag uit en join lobby knoppen voor boter kaas en eieren */

        //button to start boter kaas en eieren
        Button buttonJoinLobbyKaas = new Button("Join Lobby");
        buttonJoinLobbyKaas.setStyle("-fx-padding: 10 20 10 20");


        buttonJoinLobbyKaas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                BoterKaas boterkaas = new BoterKaas();
                boterkaas.start(mainMenu);
            }
        });


        //button to start boter kaas en eieren
        Button buttonDaagUitKaas = new Button("Daag uit");
        buttonDaagUitKaas.setStyle("-fx-padding: 10 20 10 20");


        buttonDaagUitKaas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                BoterKaas boterkaas = new BoterKaas();
                boterkaas.start(mainMenu);
            }
        });



        Label reversie = new Label("Reversie");
        Label boterkaas = new Label("Boter, kaas en eieren");


        //create gridpane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(60));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.add(reversie,0,2);
        gridPane.add(buttonDaaguitReversie, 0, 4);
        gridPane.add(buttonJoinLobbyReversie, 1, 4);
        gridPane.add(boterkaas, 0, 18);
        gridPane.add(buttonDaagUitKaas, 0, 20);
        gridPane.add(buttonJoinLobbyKaas, 1, 20);


        //Create scene
        Scene scene = new Scene(gridPane, 400, 300);


        //Setting title to the Stage
        mainMenu.setTitle("Main Menu");
        mainMenu.setScene(scene);

        //Displaying the contents of the stage
        mainMenu.show();


    }

}
