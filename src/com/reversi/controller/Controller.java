package com.reversi.controller;

import com.reversi.model.Model;

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
	public static final int SET_MOVE	             = 12; 
	public static final int ACCEPT_CHALLENGE             = 13; 
	public static final int REQUEST_PLAYERLIST           = 14; 
	public static final int CANCEL_CHALLENGE			 = 15;
	
	protected final Model model; // Can and must only have one model
	
	/**
	 * Attach model to controller via constructor
	 * 
	 * @param model 
	 */
	public Controller(Model model) {
		this.model = model;
	}
	
	/**
	 * This function is used to let the model know there are some new updates
	 * 
	 * @param notification_id
	 * @param argument The argument can be null but for is needed for some notifications
	 */
	public abstract void notifyModel(int notification_id, String[] arguments);
	
}
