package com.reversi.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Reversi {

    public void start(Stage reversiStage){

        //create gridpane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(60));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        //Create scene
        Scene scene = new Scene(gridPane, 600, 600);


        //Setting title to the Stage
        reversiStage.setTitle("Game Lobby");
        reversiStage.setScene(scene);

        //Displaying the contents of the stage
        reversiStage.show();

        //draw board
        int[][] boardReversi = new int[8][8];
        char ch = '1';
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++) {
                boardReversi[i][j] = ch++;
                StackPane square = new StackPane();
                String color ;
                color = "black";
                square.setStyle("-fx-border-color: "+color+";");
                gridPane.add(square, i, j);
            }
        }

        for (int i = 0; i < 8; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            gridPane.getRowConstraints().add(new RowConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }






        //button to go back to menu
        Button buttonGoBack = new Button("back to menu");
        buttonGoBack.setStyle("-fx-padding: 10 20 10 20");

        gridPane.add(buttonGoBack, 12, 9);


        buttonGoBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                logIn login = new logIn();
                login.start(reversiStage);
            }
        });

    }

}
