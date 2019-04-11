package com.reversi.view;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Control;
import javafx.scene.layout.*;

public class Reversi {

    private GridPane view ;

    public Reversi(){

        view = new GridPane();

        //draw board
        int[][] boardReversi = new int[8][8];
        char ch = '1';
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++) {

                boardReversi[i][j] = ch++;
                StackPane square = new StackPane();

                square.setStyle("-fx-border-color: black");
                square.setStyle("-fx-background-color: green");


                if(boardReversi[i][j] == 1){
                    square.setStyle("-fx-background-color: black");
                }
                else if (boardReversi[i][j] == 2){
                    square.setStyle("-fx-background-color: white");
                }
                else {
                    //leeg laten
                }
                view.add(square, i, j);

            }
        }

        for (int i = 0; i < 8; i++) {
            view.getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            view.getRowConstraints().add(new RowConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }


    }

    public GridPane getView() {
        return view ;
    }


}