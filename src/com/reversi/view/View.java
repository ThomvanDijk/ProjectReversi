package com.reversi.view;

import java.util.List;

import com.reversi.controller.UserController;

public abstract class View implements Runnable {
	
	private final UserController userController;
	
	public View(UserController userController) {
		this.userController = userController;
	}
	
	// The following functions will only be accessible by model
	
	// This function takes an updated board from model
	public void updateBoard(int[][] board) {
		
	}
	
	// Gives an int array of scores, [0] is player1 and [1] is player2
	public void updatePlayerScores(int[] playerScores) {
		
	}
	
	// Notifies that there is a player that want's to challenge you
	public void requestChallenge() {
		
	}
	
	// Gives a list of players online
	public void updatePlayerList() {
		
	}
	
}
