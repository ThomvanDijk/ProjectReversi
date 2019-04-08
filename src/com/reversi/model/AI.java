package com.reversi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.reversi.model.Game.GameType;
import com.reversi.model.Player.PlayerType;

public class AI {
	
	public static int maxLevel = 10; // negative to remove limit
	public static long maxTime = 8000; // milliseconds. stops tree expansion. takes some additional time to finalize calculations
	public static long startTime = 0;
	public static boolean cutOff = false;

	
	
	private TicTacToe ticTacToe;
	private static Reversi reversi;

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
	
	public int random(Board b, Player player) {
		// haal mogelijke zetten op
		ArrayList<ArrayList<Integer>> list = reversi.getValidMoves(b, player.id);
		
		// kies random zet uit mogelijke zetten
		Random rand = new Random();
		int n = rand.nextInt(((list.size())/2));
		int move = list.get(n).get(0) + (list.get(n).get(1)*8);
		System.out.println(move);
		return move;
	}
	
	public int boardWeighting(Board b, Player player) {
		int bestMoveValue = 0;
		int bestMove = -1;
		
		// haal mogelijke zetten op
		ArrayList<ArrayList<Integer>> list = reversi.getValidMoves(b, player.id);
		
		// geef waardes aan elke zet
		HashMap<Integer, Integer> boardValue = new HashMap<Integer, Integer>();
		int i;
		// hoeken
		i = 100;
		boardValue.put(0, i);
		boardValue.put(7, i);
		boardValue.put(56, i);
		boardValue.put(63, i);
		
		// vakken horizontaal of verticaal naast hoeken
		i = 2;
		boardValue.put(1, i);
		boardValue.put(6, i);
		boardValue.put(8, i);
		boardValue.put(15, i);
		boardValue.put(48, i);
		boardValue.put(55, i);
		boardValue.put(57, i);
		boardValue.put(62, i);
		
		// vakken diagonaal van hoeken
		i = 1;
		boardValue.put(9, i);
		boardValue.put(14, i);
		boardValue.put(49, i);
		boardValue.put(54, i);
		
		// vakken 2 stappen horizontaal of verticaal naast hoeken
		i = 8;
		boardValue.put(2, i);
		boardValue.put(5, i);
		boardValue.put(16, i);
		boardValue.put(23, i);
		boardValue.put(40, i);
		boardValue.put(47, i);
		boardValue.put(58, i);
		boardValue.put(61, i);
		
		// vakken 3 stappen horizontaal of verticaal naast hoeken
		i = 6;
		boardValue.put(3, i);
		boardValue.put(4, i);
		boardValue.put(24, i);
		boardValue.put(31, i);
		boardValue.put(32, i);
		boardValue.put(39, i);
		boardValue.put(59, i);
		boardValue.put(60, i);
		
		// vakken 2 stappen diagonaal van hoeken		
		i = 7;
		boardValue.put(18, i);
		boardValue.put(21, i);
		boardValue.put(42, i);
		boardValue.put(45, i);
		
		// vakken horizontaal of verticaal van de middelste 4 vakken
		i = 5;
		boardValue.put(19, i);
		boardValue.put(20, i);
		boardValue.put(26, i);
		boardValue.put(29, i);
		boardValue.put(34, i);
		boardValue.put(37, i);
		boardValue.put(43, i);
		boardValue.put(44, i);
		
		// vakken 2 stappen horizontaal of verticaal van de middelste 4 vakken
		i = 4;
		boardValue.put(11, i);
		boardValue.put(12, i);
		boardValue.put(25, i);
		boardValue.put(30, i);
		boardValue.put(33, i);
		boardValue.put(38, i);
		boardValue.put(51, i);
		boardValue.put(52, i);
		
		// Overig
		i = 3;
		boardValue.put(10, i);
		boardValue.put(13, i);
		boardValue.put(17, i);
		boardValue.put(22, i);
		boardValue.put(41, i);
		boardValue.put(46, i);
		boardValue.put(50, i);
		boardValue.put(53, i);
		
		// kies zet met hoogste waarde
		for (i = 0; i < list.size(); i++) {
			int move = list.get(i).get(0) + (list.get(i).get(1)*8);
			int value = boardValue.get(move);
			if (value > bestMoveValue) {
				bestMove = move;
				bestMoveValue = value;
			}
		}
		
		return bestMove;
	}
	
	public int minimax(Board b, Player player, int depth, int max_depth, int chosen_score, int chosen_move){
		int[][] backup = b.getBoard();
	    if (depth == max_depth) {
	    	System.out.println("Reached end depth");
	        return 0;
	    }
	    
	    else {
	    	 int opponentID = 0;
	    	 if (player.id == 1) {
	         	opponentID = 2;
	         }
	         else {
	         	opponentID = 1;
	         }
	    	int bestScore = 100;
	    	int bestMove = -1;
	    	ArrayList<ArrayList<Integer>> list = reversi.getValidMoves(b, player.id);
	        if (list.size() == 0) {
	            return 0;
	        }
	        else {
	            for (int i = 0; i < (list.size()/2); i++) {	                
	                int move = list.get(i).get(0) + (list.get(i).get(1)*8);
	                //int score = reversi.calculateValueDiff(player.id);
	                int score = 0;
	                for (int j = 0; j<b.getBoardSize(); j++) {
	                	for (int n = 0; n<b.getBoardSize(); n++) {
	                		if (backup[j][n] == player.id) {
	                			score++;
	                		} else if(backup[j][n] == opponentID){
	                			score--;
	                		}
	                	}
	                }
	                System.out.println(score);

	                Player nextPlayer;
	                if (player.id == 1) {
	                	nextPlayer = new Player(PlayerType.AI, 2);
	                }
	                else {
	                	nextPlayer = new Player(PlayerType.AI, 1);
	                }
	                
	                minimax(b, nextPlayer, depth+1, max_depth, score, move);
	                if (bestMove == -1) {
	                	bestScore = score;
	                    bestMove = move;
	                }
	                else {
	                	if (depth%2 == 0) {
		                	if (score > bestScore){
		                		System.out.print("Best score is: "+score);
			                    bestScore = score;
			                    bestMove = move;
			                }
		                }
		                else {
		                	if (score < bestScore){
			                    bestScore = score;
			                    bestMove = move;
			                }
		                }
	                }		        	                
	            }
	            chosen_score = bestScore;
	            chosen_move = bestMove;
	        }
	    }
	    b.setBoard(backup);
	    return chosen_move;
	}


}
