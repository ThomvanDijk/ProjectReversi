package com.reversi.model;

import java.util.ArrayList;
import java.util.List;

import com.reversi.controller.*;
import com.reversi.view.*;

public class GameModel extends Model {

	private boolean running;
	private TicTacToe ticTacToe;
	private Reversi reversi;
	private List<List<Integer>> board;

	public GameModel() {
		running = true;
		ticTacToe = new TicTacToe();
		//reversi = new Reversi();
		board = new ArrayList<List<Integer>>();
	}

	public void update(int serverMove, int enemyScore, int playerScore) {
		// TODO implement
	}
	
	public void boardConverter() {
		
	}

	@Override
	public void run() {
		while(this.running) {
			//ticTacToe.update();
			//reversi.start();
		}
	}

}