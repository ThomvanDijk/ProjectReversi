package com.reversi.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class BoterKaas {

    private GridPane view;

    public BoterKaas(){

        view = new GridPane();

        //button to go back to log in
        Button buttonGoBack = new Button("back to menu");
        buttonGoBack.setStyle("-fx-padding: 10 20 10 20");
        view.add(buttonGoBack, 12, 9);
        buttonGoBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Parent view = new MainMenu().getView();
                Scene scenemenu = new Scene(view, 400, 400);
                Stage stage1 = new Stage();
                stage1.setTitle("Mainmenu");
                stage1.initOwner(buttonGoBack.getScene().getWindow());
                stage1.setScene(scenemenu);
                stage1.show();
            }
        });


        //Draw board
        int[][] boardBoterKaas = new int[3][3];
        char ch = '1';
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++) {

                boardBoterKaas[i][j] = ch++;
                StackPane square = new StackPane();
                square.setStyle("-fx-border-color: black");
                square.setStyle("-fx-background-color: green");

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
        //adding things to gridPane
        view.setVgap(5);
        view.setHgap(5);
    }

    public Parent getView() {
        return view ;
    }


}