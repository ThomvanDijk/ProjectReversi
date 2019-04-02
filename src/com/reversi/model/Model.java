package com.reversi.model;

import java.util.ArrayList;
import java.util.List;

import com.reversi.controller.*;
import com.reversi.view.*;

public class Model implements Runnable {
	
	private View mainView;
	private Controller userController;
	private Controller serverController;
	private boolean running;
	private TicTacToe ticTacToe;
	private Reversi reversi;
	private List<List<Integer>> board;

	public Model() {
		running = true;
		ticTacToe = new TicTacToe();
		reversi = new Reversi();
		board = new ArrayList<List<Integer>>();
	}
	
	public void setView(View view) {
		if (this.mainView!=null) {
			throw new IllegalStateException("View already set.");
		}
		this.mainView = view;
	}
	
	public void setUserController(Controller userController) {
		if (this.userController!=null) {
			throw new IllegalStateException("UserController already set.");
		}
		this.userController = userController;
	}
	
	public void setServerController(Controller serverController) {
		if (this.serverController!=null) {
			throw new IllegalStateException("ServerController already set.");
		}
		this.serverController = serverController;
	}
	
	public void update(int serverMove, int enemyScore, int playerScore) {
		// TODO implement
	}
	
	public void boardConverter() {
		
	}

	@Override
	public void run() {
		while(running) {
			//ticTacToe.start();
			//reversi.start();
		}
	}

}
