package com.reversi.controller;

import com.reversi.model.Model;

/**
 * This abstract Controller class is used to define notificationID's and to set
 * the Model.
 * 
 * @author  Thom van Dijk
 * @version 1.0
 * @since   16-04-2019
 */
public abstract class Controller {
	
	public static final int START_REVERSI_SINGLEPLAYER 	 = 1;
	public static final int START_TICTACTOE_SINGLEPLAYER = 2;
	public static final int SUBSCRIBE_TO_REVERSI 	 	 = 3;
	public static final int SUBSCRIBE_TO_TICTACTOE	 	 = 4;
	public static final int START_ONLINE_GAME 			 = 5;
	public static final int END_GAME				     = 6;
	public static final int CHALLENGE_PLAYER	    	 = 7;  
	public static final int REQUEST_MOVE				 = 8; 
	public static final int PROCESS_NEW_CHALLENGE	     = 9;
	public static final int LOG_IN                       = 10;  
	public static final int LOG_OUT                      = 11;
	public static final int SET_MOVE	             	 = 12; 
	public static final int ACCEPT_CHALLENGE             = 13; 
	public static final int REQUEST_PLAYERLIST           = 14; 
	public static final int CANCEL_CHALLENGE			 = 15;
	
	protected final Model model;

	/**
	 * Attach GameModel to Controller via constructor.
	 * 
	 * @param model The Model that needs to be called from the Controllers.
	 */
	public Controller(Model model) {
		this.model = model;
	}
	
	/**
	 * This function is used to determine which functions must be called in
	 * GameModel.
	 * 
	 * @param notificationID An integer value representing a certain action.
	 * @param arguments      String of arguments which is sometimes needed in
	 *                       combination with a certain notificationID.
	 */
	public abstract void notifyModel(int notificationID, String[] arguments);

}
