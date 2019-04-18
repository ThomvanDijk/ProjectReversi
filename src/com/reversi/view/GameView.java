package com.reversi.view;

import java.util.Scanner;

import com.reversi.controller.*;
import com.reversi.model.GameModel;
import com.reversi.model.Model;
import com.reversi.model.Player;
import com.reversi.model.Player.PlayerType;
import com.reversi.model.Reversi;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * The view that displays the actual game.
 * 
 * @author  Thom van Dijk
 * @version 1.0
 * @since   08-04-2019
 */
public class GameView extends View {

	private Scanner scanInput;
	public static boolean consoleInput;

	private Button[] gridButtons;

	private GridPane boardPane;
	private GridPane infoPane;

	private Label blackScore;
	private Label whiteScore;

	private Label blackPlayer;
	private Label whitePlayer;

	private Label gameName;
	private Label gameMode;
	private Label hasTurnText;

	public GameView(ViewController userController, StackPane gamePane) {
		super(userController);

		infoPane = new GridPane();
		infoPane.setAlignment(Pos.TOP_CENTER);

		boardPane = new GridPane();
		boardPane.setPadding(new Insets(10, 10, 10, 10));
		// boardPane.setStyle("-fx-background-color: purple;");
		boardPane.setAlignment(Pos.BOTTOM_CENTER);

		blackScore = new Label("0");
		blackScore.setStyle("-fx-text-fill: white; -fx-font: bold 20px 'Serif';");
		whiteScore = new Label("0");
		whiteScore.setStyle("-fx-font: bold 20px 'Serif';");

		blackPlayer = new Label("-");
		blackPlayer.setPadding(new Insets(0, 0, 0, 25));
		blackPlayer.setStyle("-fx-font: bold 20px 'Serif';");
		whitePlayer = new Label("-");
		whitePlayer.setPadding(new Insets(0, 25, 0, 0));
		whitePlayer.setStyle("-fx-font: bold 20px 'Serif';");

		gameName = new Label("REVERSI");
		gameName.setStyle("-fx-font: bold 20px 'Serif';");
		gameMode = new Label("Offline");
		gameMode.setStyle("-fx-font: 15px 'Serif';");
		hasTurnText = new Label("Game not started");
		hasTurnText.setStyle("-fx-font: 15px 'Serif';");

		scanInput = new Scanner(System.in);
		consoleInput = false;

		HBox hBoxLeft = new HBox();
		HBox hBoxCenter = new HBox();
		HBox hBoxRight = new HBox();

		hBoxLeft.setAlignment(Pos.CENTER_LEFT);
		// hBoxLeft.setStyle("-fx-background-color: green;");
		hBoxLeft.setPadding(new Insets(15, 0, 0, 0));
		hBoxLeft.setPrefWidth(Main.SCREEN_HEIGHT / 3 - 20);

		hBoxCenter.setAlignment(Pos.CENTER);
		// hBoxCenter.setStyle("-fx-background-color: red;");
		hBoxCenter.setPadding(new Insets(8, 0, 0, 0));
		hBoxCenter.setPrefWidth(Main.SCREEN_HEIGHT / 3 - 100);

		hBoxRight.setAlignment(Pos.CENTER_RIGHT);
		// hBoxRight.setStyle("-fx-background-color: red;");
		hBoxRight.setPadding(new Insets(15, 0, 0, 0));
		hBoxRight.setPrefWidth(Main.SCREEN_HEIGHT / 3 - 20);

		Circle blackCircle = new Circle(0, 0, 30);
		Circle whiteCircle = new Circle(0, 0, 30);
		whiteCircle.setFill(Color.WHITE);

		StackPane blackCircleHolder = new StackPane();
		blackCircleHolder.getChildren().addAll(blackCircle, blackScore);

		StackPane whiteCircleHolder = new StackPane();
		whiteCircleHolder.getChildren().addAll(whiteCircle, whiteScore);

		hBoxLeft.getChildren().addAll(blackCircleHolder, blackPlayer);
		VBox centerAlign = new VBox(5); // 5 is the spacing between elements in the VBox
		centerAlign.setAlignment(Pos.BOTTOM_CENTER);
		centerAlign.getChildren().addAll(gameName, gameMode, hasTurnText);
		hBoxCenter.getChildren().addAll(centerAlign);
		hBoxRight.getChildren().addAll(whitePlayer, whiteCircleHolder);

		infoPane.add(hBoxLeft, 0, 0);
		infoPane.add(hBoxCenter, 1, 0);
		infoPane.add(hBoxRight, 2, 0);
		
		infoPane.setDisable(true);
		boardPane.setDisable(true);

		gamePane.getChildren().addAll(infoPane, boardPane);
		
//		int[][] board = new int[][] {
//			{0,0,0,0,0,0,0,0},
//			{0,0,0,0,0,0,0,0},
//			{0,0,0,0,0,0,0,0},
//			{0,0,0,2,1,0,0,0},
//			{0,0,0,1,2,0,0,0},
//			{0,0,0,0,0,0,0,0},
//			{0,0,0,0,0,0,0,0},
//			{0,0,0,0,0,0,0,0}
//		};
		int[][] board = new int[8][8];
		updateBoard(board, null);
	}

	/**
	 * This update function must be used to get the new values from Model. Update is
	 * only called when model had some changes.
	 *
	 * @param model Model
	 */
	@Override
	protected void update(Model model) {
		infoPane.setDisable(false);
		boardPane.setDisable(false);
		
		GameModel gameModel = (GameModel) model; // cast

		Player[] players = gameModel.getPlayers();
		updateGameState(players);

		if (players[0].hasTurn()) {
			updateBoard(gameModel.getBoard(), players[0]);
		} else {
			updateBoard(gameModel.getBoard(), players[1]);
		}

		gameName.setText(gameModel.getCurrentGameType().name());

		// Make only the first letter a capital
		String gameModeString = gameModel.getCurrentGameMode().name().toLowerCase();
		gameMode.setText(gameModeString.substring(0, 1).toUpperCase() + gameModeString.substring(1));
	}

	public void updateGameState(Player[] players) {
		if (players[0].getColor() == Reversi.BLACK) {
			blackScore.setText("" + players[0].getScore());
			whiteScore.setText("" + players[1].getScore());

			blackPlayer.setText("" + players[0].getName());
			whitePlayer.setText("" + players[1].getName());
			if (players[0].hasTurn()) {
				hasTurnText.setText("Black has the turn");
			} else {
				hasTurnText.setText("White has the turn");
			}
		} else {
			blackScore.setText("" + players[1].getScore());
			whiteScore.setText("" + players[0].getScore());

			blackPlayer.setText("" + players[1].getName());
			whitePlayer.setText("" + players[0].getName());
			if (players[1].hasTurn()) {
				hasTurnText.setText("Black has the turn");
			} else {
				hasTurnText.setText("White has the turn");
			}
		}
	}

	public void updateBoard(int[][] board, Player player) {
		int spacing = 2;
		int squareSize = (Main.SCREEN_WIDTH / board.length) - spacing - 45;

		boardPane.getChildren().clear();

		for (int col = 0; col < board.length; col++) {
			for (int row = 0; row < board[0].length; row++) {

				// From 2D to 1D array
				int index = (col * board.length) + row;

				Button boardButton = new Button();
				boardButton.setPrefWidth(squareSize);
				boardButton.setPrefHeight(squareSize);
				boardButton.setStyle("-fx-background-color: darkseagreen; -fx-text-fill: white;");

				if(player == null) {
					
				} else if (player.getType().equals(PlayerType.HUMAN)) {
					boardButton.setOnAction(e -> {

//						Task<Void> task = new Task<Void>() {
//						    @Override
//						    public Void call() throws Exception {
//						    	userController.notifyModel(Controller.SET_MOVE, new String[] { String.valueOf(index) });
//						        return null ;
//						    }
//						};
//
//						
//						Thread thread = new Thread(task);
//						thread.setDaemon(true);
//						thread.start();

						// separate non-FX thread
//			            new Thread() {
//
//			                // runnable for that thread
//			                public void run() {
						viewController.notifyModel(Controller.SET_MOVE, new String[] { String.valueOf(index) });
//			                }
//			            }.start();

					});
				}

				StackPane squareHolder = new StackPane(boardButton);
				squareHolder.setPadding(new Insets(spacing));

				boardPane.add(squareHolder, row, col);

				if (board[col][row] == 1) {
					Circle blackCircle = new Circle(0, 0, squareSize / 2);
					boardPane.add(new StackPane(blackCircle), row, col);
				}

				if (board[col][row] == 2) {
					Circle whiteCircle = new Circle(0, 0, squareSize / 2);
					whiteCircle.setFill(Color.WHITE);
					boardPane.add(new StackPane(whiteCircle), row, col);
				}
			}
		}
	}

	// Use console as input and alternative ui
	public void consoleInput() {
		String textToSend;

		System.out.println("Application started waiting for login...");

		while (scanInput.hasNextLine() && !Thread.currentThread().isInterrupted()) {

			textToSend = scanInput.nextLine();

			String commands[] = textToSend.split(" ");

			if (commands[0].equals("login")) { // login <player name>
				if (commands[1] != null) {
					viewController.notifyModel(Controller.LOG_IN, new String[] { commands[1], "localhost" });
				} else {
					System.out.println("Wrong command! Try: login <name>");
				}
			}

			if (commands[0].equals("sub")) { // sub tictactoe or reversi
				if (commands[1] != null) {
					if (commands[1].equals("reversi")) {
						viewController.notifyModel(Controller.SUBSCRIBE_TO_REVERSI, null);
					} else if (commands[1].equals("tictactoe")) {
						viewController.notifyModel(Controller.SUBSCRIBE_TO_TICTACTOE, null);
					} else {

					}
				} else {
					System.out.println("Wrong command! Try: sub tictactoe or reversi");
				}
			}

			if (commands[0].equals("chal")) { // chal <player name> followed by tictactoe or reversi
				if (commands[1] != null && commands[2] != null) {
					viewController.notifyModel(Controller.CHALLENGE_PLAYER, new String[] { commands[1], commands[2] });
				} else {
					System.out.println("Wrong command! Try: chal <player name> followed by tictactoe or reversi");
				}
			}

			if (commands[0].equals("accept")) { // accept <chal no>
				if (commands[1] != null) {
					viewController.notifyModel(Controller.ACCEPT_CHALLENGE, new String[] { commands[1] });
				} else {
					System.out.println("Wrong command! Try: accept <chal no>");
				}
			}

			if (commands[0].equals("players")) { // accept <chal no>
				viewController.notifyModel(Controller.REQUEST_PLAYERLIST, null);
			}

			if (textToSend.equals("exit")) {
				break;
			}
		}

		scanInput.close();
	}

}