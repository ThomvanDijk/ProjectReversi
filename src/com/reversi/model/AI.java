package com.reversi.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import com.reversi.model.Game.GameType;
import com.reversi.model.Player.PlayerType;

public class AI {
	
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
		int bestMoveValue = -200;
		int bestMove = -1;
		
		// haal mogelijke zetten op
		ArrayList<ArrayList<Integer>> list = reversi.getValidMoves(b, player.id);
		
		int[] boardValue = areaValue(b, player);
		System.out.println(list);
		// kies zet met hoogste waarde
		for (int i = 0; i < list.size(); i++) {
			int move = list.get(i).get(0) + (list.get(i).get(1)*8);
			int value = boardValue[move];
			if (value > bestMoveValue) {
				bestMove = move;
				bestMoveValue = value;
			}
		}
		
		return bestMove;
	}
	
	int[] areaValue(Board b, Player player) {
		// geef waardes aan elke zet
		int[] boardValue = new int[64];
		
		int[][] board = b.getBoard();
		int i;
		int j;
		// hoeken
		i = 100;
		boardValue[0]= i;
		boardValue[7] = i;
		boardValue[56] = i;
		boardValue[63] = i;
		
		// vakken horizontaal of verticaal naast hoeken
		i = 2;
		j = -99;
		if (board[0][2] != player.opponent) {
			boardValue[1] = i;
		}
		else {
			boardValue[1] = j;
		}
		
		if (board[0][5] != player.opponent) {
			boardValue[6] = i;
		}
		else {
			boardValue[6] = j;
		}
		
		if (board[2][0] != player.opponent) {
			boardValue[8] = i;
		}
		else {
			boardValue[8] = j;
		}
		
		if (board[2][7] != player.opponent) {
			boardValue[15] = i;
		}
		else {
			boardValue[15] = j;
		}
		
		if (board[5][0] != player.opponent) {
			boardValue[48] = i;
		}
		else {
			boardValue[48] = j;
		}
		
		if (board[5][7] != player.opponent) {
			boardValue[55] = i;
		}
		else {
			boardValue[55] = j;
		}
		
		if (board[7][2] != player.opponent) {
			boardValue[57] = i;
		}
		else {
			boardValue[57] = j;
		}
		
		if (board[7][5] != player.opponent) {
			boardValue[62] = i;
		}
		else {
			boardValue[62] = j;
		}
		
		// vakken diagonaal van hoeken
		i = -100;
		j = 0;
		
		if (board[0][0] == 0) {
			boardValue[9] = i;
		}
		else {
			boardValue[9] = j;
		}
		
		if (board[0][7] == 0) {
			boardValue[14] = i;
		}
		else {
			boardValue[14] = j;
		}
		
		if (board[7][0] == 0) {
			boardValue[49] = i;
		}
		else {
			boardValue[49] = j;
		}

		if (board[7][7] == 0) {
			boardValue[54] = i;
		}
		else {
			boardValue[54] = j;
		}
		
		// vakken 2 stappen horizontaal of verticaal naast hoeken
		i = 8;
		boardValue[2] = i;
		boardValue[5] = i;
		boardValue[16] = i;
		boardValue[23] = i;
		boardValue[40] = i;
		boardValue[47] = i;
		boardValue[58] = i;
		boardValue[61] = i;
		
		// vakken 3 stappen horizontaal of verticaal naast hoeken
		i = 6;
		boardValue[3] = i;
		boardValue[4] = i;
		boardValue[24] = i;
		boardValue[31] = i;
		boardValue[32] = i;
		boardValue[39] = i;
		boardValue[59] = i;
		boardValue[60] = i;
		
		// vakken 2 stappen diagonaal van hoeken		
		i = 7;
		boardValue[18] = i;
		boardValue[21] = i;
		boardValue[42] = i;
		boardValue[45] = i;
		
		// vakken horizontaal of verticaal van de middelste 4 vakken
		i = 5;
		boardValue[19] = i;
		boardValue[20] = i;
		boardValue[26] = i;
		boardValue[29] = i;
		boardValue[34] = i;
		boardValue[37] = i;
		boardValue[43] = i;
		boardValue[44] = i;
		
		// vakken 2 stappen horizontaal of verticaal van de middelste 4 vakken
		i = 4;
		boardValue[11] = i;
		boardValue[12] = i;
		boardValue[25] = i;
		boardValue[30] = i;
		boardValue[33] = i;
		boardValue[38] = i;
		boardValue[51] = i;
		boardValue[52] = i;
		
		// Overig
		i = 3;
		boardValue[10] = i;
		boardValue[13] = i;
		boardValue[17] = i;
		boardValue[22] = i;
		boardValue[41] = i;
		boardValue[46] = i;
		boardValue[50] = i;
		boardValue[53] = i;
		
		return boardValue;
	}

	public int minimaxAvailableMoves(Board b, Player player, int depth, int max_depth, int chosen_score, int chosen_move){
		//int[][] backup = b.getBoard().clone();
		int boardSize = b.getBoardSize();
		int[][] currentBoard = b.getBoard();
		
		int[][] backup = new int[boardSize][boardSize];
		for (int i = 0; i < boardSize; i++) {
		  backup[i] = Arrays.copyOf(currentBoard[i], currentBoard[i].length);
		}
		
	    if (depth == max_depth) {
	        return 0;
	    }	    
	    else {
	    	int bestScore = 100;
	    	int bestMove = -1;
	    	
	    	ArrayList<ArrayList<Integer>> list = reversi.getValidMoves(b, player.id);
	    	ArrayList<ArrayList<Integer>> goodList = (ArrayList<ArrayList<Integer>>) list.clone();
	    	
	    	for(int i = list.size() -1; i > -1; i--) {
	    		int a = list.get(i).get(0) + (list.get(i).get(1) * 8);
	    		int c = areaValue(b, player)[a];
	    		if (c < -50) {
	    			goodList.remove(i);
	    		}
	    	}
	    	if (goodList.size() > 0) {
	    		list = goodList;
	    	}
	    	
	        if (list.size() == 0) {
	            return 0;
	        }
	        else {
	            for (int i = 0; i < (list.size()); i++) {	                
	                int move = list.get(i).get(0) + (list.get(i).get(1)*8);
	                reversi.makeForwardMove(player, move, b);
	                int score = reversi.getValidMoves(b, player.opponent).size();

	                Player nextPlayer;
	                if (player.id == 1) {
	                	nextPlayer = new Player(PlayerType.AI, 2);
	                }
	                else {
	                	nextPlayer = new Player(PlayerType.AI, 1);	           
	                }
	                nextPlayer.setTurn(true);
	                player.setTurn(false);
	                
	                minimaxAvailableMoves(b, nextPlayer, depth+1, max_depth, score, move);
	                int[][] restore = new int[boardSize][boardSize];
	        		for (int j = 0; j < boardSize; j++) {
	        		  restore[j] = Arrays.copyOf(backup[j], backup[j].length);
	        		}
	                b.setBoard(restore);
	                
	                if (bestMove == -1) {
	                	bestScore = score;
	                    bestMove = move;
	                }
	                else {
	                	if (depth%2 == 0) {
		                	if (score < bestScore){
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
	
	public int minimax(Board b, Player player, int depth, int max_depth, int chosen_score, int chosen_move){
		//int[][] backup = b.getBoard().clone();
		int boardSize = b.getBoardSize();
		int[][] currentBoard = b.getBoard();
		
		int[][] backup = new int[boardSize][boardSize];
		for (int i = 0; i < boardSize; i++) {
		  backup[i] = Arrays.copyOf(currentBoard[i], currentBoard[i].length);
		}
		
	    if (depth == max_depth) {
	        return 0;
	    }
	    
	    else {
	    	int bestScore = 100;
	    	int bestMove = -1;
	    	ArrayList<ArrayList<Integer>> list = reversi.getValidMoves(b, player.id);
	        if (list.size() == 0) {
	            return 0;
	        }
	        else {
	            for (int i = 0; i < (list.size()); i++) {	                
	                int move = list.get(i).get(0) + (list.get(i).get(1)*8);
	                reversi.makeForwardMove(player, move, b);
	                int score = reversi.calculateValueDiff(player.id);

	                Player nextPlayer;
	                if (player.id == 1) {
	                	nextPlayer = new Player(PlayerType.AI, 2);
	                }
	                else {
	                	nextPlayer = new Player(PlayerType.AI, 1);	           
	                }
	                nextPlayer.setTurn(true);
	                player.setTurn(false);
	                
	                minimax(b, nextPlayer, depth+1, max_depth, score, move);
	                int[][] restore = new int[boardSize][boardSize];
	        		for (int j = 0; j < boardSize; j++) {
	        		  restore[j] = Arrays.copyOf(backup[j], backup[j].length);
	        		}
	                b.setBoard(restore);
	                
	                if (bestMove == -1) {
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
