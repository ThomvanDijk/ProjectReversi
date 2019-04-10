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

public class GameView extends View {
	
	private Scanner scanInput;
	private boolean running;

	public GameView(UserController userController, String[] args) {
		super(userController);
	
		scanInput = new Scanner(System.in);
		running = true;
		
		// Below are all the current notification id's you can use as argument in the function
		// userController.notifyModel(notification_id, argument) The argument is used in case
		// you give a new board position or when you login.
		
		// START_REVERSI_SINGLEPLAYER   
		// START_TICTACTOE_SINGLEPLAYER 
		// END_REVERSI_SINGLEPLAYER     
		// END_TICTACTOE_SINGLEPLAYER   
		// START_REVERSI_MULTIPLAYER    
		// START_TICTACTOE_MULTIPLAYER  
		// END_REVERSI_MULTIPLAYER      
		// END_TICTACTOE_MULTIPLAYER    
		// LOG_IN  						// args { "Name" }                                              
		// LOG_OUT                                                                                      
		// CHALLENGE_PLAYER		        // args { "PlayerName", "GameType" } GameType.REVERSI GameType.TICTACTOE
		// SET_MOVE_REVERSI             // args { "Value between 0 and board^2" }                       
		// SET_MOVE_TICTACTOE           // args { "Value between 0 and board^2" }                       

		//userController.notifyModel(notification_id, arguments); // Argument is a String array
		
		// Example
		//userController.notifyModel(Controller.LOG_IN, new String[] {"Naam", "localhost"});

		//Window window = new Window();
		//window.rmain(args);
	}
	
	/**
	 * This run function is used to refresh pages
	 *
	 * @param model Model
	 */
	@Override
	public void run() {
		// If needed this function keeps running until the thread is terminated
		
		if(running) {
			consoleInput();
			running = false;
		}
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
					System.out.println("Wrong command! Try: chal <player name> followed by tictactoe or reversi");
				}
			}

			// Display text to the text area
			System.out.println("Client: " + textToSend + "\n");

			if (textToSend.equals("exit")) {
				break;
			}
		}

		scanInput.close();
	}

}
