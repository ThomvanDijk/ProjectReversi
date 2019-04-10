package com.reversi.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainMenu {

    private GridPane view ;

    public MainMenu(){

        view = new GridPane();

/** daag uit en join lobby knoppen voor reversi */

        Button buttonJoinLobbyReversie = new Button("Join lobby");
        buttonJoinLobbyReversie.setStyle("-fx-padding: 10 20 10 20");
        buttonJoinLobbyReversie.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Parent view = new Reversi().getView();
                Scene scenemenu = new Scene(view, 400, 400);
                Stage stage1 = new Stage();
                stage1.setTitle("Reversi");
                stage1.initOwner(buttonJoinLobbyReversie.getScene().getWindow());
                stage1.setScene(scenemenu);
                stage1.show();

            }
        });


       Button buttonDaaguitReversie = new Button("Daag uit");
       buttonDaaguitReversie.setStyle("-fx-padding: 10 20 10 20");
       buttonDaaguitReversie.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Parent view = new Reversi().getView();
                Scene scenemenu = new Scene(view, 400, 400);
                Stage stage1 = new Stage();
                stage1.setTitle("Reversi");
                stage1.initOwner(buttonDaaguitReversie.getScene().getWindow());
                stage1.setScene(scenemenu);
                stage1.show();

            }
        });


/** daag uit en join lobby knoppen voor boter kaas en eieren */

        //button to start boter kaas en eieren
        Button buttonJoinLobbyKaas = new Button("Join Lobby");
        buttonJoinLobbyKaas.setStyle("-fx-padding: 10 20 10 20");
        buttonJoinLobbyKaas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Parent view = new boterKaas().getView();
                Scene scenemenu = new Scene(view, 400, 400);
                Stage stage1 = new Stage();
                stage1.setTitle("Tic tac toe");
                stage1.initOwner(buttonJoinLobbyKaas.getScene().getWindow());
                stage1.setScene(scenemenu);
                stage1.show();


            }
        });


        //button to start boter kaas en eieren
        Button buttonDaagUitKaas = new Button("Daag uit");
        buttonDaagUitKaas.setStyle("-fx-padding: 10 20 10 20");
        buttonDaagUitKaas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Parent view = new BoterKaas().getView();
                Scene scenemenu = new Scene(view, 400, 400);
                Stage stage1 = new Stage();
                stage1.setTitle("Tic tac toe");
                stage1.initOwner(buttonDaagUitKaas.getScene().getWindow());
                stage1.setScene(scenemenu);
                stage1.show();

            }
        });


        //tekst labels
        Label reversie = new Label("Reversie");
        Label boterkaas = new Label("Boter, kaas en eieren");

        //Adding things to gridpane
        view.setPadding(new Insets(60));
        view.setVgap(5);
        view.setHgap(5);
        view.add(reversie,0,2);
        view.add(buttonDaaguitReversie, 0, 4);
        view.add(buttonJoinLobbyReversie, 1, 4);
        view.add(boterkaas, 0, 18);
        view.add(buttonDaagUitKaas, 0, 20);
        view.add(buttonJoinLobbyKaas, 1, 20);

}

    public Parent getView() {
        return view ;
    }

}