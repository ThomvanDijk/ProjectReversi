package com.reversi.model;

import java.util.Random;

public class AI {
	
	private TicTacToe ticTacToe;
	private Reversi reversi;

	public AI() {
		
	}
	
	public void setTicTacToe(TicTacToe ticTacToe) {
		this.ticTacToe = ticTacToe;
	}
	
	public void setReversi(Reversi reversi) {
		this.reversi = reversi;
	}
	
	// Calculate the best move for player
	public int calculateMove(Board board, Player player) {
		Random rand = new Random();
		int move = 0;
		
		if(board.emptyPlaces()) {
			while(!ticTacToe.isValidMove(move, player.id)) {
				// between 0 and board.getBoardSize() * 2 (exclusive)
				move = rand.nextInt(board.getBoardSize() * board.getBoardSize());
				System.out.println("AI check move: " + move);
			}
		}
	
		return move;
	}

}
