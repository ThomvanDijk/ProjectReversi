package com.reversi.view;

import com.reversi.controller.Controller;
import com.reversi.controller.UserController;
import com.reversi.model.Model;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;

public class ControlView extends View {
	
	private Button subReversiButton;
	private Button subTictactoeButton;

	public ControlView(UserController userController, StackPane controlPane) {
		super(userController);
		
		subReversiButton = new Button("Subscribe Reversi");
		subReversiButton.setDisable(true);
		subTictactoeButton = new Button("Subscribe Tic-tac-toe");
		subTictactoeButton.setDisable(true);
		subTictactoeButton.setPrefWidth(140);

		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.TOP_CENTER);

		// Create all the necessities to login
		createLogin(gridPane);

		subReversiButton.setOnAction(e -> {userController.notifyModel(Controller.SUBSCRIBE_TO_REVERSI, null);});
		subTictactoeButton.setOnAction(e -> {userController.notifyModel(Controller.SUBSCRIBE_TO_TICTACTOE, null);});
		
		Separator separator1 = new Separator();
		separator1.setStyle("-fx-background-color: black; -fx-border-height: 2;");
		Separator separator2 = new Separator();
		separator2.setStyle("-fx-background-color: black; -fx-border-height: 2;");
		
		GridPane.setMargin(separator1, new Insets(0, 0, 10, 0));
		GridPane.setMargin(separator2, new Insets(0, 0, 10, 0));
		//GridPane.setMargin(subReversiButton, new Insets(0, 0, 10, 0));
		
		gridPane.add(separator1, 0, 3);
		gridPane.add(separator2, 1, 3);
		gridPane.add(subReversiButton, 0, 4);
		
		HBox alignButtonRight = new HBox();
		alignButtonRight.setAlignment(Pos.CENTER_RIGHT);
		alignButtonRight.getChildren().add(subTictactoeButton);
		
		gridPane.add(alignButtonRight, 1, 4);

		controlPane.getChildren().add(gridPane);
	}

	@Override
	protected void update(Model model) {
	}
	
	public void createLogin(GridPane gridPane) {
		Label usernameLabel = new Label("Username:");
		usernameLabel.setPadding(new Insets(0, 15, 0, 0));

		Label addressLabel = new Label("Server address:");
		addressLabel.setPadding(new Insets(0, 15, 0, 0));

		TextField usernameField = new TextField();
		usernameField.setMaxWidth(200);
		usernameField.setPromptText("Username");

		TextField addressField = new TextField("localhost");
		addressField.setMaxWidth(200);
		addressField.setPromptText("IP address");

		// Create button for login and align it right
		Button loginButton = new Button("Login");
		loginButton.setOnAction(e -> {
			userController.notifyModel(Controller.LOG_IN,
					new String[] { usernameField.getText(), addressField.getText() });
			
			subReversiButton.setDisable(false);
			subTictactoeButton.setDisable(false);
		});

		HBox alignButtonRight = new HBox();
		alignButtonRight.setAlignment(Pos.CENTER_RIGHT);
		alignButtonRight.getChildren().add(loginButton);

		gridPane.setPadding(new Insets(10, 10, 10, 10));

		gridPane.add(usernameLabel, 0, 0);
		gridPane.add(addressLabel, 0, 1);

		gridPane.add(usernameField, 1, 0);
		gridPane.add(addressField, 1, 1);
		gridPane.add(alignButtonRight, 1, 2);
		
		GridPane.setMargin(usernameLabel, new Insets(0, 0, 10, 0));
		GridPane.setMargin(usernameField, new Insets(0, 0, 10, 0));
		GridPane.setMargin(addressLabel, new Insets(0, 0, 10, 0));
		GridPane.setMargin(addressField, new Insets(0, 0, 10, 0));
		GridPane.setMargin(alignButtonRight, new Insets(0, 0, 10, 0));
	}

}
