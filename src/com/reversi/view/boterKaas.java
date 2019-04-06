package com.reversi.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class boterKaas {

    public void start(Stage boterKaasStage){

        //create gridpane
        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        //Create scene
        Scene scene = new Scene(gridPane, 600, 600);


        //Setting title to the Stage
        boterKaasStage.setTitle("in Game");
        boterKaasStage.setScene(scene);

        //Displaying the contents of the stage
        boterKaasStage.show();



        final int size = 3;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col ++) {
                StackPane square = new StackPane();
                String color ;
                color = "black";
                square.setStyle("-fx-border-color: "+color+";");
                gridPane.add(square, col, row);
            }
        }
        for (int i = 0; i < size; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            gridPane.getRowConstraints().add(new RowConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }

        //button to go back to log in
        Button buttonGoBack = new Button("back to menu");
        gridPane.add(buttonGoBack, 12, 9);

        buttonGoBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                logIn login = new logIn();
                login.start(boterKaasStage);
            }
        });

    }


}
