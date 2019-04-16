package com.reversi.view;

import com.reversi.controller.Controller;
import com.reversi.controller.ViewController;
import com.reversi.model.Model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * The view that displays the controls used to start a game for example or to
 * login. This view will also show lists of online players and manages user
 * input to the server.
 * 
 * @author  Thom van Dijk
 * @version 1.0
 * @since   16-04-2019
 */
public class ControlView extends View {

	private Button subReversiButton;
	private Button subTictactoeButton;

	private Button playReversiButton;
	private Button playTictactoeButton;

	public ControlView(ViewController userController, StackPane controlPane) {
		super(userController);

		subReversiButton = new Button("Subscribe Reversi");
		subReversiButton.setDisable(true);
		subTictactoeButton = new Button("Subscribe Tic-tac-toe");
		subTictactoeButton.setDisable(true);
		subTictactoeButton.setPrefWidth(130);

		playReversiButton = new Button("Play Reversi");
		playTictactoeButton = new Button("Play Tic-tac-toe");

		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.TOP_CENTER);
		gridPane.setPadding(new Insets(10, 10, 10, 10));

		// Create all the necessities to login
		createLogin(gridPane);

		// Buttons to subscribe
		subReversiButton.setOnAction(e -> {
			userController.notifyModel(Controller.SUBSCRIBE_TO_REVERSI, null);
		});
		subTictactoeButton.setOnAction(e -> {
			userController.notifyModel(Controller.SUBSCRIBE_TO_TICTACTOE, null);
		});

		gridPane.add(subReversiButton, 0, 4);
		HBox alignButtonRight = new HBox();
		alignButtonRight.setAlignment(Pos.CENTER_RIGHT);
		alignButtonRight.getChildren().add(subTictactoeButton);
		gridPane.add(alignButtonRight, 1, 4);

		// Buttons for offline play
		createOfflineButtons(gridPane);

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
			viewController.notifyModel(Controller.LOG_IN,
					new String[] { usernameField.getText(), addressField.getText() });

			subReversiButton.setDisable(false);
			subTictactoeButton.setDisable(false);
		});

		HBox alignButtonRight = new HBox();
		alignButtonRight.setAlignment(Pos.CENTER_RIGHT);
		alignButtonRight.getChildren().add(loginButton);

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

		Separator separator1 = new Separator();
		separator1.setStyle("-fx-background-color: black; -fx-border-height: 2;");
		Separator separator2 = new Separator();
		separator2.setStyle("-fx-background-color: black; -fx-border-height: 2;");

		GridPane.setMargin(separator1, new Insets(0, 0, 10, 0));
		GridPane.setMargin(separator2, new Insets(0, 0, 10, 0));

		gridPane.add(separator1, 0, 3);
		gridPane.add(separator2, 1, 3);
	}

	public void createOfflineButtons(GridPane gridPane) {

		Label sectionText = new Label("Play offline");

		// Separator above offline buttons
		Separator separator1 = new Separator();
		separator1.setStyle("-fx-background-color: black; -fx-border-height: 2;");
		Separator separator2 = new Separator();
		separator2.setStyle("-fx-background-color: black; -fx-border-height: 2;");

		GridPane.setMargin(sectionText, new Insets(10, 0, 0, 1));
		GridPane.setMargin(separator1, new Insets(5, 0, 10, 0));
		GridPane.setMargin(separator2, new Insets(5, 0, 10, 0));

		gridPane.add(sectionText, 0, 19);
		gridPane.add(separator1, 0, 20);
		gridPane.add(separator2, 1, 20);

		// Buttons to subscribe
		playReversiButton.setOnAction(e -> {
			viewController.notifyModel(Controller.START_REVERSI_SINGLEPLAYER, null);
		});
		playTictactoeButton.setOnAction(e -> {
			viewController.notifyModel(Controller.START_TICTACTOE_SINGLEPLAYER, null);
		});

		gridPane.add(playReversiButton, 0, 21);
		HBox alignButtonRight = new HBox();
		alignButtonRight.setAlignment(Pos.CENTER_RIGHT);
		alignButtonRight.getChildren().add(playTictactoeButton);
		gridPane.add(alignButtonRight, 1, 21);
	}

}
