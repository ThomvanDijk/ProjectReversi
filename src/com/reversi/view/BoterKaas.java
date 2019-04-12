package com.reversi.view;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Control;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class BoterKaas {

    private GridPane view;

    public BoterKaas(){

        view = new GridPane();


        //Draw board
        int[][] boardBoterKaas = new int[3][3];
        char ch = '1';
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++) {

                boardBoterKaas[i][j] = ch++;
                StackPane square = new StackPane();
                square.setStyle("-fx-border-color: black");
                square.setStyle("-fx-background-color: white");

                int x = 2;
                boardBoterKaas[1][2] = x;

                if(boardBoterKaas[i][j] == 1){
                    //x
                    square.setStyle("-fx-background-color: black");
                }
                else if (boardBoterKaas[i][j] == 2){
                    Circle circle1 = new Circle();
                    circle1.setRadius(25);
                    circle1.setFill(Color.BLACK);
                    square.getChildren().add(circle1);
                }
                else {
                    //leeg laten
                }
                view.add(square, i, j);
            }
        }
        for (int i = 0; i < 3; i++) {
            view.getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            view.getRowConstraints().add(new RowConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }

    }

    public GridPane getView() {
        return view ;
    }


}