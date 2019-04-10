package com.reversi.controller;

import com.reversi.model.Model;

public abstract class Controller {
	
	public static final int START_REVERSI_SINGLEPLAYER   = 1;
	public static final int START_TICTACTOE_SINGLEPLAYER = 2;
	public static final int END_REVERSI_SINGLEPLAYER     = 3;
	public static final int END_TICTACTOE_SINGLEPLAYER   = 4;
	public static final int START_ONLINE_GAME	    	 = 5;  // args { "GameType" } "reversi" or "tictactoe"
	public static final int REQUEST_MOVE				 = 6;
	public static final int END_ONLINE_GAME		      	 = 7;  // args { "GameType" } "reversi" or "tictactoe"
//	public static final int END_TICTACTOE_MULTIPLAYER    = 8;
	public static final int LOG_IN                       = 9;  // args { "Name" }
	public static final int LOG_OUT                      = 10;
	public static final int CHALLENGE_PLAYER             = 11; // args { "PlayerName", "GameType" } GameType: REVERSI TICTACTOE
	public static final int OTHER_DID_MOVE	             = 12; // args { "Value between 0 and board^2" }
//	public static final int SET_MOVE_TICTACTOE           = 13; // args { "Value between 0 and board^2" }
	
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
