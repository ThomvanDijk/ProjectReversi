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

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Control;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

public class GameView extends View {
	
	private Scanner scanInput;
	private boolean consoleInput;
    private GridPane view ;


	public GameView(UserController userController) {
		super(userController);
	
		scanInput = new Scanner(System.in);
		consoleInput = false;
		
		// Examples notify Model
		//userController.notifyModel(Controller.LOG_IN, new String[] {"Naam", "localhost"});
		//userController.notifyModel(Controller.START_ONLINE_GAME, new String[] {"reversi"});
		//userController.notifyModel(Controller.CHALLENGE_PLAYER, new String[] {"Naam", "reversi"});
		//userController.notifyModel(Controller.ACCEPT_CHALLENGE, new String[] {"23"});
		//userController.notifyModel(Controller.REQUEST_PLAYERLIST, null);
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
		
		//gameModel.getBoard();
		//gameModel.getPlayer(); returns a player array
		//gameModel.getPlayerScores();
		//hasChallenge(); // returns boolean
		
		view = new GridPane();
        //draw board
        int[][] boardReversi = gameModel.getBoard();
        char ch = '1';
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++) {

                boardReversi[i][j] = ch++;
                StackPane square = new StackPane();

                square.setStyle("-fx-border-color: black");
                square.setStyle("-fx-background-color: green");


                if(boardReversi[i][j] == 1){
                    square.setStyle("-fx-background-color: black");
                }
                else if (boardReversi[i][j] == 2){
                    square.setStyle("-fx-background-color: white");
                }
                else {
                    //leeg laten
                }
                view.add(square, i, j);

            }
        }

        for (int i = 0; i < 8; i++) {
            view.getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            view.getRowConstraints().add(new RowConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }
	}
	
	public GridPane getView() {
        return view ;
    }
	
	/**
	 * This run function is used to refresh pages
	
	 * @param model Model
	 */
	@Override
	public void run() {
		// If needed this function keeps running until the thread is terminated
		
		if(consoleInput) {
			consoleInput();
			consoleInput = false;
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
					userController.notifyModel(Controller.START_ONLINE_GAME, new String[] {commands[1]});
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

