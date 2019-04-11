package com.reversi.view;

import com.reversi.controller.Controller;
import com.reversi.controller.UserController;
import com.reversi.model.GameModel;

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
	public UserController userController;

	public Login(UserController userController) {

		this.userController = userController;

		view = new GridPane();

		/** daag uit en join lobby knoppen voor reversi */

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

		// label
		Label login = new Label("Vul je ip in om in te loggen");
		Label speeloffline = new Label("speel offline");

		// textfield
		TextField ia = new TextField();
		ia.setPromptText("Ip Adres");
		TextField gn = new TextField();
		gn.setPromptText("Gebruikersnaam");

		// Button
		Button lgb = new Button("log in");
		lgb.setStyle("-fx-padding: 5 10 5 10");

		// create gridpane
		view.setPadding(new Insets(60));
		view.setVgap(5);
		view.setHgap(5);
		view.add(lgb, 0, 2);
		view.add(ia, 0, 1);
		view.add(gn, 1, 1);
		view.add(login, 0, 0);
		view.add(speeloffline, 0, 11);
		view.add(buttonBoterKaas, 0, 12);
		view.add(buttonReversie, 2, 12);

		lgb.setOnAction(e -> {
			String username = gn.getText();
			String ipAdres = ia.getText();

			System.out.println(username + ipAdres + this.userController);

			this.userController.notifyModel(Controller.LOG_IN, new String[] { username, ipAdres });
		});

	}

	public Parent getView() {
		return view;
	}

	public Stage getStage() {
		return stage;
	}

}
