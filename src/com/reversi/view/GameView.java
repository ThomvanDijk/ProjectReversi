/**
* Abstract class for GameView to hide some functions that GameView doesn't need to know. 
* 
* @author Thom van Dijk
* @version 1.0
* @since 08-04-2019
*/

package com.reversi.view;

import java.util.Scanner;

import com.reversi.controller.*;
import com.reversi.model.GameModel;
import com.reversi.model.Model;
import com.reversi.model.Player;
import com.reversi.model.Reversi;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GameView extends View {
	
	private Scanner scanInput;
	public static boolean consoleInput;
	
	private GridPane boardPane;
	
	private Label blackScore;
	private Label whiteScore;
	
	private Label blackPlayer;
	private Label whitePlayer;
	
	private Label gameName;
	private Label hasTurnText;

	public GameView(UserController userController, StackPane gamePane) {
		super(userController);
		
		blackScore = new Label("0");
		blackScore.setStyle("-fx-text-fill: white; -fx-font: bold 20px 'Serif';");
		whiteScore = new Label("0");
		whiteScore.setStyle("-fx-font: bold 20px 'Serif';");
		
		blackPlayer = new Label("Player");
		blackPlayer.setPadding(new Insets(0, 0, 0, 20));
		blackPlayer.setStyle("-fx-font: bold 20px 'Serif';");
		whitePlayer = new Label("Computer");
		whitePlayer.setPadding(new Insets(0, 20, 0, 0));
		whitePlayer.setStyle("-fx-font: bold 20px 'Serif';");
		
		gameName = new Label("Reversi");
		gameName.setStyle("-fx-font: bold 20px 'Serif';");
		hasTurnText = new Label("Reversi");
		hasTurnText.setStyle("-fx-font: 10px 'Serif';");

		scanInput = new Scanner(System.in);
		consoleInput = false;

		// Examples notify Model
		//userController.notifyModel(Controller.LOG_IN, new String[] {"Naam", "localhost"});
		//userController.notifyModel(Controller.START_ONLINE_GAME, new String[] {"reversi"});
		//userController.notifyModel(Controller.CHALLENGE_PLAYER, new String[] {"Naam", "reversi"});
		//userController.notifyModel(Controller.ACCEPT_CHALLENGE, new String[] {"23"});
		//userController.notifyModel(Controller.REQUEST_PLAYERLIST, null);

		boardPane = new GridPane();
		boardPane.setPadding(new Insets(10, 10, 10, 10));
		//boardPane.setStyle("-fx-background-color: purple;");
		boardPane.setAlignment(Pos.BOTTOM_CENTER);
		
		int[][] board = new int[8][8];
		showBoard(board);
		
		
		HBox hBoxLeft = new HBox();
		HBox hBoxCenter = new HBox();
		HBox hBoxRight = new HBox();
		
		hBoxLeft.setAlignment(Pos.CENTER_LEFT);
		//hBoxLeft.setStyle("-fx-background-color: green;");
		hBoxLeft.setPadding(new Insets(15, 0, 0, 0));
		hBoxLeft.setPrefWidth(Main.SCREEN_HEIGHT / 3 - 20);
		
		hBoxCenter.setAlignment(Pos.CENTER);
		//hBoxCenter.setStyle("-fx-background-color: red;");
		hBoxCenter.setPadding(new Insets(0, 0, 0, 0));
		hBoxCenter.setPrefWidth(Main.SCREEN_HEIGHT / 3 - 100);
		
		hBoxRight.setAlignment(Pos.CENTER_RIGHT);
		//hBoxRight.setStyle("-fx-background-color: red;");
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
		centerAlign.setAlignment(Pos.CENTER);
		centerAlign.getChildren().addAll(gameName, hasTurnText);
		hBoxCenter.getChildren().addAll(centerAlign);
		hBoxRight.getChildren().addAll(whitePlayer, whiteCircleHolder);		
		
		
		GridPane mainPane = new GridPane();
		mainPane.setAlignment(Pos.TOP_CENTER);

		mainPane.add(hBoxLeft, 0, 0);
		mainPane.add(hBoxCenter, 1, 0);
		mainPane.add(hBoxRight, 2, 0);

		gamePane.getChildren().addAll(boardPane, mainPane);
	}

	/**
	 * This update function must be used to get the new values from model (GameModel).
	 * Update is only called when model had some changes.
	 *
	 * @param model Model
	 */
	@Override
	protected void update(Model model) {
		GameModel gameModel = (GameModel) model; // cast
		
		showBoard(gameModel.getBoard());
		
		showScores(gameModel.getPlayers());
		//gameModel.getPlayer(); returns a player array
		//gameModel.getPlayerScores();
		//hasChallenge(); // returns boolean
	}
	
	public void showScores(Player[] players) {
		
		if(players[0].getColor() == Reversi.BLACK) {
			blackScore.setText("" + players[0].getScore());
			whiteScore.setText("" + players[1].getScore());
					        
			blackPlayer.setText("" + players[0].getName());
			whitePlayer.setText("" + players[1].getName());
		} else {
			blackScore.setText("" + players[1].getScore());
			whiteScore.setText("" + players[0].getScore());
					        
			blackPlayer.setText("" + players[1].getName());
			whitePlayer.setText("" + players[0].getName());
		}
	}
	
	public void showBoard(int[][] board) {
		int spacing = 2;
		int squareSize = (Main.SCREEN_WIDTH / board.length) - spacing - 45;
		
		boardPane.getChildren().clear();
		
		for(int col = 0; col < board.length; col++) {
			for(int row = 0; row < board[0].length; row++) {
				Button backgroundSquare = new Button();
				backgroundSquare.setPrefWidth(squareSize);
				backgroundSquare.setPrefHeight(squareSize);
				backgroundSquare.setStyle("-fx-background-color: darkseagreen; -fx-text-fill: white;");
				
				StackPane squareHolder = new StackPane(backgroundSquare);
				squareHolder.setPadding(new Insets(spacing));
				
				boardPane.add(squareHolder, row, col);
	
				if(board[col][row] == 1) {
					Circle blackCircle = new Circle(0, 0, squareSize / 2);
					boardPane.add(blackCircle, row, col);
				}
				
				if(board[col][row] == 2) {
					Circle whiteCircle = new Circle(0, 0, squareSize / 2);
					whiteCircle.setFill(Color.WHITE);
					boardPane.add(whiteCircle, row, col);
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

			if(commands[0].equals("login")) { // login <player name>
				if(commands[1] != null) {
					userController.notifyModel(Controller.LOG_IN, new String[] {commands[1], "localhost"});
				} else {
					System.out.println("Wrong command! Try: login <name>");
				}	
			}
			
			if(commands[0].equals("sub")) { // sub tictactoe or reversi
				if(commands[1] != null) {
					if(commands[1].equals("reversi")) {
						userController.notifyModel(Controller.SUBSCRIBE_TO_REVERSI, null);
					} else if(commands[1].equals("tictactoe")) {
						userController.notifyModel(Controller.SUBSCRIBE_TO_TICTACTOE, null);
					} else {
						
					}
				} else {
					System.out.println("Wrong command! Try: sub tictactoe or reversi");
				}
			}
			
			if(commands[0].equals("chal")) { // chal <player name> followed by tictactoe or reversi
				if(commands[1] != null && commands[2] != null) {
					userController.notifyModel(Controller.CHALLENGE_PLAYER, new String[] {commands[1], commands[2]});
				} else {
					System.out.println("Wrong command! Try: chal <player name> followed by tictactoe or reversi");
				}
			}
			
			if(commands[0].equals("accept")) { // accept <chal no>
				if(commands[1] != null) {
					userController.notifyModel(Controller.ACCEPT_CHALLENGE, new String[] {commands[1]});
				} else {
					System.out.println("Wrong command! Try: accept <chal no>");
				}
			}
			
			if(commands[0].equals("players")) { // accept <chal no>
				userController.notifyModel(Controller.REQUEST_PLAYERLIST, null);
			}

			if (textToSend.equals("exit")) {
				break;
			}
		}

		scanInput.close();
	}

}

