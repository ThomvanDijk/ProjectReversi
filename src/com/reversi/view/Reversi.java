package com.reversi.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Reversi {

	private GridPane view;

	public Reversi() {

		view = new GridPane();

		// button to go back to menu
		Button buttonGoBack = new Button("back to menu");
		buttonGoBack.setStyle("-fx-padding: 10 20 10 20");
		view.add(buttonGoBack, 12, 9);
		buttonGoBack.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				Parent view = new MainMenu().getView();
				Scene scenemenu = new Scene(view, 400, 400);
				Stage stage1 = new Stage();
				stage1.setTitle("Tic tac toe");
				stage1.initOwner(buttonGoBack.getScene().getWindow());
				stage1.setScene(scenemenu);
				stage1.show();

			}
		});

		// draw board
		int[][] boardReversi = new int[8][8];
		char ch = '1';
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {

				boardReversi[i][j] = ch++;
				StackPane square = new StackPane();

				square.setStyle("-fx-border-color: black");
				square.setStyle("-fx-background-color: green");

				if (boardReversi[i][j] == 1) {
					square.setStyle("-fx-background-color: black");
				} else if (boardReversi[i][j] == 2) {
					square.setStyle("-fx-background-color: white");
				} else {
					// leeg laten
				}
				view.add(square, i, j);

			}
		}

		for (int i = 0; i < 8; i++) {
			view.getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE,
					Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
			view.getRowConstraints().add(new RowConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY,
					Priority.ALWAYS, VPos.CENTER, true));
		}

		// Add stuff gridPane
		view.setPadding(new Insets(60));
		view.setVgap(5);
		view.setHgap(5);

	}
 
	public Parent getView() {
		return view;
	}
}