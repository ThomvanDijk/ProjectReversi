package com.reversi.model;

import java.util.Scanner;

import com.reversi.controller.*;
import com.reversi.main.Main;

public class TicTacToe extends Game {
	
	private Player player1;
	private Player player2;
	private Scanner scanInput;
	private String data;

	public TicTacToe() {
		super(GameType.TICTACTOE);
		
		player1 = new Player();
		player2 = new Player();
		
		player1.setTurn(true);
		player2.setTurn(false);
		
		scanInput = new Scanner(System.in);
		//data = scanInput.nextLine();
		consoleInput();
	}
	
	public void consoleInput() {
		System.out.println("--------------");
		System.out.println("x has the turn");
		
		while (scanInput.hasNextLine()) {
			//System.out.println(scanInput.nextLine());
			//int place = scanInput.nextInt();
			
			
			
			if(player1.hasTurn()) {
				board.setMove(scanInput.nextInt(), 1);
				player1.setTurn(false);
				player2.setTurn(true);
				
				System.out.println("--------------");
				System.out.println("o has the turn");
			} else {
				board.setMove(scanInput.nextInt(), 2);
				player1.setTurn(true);
				player2.setTurn(false);
				
				System.out.println("--------------");
				System.out.println("x has the turn");
			}

		}

		if(!Main.running) {
			scanInput.close();
		}
	}
	
	public void update() {

	}

}
