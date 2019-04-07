package com.reversi.model;

import java.util.ArrayList;
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
	
	public int calculateMove2(Board b, Player player) {
		ArrayList<ArrayList<Integer>> list = reversi.getValidMoves(b, player.id);
		System.out.println(list);
		Random rand = new Random();
		int n = rand.nextInt(((list.size())/2));
		int move = list.get(n).get(0) + (list.get(n).get(1)*8);
		System.out.println(move);
		return move;
	}
	
	public int minimax(Board b, Player player, int depth, int max_depth, int chosen_score, int chosen_move){
		
	    if (depth == max_depth) {
	    	System.out.println("Hey ik ben klaar baby");
	        return chosen_move;
	    }
	    
	    else {
	    	int bestScore = 100;
	    	int bestMove = 0;
	    	ArrayList<ArrayList<Integer>> list = reversi.getValidMoves(b, player.id);
	        if (list.size() == 0) {
	            return 0;
	        }
	        else {
	            for (int i = 0; i < (list.size()/2); i++) {	                
	                int move = list.get(i).get(0) + (list.get(i).get(1)*8);
	                int score = reversi.calculateValueDiff(player.id);
	                Board newBoard = new Board(8, GameType.REVERSI);
	                newBoard = reversi.makeMove(player, move, b);
	                Board copyBoard = new Board(8, GameType.REVERSI);
	                try {
						copyBoard = (Board) newBoard.clone();
					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                Player nextPlayer;
	                if (player.id == 1) {
	                	nextPlayer = new Player(PlayerType.AI, 2);
	                }
	                else {
	                	nextPlayer = new Player(PlayerType.AI, 1);
	                }
	                
	                minimax(copyBoard, nextPlayer, depth+1, max_depth, score, move);
	                if (bestMove == 0) {
	                	bestScore = score;
	                    bestMove = move;
	                }
	                else {
	                	if (depth%2 == 0) {
		                	if (score > bestScore){
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
	    return chosen_move;
	}


}
