package com.reversi.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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


        buttonLogIN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                MainMenu mainmenu = new MainMenu();
                mainmenu.start(logIN);
            }
        });

        Button buttonBoterKaas = new Button("Boter Kaas en eieren");

        buttonBoterKaas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                boterKaas boterkaas = new boterKaas();
                boterkaas.start(logIN);
            }
        });


        Button buttonReversie = new Button("Reversi");

        buttonReversie.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Reversi reversi = new Reversi();
                reversi.start(logIN);
            }
        });


        //label
        Label login = new Label("Vul je ip in om in te logggen");

        Label speeloffline = new Label("speel offline");


        //textfield
        TextField text = new TextField("IP adres");



        //create gridpane
        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.add(buttonLogIN, 1, 3);
        gridPane.add(text, 1, 2 );
        gridPane.add(login, 1,1);
        gridPane.add(speeloffline, 1, 15);
        gridPane.add(buttonBoterKaas, 1, 18);
        gridPane.add(buttonReversie, 3, 18);

        //Create scene
        Scene scene = new Scene(gridPane, 400, 300);


        //Setting title to the Stage
        logIN.setTitle("log in met je ip");
        logIN.setScene(scene);

        //Displaying the contents of the stage
        logIN.show();




    }
}