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

public class Login {

    public void start(Stage logIn) {


        /** daag uit en join lobby knoppen voor reversi */

        Button buttonBoterKaas = new Button("Boter Kaas en eieren");
        buttonBoterKaas.setStyle("-fx-padding: 10 20 10 20");



        buttonBoterKaas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                BoterKaas boterkaas = new BoterKaas();
                boterkaas.start(logIn);
            }
        });


        Button buttonReversie = new Button("Reversi");
        buttonReversie.setStyle("-fx-padding: 10 20 10 20");


        buttonReversie.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Reversi reversi = new Reversi();
                reversi.start(logIn);
            }
        });


        
        // buttons
        Button buttonLogIN = new Button("Inloggen");

        //label
        Label login = new Label("Vul je ip in om in te loggen");
        Label speeloffline = new Label("speel offline");

        //textfield
        TextField ia = new TextField("IP adres");
        TextField gn = new TextField("Gebruikersnaam");

        //create gridpane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(60));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.add(buttonLogIN, 0, 2);
        gridPane.add(ia, 0, 1 );
        gridPane.add(gn, 1, 1 );
        gridPane.add(login, 0,0);
        gridPane.add(speeloffline, 0, 11);
        gridPane.add(buttonBoterKaas, 0, 12);
        gridPane.add(buttonReversie, 2, 12);




        //Create scene
        Scene scene = new Scene(gridPane, 500, 350);


        //Setting title to the Stage
        logIn.setTitle("Welcome");
        logIn.setScene(scene);

        //Displaying the contents of the stage
        logIn.show();
        
        // Styling      
        buttonLogIN.setStyle("-fx-padding: 5 10 5 10");
        buttonLogIN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                MainMenu mainmenu = new MainMenu();
                mainmenu.start(logIn);
            }
        });




    }
}