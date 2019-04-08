/**
* Abstract class for GameView to hide some functions that GameView doesn't need to know. 
* 
* @author Thom van Dijk
* @version 1.0
* @since 08-04-2019
*/

package com.reversi.view;

import com.reversi.controller.UserController;
import com.reversi.model.GameModel;
import com.reversi.model.Model;

public class GameView extends View {

	public GameView(UserController userController, String[] args) {
		super(userController);
		
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

		//userController.notifyModel(notification_id, argument);

		Window window = new Window();
		window.rmain(args);
	}
	
	/**
	 * This run function is used to refresh pages e.d.
	 *
	 * @param model Model
	 */
	@Override
	public void run() {
		// If needed this function keeps running until the thread is terminated
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
	}

}
