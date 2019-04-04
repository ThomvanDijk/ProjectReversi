package com.reversi.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class endGame {

    public void start (Stage endGameStage){

        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        Label verloren = new Label("U heeft verloren");
        Label gewonnen = new Label("U heeftgewonnen");

        gridPane.add(verloren, 0, 1);
        gridPane.add(gewonnen, 0, 3);

        //button to go back to log in
        Button buttonGoBack = new Button("back to menu");
        gridPane.add(buttonGoBack, 12, 9);

        buttonGoBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                MainMenu mainmenu = new MainMenu();
                mainmenu.start(endGameStage);
            }
        });


    }

}
