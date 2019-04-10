package com.reversi.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Login {

    // can be any Parent subclass:
    private Stage stage;
    private GridPane view;



    public Login() {

        view = new GridPane();
        this.stage = stage;

        /** daag uit en join lobby knoppen voor reversi */

        Button buttonLogIN = new Button("log in");
        buttonLogIN.setStyle("-fx-padding: 10 20 10 20");
        buttonLogIN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Parent view1 = new MainMenu().getView();
                Scene scenemenu = new Scene(view1, 400, 400);
                Stage stage = new Stage();
                stage.setTitle("Mainmenu");
                stage.initOwner(buttonLogIN.getScene().getWindow());
                stage.setScene(scenemenu);
                stage.show();


                //view.getChildren().clear();

                //logIn login = new logIn();
                //login.getStage().close();

            }
        });


        Button buttonBoterKaas = new Button("Boter Kaas en eieren");
        buttonBoterKaas.setStyle("-fx-padding: 10 20 10 20");
        buttonBoterKaas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Parent view = new BoterKaas().getView();
                Scene scenemenu = new Scene(view, 400, 400);
                Stage stage1 = new Stage();
                stage1.setTitle("Tic tac toe");
                stage1.initOwner(buttonBoterKaas.getScene().getWindow());
                stage1.setScene(scenemenu);
                stage1.show();

            }
        });


        Button buttonReversie = new Button("Reversi");
        buttonReversie.setStyle("-fx-padding: 10 20 10 20");
        buttonReversie.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Parent view = new Reversi().getView();
                Scene scenemenu = new Scene(view, 400, 400);
                Stage stage1 = new Stage();
                stage1.setTitle("Reversi");
                stage1.initOwner(buttonReversie.getScene().getWindow());
                stage1.setScene(scenemenu);
                stage1.show();

            }
        });


        //label
        Label login = new Label("Vul je ip in om in te loggen");
        Label speeloffline = new Label("speel offline");

        //textfield
        TextField text = new TextField("IP adres");

        //create gridpane
        view.setPadding(new Insets(60));
        view.setVgap(5);
        view.setHgap(5);
        view.add(buttonLogIN, 0, 2);
        view.add(text, 0, 1 );
        view.add(login, 0,0);
        view.add(speeloffline, 0, 11);
        view.add(buttonBoterKaas, 0, 12);
        view.add(buttonReversie, 2, 12);


    }


    public Parent getView() {
        return view ;
    }

    public Stage getStage(){
        return stage;
    }


}
