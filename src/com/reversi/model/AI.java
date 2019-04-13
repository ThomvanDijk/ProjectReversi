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
	
	public ArrayList<ArrayList<Integer>> removeBadMoves(ArrayList<ArrayList<Integer>> list, Board b, Player player) {
		int boardSize = b.getBoardSize();
		int[][] currentBoard = b.getBoard();
		int[][] backup = new int[boardSize][boardSize];
		for (int i = 0; i < boardSize; i++) {
			backup[i] = Arrays.copyOf(currentBoard[i], currentBoard[i].length);
		}
		
		// List with good moves
		ArrayList<ArrayList<Integer>> newList = new ArrayList<ArrayList<Integer>>();
		
		for (int i = 0; i < list.size(); i++) {
			boolean goodMoveLeft = true;
			int move = list.get(i).get(0) + (list.get(i).get(1)*8);
			
			ArrayList<ArrayList<Integer>> opponentMoves = reversi.getValidMoves(reversi.makeForwardMove(player, move, b), player.opponent);
			
			for (ArrayList<Integer> x : opponentMoves)
			{
			   int check = x.get(0) + (x.get(1)*8);
			   if (check == 0 || check == 7 || check == 56 || check == 63) {				
					goodMoveLeft = false;
				}

			}
			if (goodMoveLeft == true){
				newList.add(list.get(i));
			}
			int[][] restore = new int[boardSize][boardSize];
			for (int j = 0; j < boardSize; j++) {
			  restore[j] = Arrays.copyOf(backup[j], backup[j].length);
			}
	        b.setBoard(restore);				
		}

		return newList;

	}
	public int boardWeighting(Board b, Player player) {
		int bestMoveValue = -200;
		int bestMove = -1;
		
		// haal mogelijke zetten op
		ArrayList<ArrayList<Integer>> list = reversi.getValidMoves(b, player.id);
		
		int[] boardValue = areaValue(b, player);
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
	public int blockingMove(ArrayList<ArrayList<Integer>> list, Board b, Player player) {
		int boardSize = b.getBoardSize();
		int[][] currentBoard = b.getBoard();
		int[][] backup = new int[boardSize][boardSize];
		for (int i = 0; i < boardSize; i++) {
			backup[i] = Arrays.copyOf(currentBoard[i], currentBoard[i].length);
		}
		 
		boolean block = false;
		for (int i = 0; i < list.size(); i++) {
			int move = list.get(i).get(0) + (list.get(i).get(1)*8);
			if(reversi.getValidMoves(reversi.makeForwardMove(player, move, b), player.opponent).size() == 0){
				System.out.println("You got blocked, son");
				block = true;
			}
			int[][] restore = new int[boardSize][boardSize];
			for (int j = 0; j < boardSize; j++) {
			  restore[j] = Arrays.copyOf(backup[j], backup[j].length);
			}
	        b.setBoard(restore);	
			if (block == true) {
				return move;
			}
		}
		return -1;
	}
	
	int[] areaValue(Board b, Player player) {
		// geef waardes aan elke zet
		int[] boardValue = new int[64];
		
		int[][] board = b.getBoard();
		int i;
		int j;
		int k;
		// hoeken
		i = 100;
		boardValue[0]= i;
		boardValue[7] = i;
		boardValue[56] = i;
		boardValue[63] = i;
		
		// vakken horizontaal of verticaal naast hoeken
		i = 2;
		j = -99;
		k = 51;
		
		/*if (board[0][2] == player.opponent && board[0][3] == player.id) {
			boardValue[1] = k;
		}			
		else
		if (board[0][2] == player.id) {
			boardValue[1] = i;
		}
		else {
			boardValue[1] = j;
		}*/
		
		if (board[0][2] == player.id) {
			boardValue[1] = i;
		}
		else {
			boardValue[1] = j;
		}
		
		if (board[0][5] == player.id) {
			boardValue[6] = i;
		}
		else {
			boardValue[6] = j;
		}
		
		if (board[2][0] == player.id) {
			boardValue[8] = i;
		}
		else {
			boardValue[8] = j;
		}
		
		if (board[2][7] == player.id) {
			boardValue[15] = i;
		}
		else {
			boardValue[15] = j;
		}
		
		if (board[5][0] == player.id) {
			boardValue[48] = i;
		}
		else {
			boardValue[48] = j;
		}
		
		if (board[5][7] == player.id) {
			boardValue[55] = i;
		}
		else {
			boardValue[55] = j;
		}
		
		if (board[7][2] == player.id) {
			boardValue[57] = i;
		}
		else {
			boardValue[57] = j;
		}
		
		if (board[7][5] == player.id) {
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

	public int minimaxAvailableMoves(Board b, Player player, int depth, int maxDepth, int chosenScore, int chosenMove, int alpha, int beta){
		ArrayList<ArrayList<Integer>> list = reversi.getValidMoves(b, player.id);
    	
		if (depth == 0) {
			// remove bad moves
			ArrayList<ArrayList<Integer>> goodList = removeBadMoves(list, b, player);
			if (goodList.size() > 0) {
		    	list = goodList;
			}
			
	    	// see if there are any really good moves
			boolean goodMove = false;
			int bestAreaScore = 49;
			int bestAreaMove = -2;
	    	for(int q = list.size() -1; q > -1; q--) {
	    		int a = list.get(q).get(0) + (list.get(q).get(1) * 8);
	    		int c = player.ai.areaValue(b, player)[a];
	    		if (c > 50) {
	    			if (goodMove == true) {
	    				if (c > bestAreaScore) {
	    					bestAreaMove = a;
			    			bestAreaScore = c;
	    				}
	    			} else {
		    			System.out.println("We got a good spot, boss: "+a);
		    			goodMove = true;
		    			bestAreaMove = a;
		    			bestAreaScore = c;
	    			}
	    		}
	    	}
	    	if (goodMove == true) {
	    		return bestAreaMove;
	    	}
		}
		
		// Make backup of current board state
		int boardSize = b.getBoardSize();
		int[][] currentBoard = b.getBoard();
		
		int[][] backup = new int[boardSize][boardSize];
		for (int i = 0; i < boardSize; i++) {
		  backup[i] = Arrays.copyOf(currentBoard[i], currentBoard[i].length);
		}
		
	    if (depth == maxDepth) {
	    	ArrayList<ArrayList<Integer>> goodList = (ArrayList<ArrayList<Integer>>) list.clone();
	    	
	    	for(int i = list.size() -1; i > -1; i--) {
	    		int a = list.get(i).get(0) + (list.get(i).get(1) * 8);
	    		int c = areaValue(b, player)[a];
	    		if (c < -50) {
	    			goodList.remove(i);
	    		}
	    	}
	    	int currentScore = removeBadMoves(list, b, player).size();
	        return 100 * (currentScore - chosenScore) / (currentScore + chosenScore + 1);
	    }	    
	    else {
	    	int bestScore = 100;
	    	int bestMove = -1;
  	
	    	
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
	        	ArrayList<ArrayList<Integer>> tempList = (ArrayList<ArrayList<Integer>>) list.clone();
		    	
		    	for(int i = list.size() -1; i > -1; i--) {
		    		int a = list.get(i).get(0) + (list.get(i).get(1) * 8);
		    		int c = areaValue(b, player)[a];
		    		if (c < -50) {
		    			tempList.remove(i);
		    		}
		    	}
		    	int currentScore = removeBadMoves(list, b, player).size();
		        return 100 * (currentScore - chosenScore) / (currentScore + chosenScore + 1);
	        		
	        }
	        else {
	            for (int i = 0; i < (list.size()); i++) {	                
	                int move = list.get(i).get(0) + (list.get(i).get(1)*8);
	                reversi.makeForwardMove(player, move, b);
	                int currentscore = reversi.getValidMoves(b, player.id).size();
	
	                Player nextPlayer;
	                if (player.id == 1) {
	                	nextPlayer = new Player(PlayerType.AI, 2);
	                }
	                else {
	                	nextPlayer = new Player(PlayerType.AI, 1);	           
	                }
	                nextPlayer.setTurn(true);
	                player.setTurn(false);
	                
	                int score = minimaxAvailableMoves(b, nextPlayer, depth+1, maxDepth, currentscore, move, alpha, beta);
	                int[][] restore = new int[boardSize][boardSize];
	        		for (int j = 0; j < boardSize; j++) {
	        		  restore[j] = Arrays.copyOf(backup[j], backup[j].length);
	        		}
	                b.setBoard(restore);
	                
	                if (bestMove == -1) {
	                	bestScore = score;
	                    bestMove = move;
	                }
	                	                	
                	if (depth%2 == 0) {
	                	if (score > bestScore){
		                    bestScore = score;
		                    bestMove = move;
		                }
	                	if(score > alpha) {
	                		alpha = score;
	                	}
	                	if(beta <= alpha) {
	                		break;
	                	}
	                }
	                else {
	                	if (score < bestScore){
		                    bestScore = score;
		                    bestMove = move;
		                }
	                	if(score < beta) {
	                		beta = score;
	                	}
	                	if(beta <= alpha) {
	                		break;
	                	}
	                }
		                
	                
	            }	            
	            chosenScore = bestScore;
	            chosenMove = bestMove;
	        }

	    }
	    /*if (chosenMove == -1) {
	    	return boardWeighting(b, player);
	    }*/
	    if (depth != 0) {
	    	return chosenScore;
	    } 
	    else {  
	    	return chosenMove;
	    }
	}
	
	public int minimax(Board b, Player player, int depth, int maxDepth, int chosenScore, int chosenMove, int alpha, int beta){
		//int[][] backup = b.getBoard().clone();
		int boardSize = b.getBoardSize();
		int[][] currentBoard = b.getBoard();
		
		int[][] backup = new int[boardSize][boardSize];
		for (int i = 0; i < boardSize; i++) {
		  backup[i] = Arrays.copyOf(currentBoard[i], currentBoard[i].length);
		}
		
	    if (depth == maxDepth) {
	    	reversi.calculateValueDiff(player.id);
	    }
	    
	    else {
	    	int bestScore = 100;
	    	int bestMove = -1;
	    	ArrayList<ArrayList<Integer>> list = reversi.getValidMoves(b, player.id);
	        if (list.size() == 0) {
	            return reversi.calculateValueDiff(player.id);
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
	                
	                score = minimax(b, nextPlayer, depth+1, maxDepth, score, move, alpha, beta);
	                int[][] restore = new int[boardSize][boardSize];
	        		for (int j = 0; j < boardSize; j++) {
	        		  restore[j] = Arrays.copyOf(backup[j], backup[j].length);
	        		}
	                b.setBoard(restore);
	                
	                if (bestMove == -1) {
	                	bestScore = score;
	                    bestMove = move;
	                }
	                
                	if (depth%2 == 0) {
	                	if (score > bestScore){
		                    bestScore = score;
		                    bestMove = move;
		                }
	                	if(score > alpha) {
	                		alpha = score;
	                	}
	                	if(beta <= alpha) {
	                		break;
	                	}
	                }
	                else {
	                	if (score < bestScore){
		                    bestScore = score;
		                    bestMove = move;
		                }
	                	if(score < beta) {
	                		beta = score;
	                	}
	                	if(beta <= alpha) {
	                		break;
	                	}
	                }
	                		        	                
	            }
	            
	            chosenScore = bestScore;
	            chosenMove = bestMove;
	        }
	    }
	    if (depth != 0) {
	    	return chosenScore;
	    }
	    else {
	    	return chosenMove;
	    }
	}
	
	public int minimaxTest(Board b, Player player, int depth, int maxDepth, int chosenScore, int chosenMove, int alpha, int beta){
		ArrayList<ArrayList<Integer>> list = reversi.getValidMoves(b, player.id);
    	
		if (depth == 0) {
			// remove bad moves
			ArrayList<ArrayList<Integer>> goodList = removeBadMoves(list, b, player);
			if (goodList.size() > 0) {
		    	list = goodList;
			}
			
	    	// see if there are any really good moves
			boolean goodMove = false;
			int bestAreaScore = 49;
			int bestAreaMove = -2;
	    	for(int q = list.size() -1; q > -1; q--) {
	    		int a = list.get(q).get(0) + (list.get(q).get(1) * 8);
	    		int c = player.ai.areaValue(b, player)[a];
	    		if (c > 50) {
	    			if (goodMove == true) {
	    				if (c > bestAreaScore) {
	    					bestAreaMove = a;
			    			bestAreaScore = c;
	    				}
	    			} else {
		    			System.out.println("We got a good spot, boss: "+a);
		    			goodMove = true;
		    			bestAreaMove = a;
		    			bestAreaScore = c;
	    			}
	    		}
	    	}
	    	if (goodMove == true) {
	    		return bestAreaMove;
	    	}
		}
		
		// Make backup of current board state
		int boardSize = b.getBoardSize();
		int[][] currentBoard = b.getBoard();
		
		int[][] backup = new int[boardSize][boardSize];
		for (int i = 0; i < boardSize; i++) {
		  backup[i] = Arrays.copyOf(currentBoard[i], currentBoard[i].length);
		}
		
	    if (depth == maxDepth) {
	    	return reversi.calculateValueDiff(player.id);
	    }	    
	    else {
	    	int bestScore = 100;
	    	int bestMove = -1;
  	
	    	
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
	        	return reversi.calculateValueDiff(player.id);
	        		
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
	                
	                score = minimaxAvailableMoves(b, nextPlayer, depth+1, maxDepth, score, move, alpha, beta);
	                int[][] restore = new int[boardSize][boardSize];
	        		for (int j = 0; j < boardSize; j++) {
	        		  restore[j] = Arrays.copyOf(backup[j], backup[j].length);
	        		}
	                b.setBoard(restore);
	                
	                if (bestMove == -1) {
	                	bestScore = score;
	                    bestMove = move;
	                }
	                	                	
                	if (depth%2 == 0) {
	                	if (score > bestScore){
		                    bestScore = score;
		                    bestMove = move;
		                }
	                	if(score > alpha) {
	                		alpha = score;
	                	}
	                	if(beta <= alpha) {
	                		break;
	                	}
	                }
	                else {
	                	if (score < bestScore){
		                    bestScore = score;
		                    bestMove = move;
		                }
	                	if(score < beta) {
	                		beta = score;
	                	}
	                	if(beta <= alpha) {
	                		break;
	                	}
	                }
		                
	                
	            }	            
	            chosenScore = bestScore;
	            chosenMove = bestMove;
	        }

	    }
	    /*if (chosenMove == -1) {
	    	return boardWeighting(b, player);
	    }*/
	    if (depth != 0) {
	    	return chosenScore;
	    } 
	    else {  
	    	return chosenMove;
	    }
	}
}
