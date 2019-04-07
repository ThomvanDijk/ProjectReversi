package com.reversi.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class logIn {

    public void start(Stage logIN) {


        /** daag uit en join lobby knoppen voor reversi */

        Button buttonLogIN = new Button("log in");
        buttonLogIN.setStyle("-fx-padding: 10 20 10 20");


        buttonLogIN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                MainMenu mainmenu = new MainMenu();
                mainmenu.start(logIN);
            }
        });

        Button buttonBoterKaas = new Button("Boter Kaas en eieren");
        buttonBoterKaas.setStyle("-fx-padding: 10 20 10 20");



        buttonBoterKaas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                boterKaas boterkaas = new boterKaas();
                boterkaas.start(logIN);
            }
        });


        Button buttonReversie = new Button("Reversi");
        buttonReversie.setStyle("-fx-padding: 10 20 10 20");


        buttonReversie.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Reversi reversi = new Reversi();
                reversi.start(logIN);
            }
        });


        //label
        Label login = new Label("Vul je ip in om in te loggen");
        Label speeloffline = new Label("speel offline");

        //textfield
        TextField text = new TextField("IP adres");

        //create gridpane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(60));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.add(buttonLogIN, 0, 2);
        gridPane.add(text, 0, 1 );
        gridPane.add(login, 0,0);
        gridPane.add(speeloffline, 0, 11);
        gridPane.add(buttonBoterKaas, 0, 12);
        gridPane.add(buttonReversie, 2, 12);







        //Create scene
        Scene scene = new Scene(gridPane, 500, 350);


        //Setting title to the Stage
        logIN.setTitle("G1");
        logIN.setScene(scene);

        //Displaying the contents of the stage
        logIN.show();




    }
}