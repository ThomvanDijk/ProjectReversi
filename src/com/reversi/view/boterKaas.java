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



        int[][] boardBoterKaas = new int[3][3];


        char ch = '1';
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++) {

                boardBoterKaas[i][j] = ch++;
                StackPane square = new StackPane();
                String color ;
                color = "black";
                square.setStyle("-fx-border-color: "+color+";");
                gridPane.add(square, i, j);


                if(boardBoterKaas[i][j] == 1){
                 square.setStyle("-fx-background-color: black");
                }
                else if (boardBoterKaas[i][j] == 2){
                    square.setStyle("-fx-background-color: black");
                }
                else {

                 }

            }
        }

      for (int i = 0; i < 3; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            gridPane.getRowConstraints().add(new RowConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }

        //button to go back to log in
        Button buttonGoBack = new Button("back to menu");
        buttonGoBack.setStyle("-fx-padding: 10 20 10 20");

        gridPane.setPadding(new Insets(60));
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
