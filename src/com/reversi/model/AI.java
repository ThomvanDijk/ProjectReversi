package com.reversi.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import com.reversi.model.Game.GameType;
import com.reversi.model.Player.PlayerType;

/**
 * The AI class does the calculating work to see which
 * move should be played to increase the odds of winning
 * the current game played.
 * 
 * @author  Sebastiaan van Vliet <SebastiaanVliet@gmail.com>
 * @version 1.0
 * @since   1.0
 */

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
	
	/**
	 * Deze functie berekent wat de beste zet is voor TicTacToe.
	 * 
	 * @param board  Het huidige bord
	 * @param player De speler die een zet wilt doen
	 * @return
	 */
	public int calculateMove(Board board, Player player) {
		Random rand = new Random();
		int move = 0;
		
		if(board.emptyPlaces()) {
			while(!ticTacToe.isValidMove(move, player.color)) {
				// between 0 and board.getBoardSize() * 2 (exclusive)
				move = rand.nextInt(board.getBoardSize() * board.getBoardSize());
				System.out.println("AI check move: " + move);
			}
		}
	
		return move;
	}
	
	/**
	 * Deze functie doet een random zet op het veld. Dit doet dit
	 * algoritme door de valide zetten op te vragen en van die zetten
	 * een willekeurige waarde te pakken.
	 * 
	 * @param b 		Het huidige bord
	 * @param player	De speler die een zet wilt doen
	 * @return			De zet die berekend is
	 */
	public int random(Board b, Player player) {
		// haal mogelijke zetten op
		ArrayList<ArrayList<Integer>> list = reversi.getValidMoves(b, player.color);
		
		// kies random zet uit mogelijke zetten
		Random rand = new Random();
		int n = rand.nextInt(((list.size())/2));
		int move = list.get(n).get(0) + (list.get(n).get(1)*8);
		System.out.println(move);
		return move;
	}
	
	/**

	 */
	public ArrayList<ArrayList<Integer>> removeBadMoves(ArrayList<ArrayList<Integer>> list, Board b, Player player) {
		int boardSize = b.getBoardSize();
		int[][] currentBoard = b.getBoard();
		// Backup maken van het bord
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
			// Goede move toevoegen aan lijst
			if (goodMoveLeft == true){
				newList.add(list.get(i));
			}
			// Bord naar oude staat terug brengen
			int[][] restore = new int[boardSize][boardSize];
			for (int j = 0; j < boardSize; j++) {
			  restore[j] = Arrays.copyOf(backup[j], backup[j].length);
			}
	        b.setBoard(restore);				
		}

		return newList;

	}
	/**
	 * Deze functie berekent de beste zet, op basis van de boardweight
	 * 
	 * @param b 		Het huidige bord
	 * @param player	De speler die een zet wilt doen
	 * @return			De zet die berekend is
	 */
	public int boardWeighting(Board b, Player player) {
		int bestMoveValue = -200;
		int bestMove = -1;
		
		// haal mogelijke zetten op
		ArrayList<ArrayList<Integer>> list = reversi.getValidMoves(b, player.color);
		
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
	/**
	 * Deze functie berekend of het mogelijk is om een zet te doen die er voor zorgt
	 * dat de tegenstander geen mogelijke zetten meer heeft.
	 * 
	 * @param list		Lijst met mogelijke zetten
	 * @param b 		Het huidige bord
	 * @param player	De speler die een zet wilt doen
	 * @return			De zet die berekend is, of -1 als er
	 * 					geen zet gevonden is.
	 */
	public int blockingMove(ArrayList<ArrayList<Integer>> list, Board b, Player player) {
		int boardSize = b.getBoardSize();
		int[][] currentBoard = b.getBoard();
		// Backup van het bord maken
		int[][] backup = new int[boardSize][boardSize];
		for (int i = 0; i < boardSize; i++) {
			backup[i] = Arrays.copyOf(currentBoard[i], currentBoard[i].length);
		}
		 
		boolean block = false;
		// Kijk of er zetten zijn die er voor zorgen dat de tegenstander geen zet kan doen de volgende beurt
		for (int i = 0; i < list.size(); i++) {
			int move = list.get(i).get(0) + (list.get(i).get(1)*8);
			if(reversi.getValidMoves(reversi.makeForwardMove(player, move, b), player.opponent).size() == 0){
				System.out.println("You got blocked, son");
				block = true;
			}
			// Zet het bord terug in de oude staat
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
	
	/**
	 * Deze functie verandert de waardes van de boardweight afhankelijk van de stand van het bord.
	 * 
	 * @param b 		Het huidige bord
	 * @param player	De speler die aan zet is
	 * @return			De waardes van elk bord vak
	 */
	int[] areaValue(Board b, Player player) {
		// geef waardes aan elke zet
		int[] boardValue = new int[64];
		
		int[][] board = b.getBoard();
		int i;
		int j;
		int k;
		int l;
		// hoeken
		i = 100;
		boardValue[0]= i;
		boardValue[7] = i;
		boardValue[56] = i;
		boardValue[63] = i;
		
		// vakken horizontaal of verticaal naast hoeken
		i = 2;
		j = -99;
		k = 30;
		l = -51;
		
		if (board[0][0] == player.color) {
			boardValue[1] = k;
		} else if (board[0][0] == player.opponent) {
			boardValue[1] = l;
		} else if (board[0][2] != 0) {
			boardValue[1] = i;
		}
		else {
			boardValue[1] = j;
		}
		
		if (board[0][7] == player.color) {
			boardValue[6] = k;
		} else if (board[0][7] == player.opponent) {
			boardValue[6] = l;
		} else if (board[0][5] != 0) {
			boardValue[6] = i;
		}
		else {
			boardValue[6] = j;
		}
		
		if (board[0][0] == player.color) {
			boardValue[8] = k;
		} else if (board[0][0] == player.opponent) {
			boardValue[8] = l;
		} else if (board[2][0] != 0) {
			boardValue[8] = i;
		}
		else {
			boardValue[8] = j;
		}
		
		if (board[0][7] == player.color) {
			boardValue[15] = k;
		} else if (board[0][7] == player.opponent) {
			boardValue[15] = l;
		} else if (board[2][7] != 0) {
			boardValue[15] = i;
		}
		else {
			boardValue[15] = j;
		}
		
		if (board[7][0] == player.color) {
			boardValue[48] = k;
		} else if (board[7][0] == player.opponent) {
			boardValue[48] = l;
		} else if (board[5][0] != 0) {
			boardValue[48] = i;
		}
		else {
			boardValue[48] = j;
		}
		
		if (board[7][7] == player.color) {
			boardValue[55] = k;
		} else if (board[7][7] == player.opponent) {
			boardValue[55] = l;
		} else if (board[5][7] != 0) {
			boardValue[55] = i;
		}
		else {
			boardValue[55] = j;
		}
		
		if (board[7][0] == player.color) {
			boardValue[57] = k;
		} else if (board[7][0] == player.opponent) {
			boardValue[57] = l;
		} else if (board[7][2] != 0) {
			boardValue[57] = i;
		}
		else {
			boardValue[57] = j;
		}
		
		if (board[7][7] == player.color) {
			boardValue[62] = k;
		} else if (board[7][7] == player.opponent) {
			boardValue[62] = l;
		} else if (board[7][5] != 0) {
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

	/**
	 * Deze functie berekend de best mogelijke zet door middel van een minimax algoritme dat gebruik
	 * maakt van alpha-beta pruning. Er wordt vooral gekeken naar mobility, maar er wordt ook in
	 * sommige gevallen gekeken naar, stability, corner grab, board state, boardweight, score
	 * en time passed.
	 * 
	 * @param b 			Het huidige bord
	 * @param player		De speler die een zet wilt doen
	 * @param depth			Hoe diep het minimax algoritme is
	 * @param maxDepth		Hoe diep het minimax algortime moet gaan
	 * @param chosenScore	De score van de vorige diepte
	 * @param chosenMove	De zet die gekozen was in de vorige diepte
	 * @param alpha			Alpha waarde van vorige diepte
	 * @param beta			Beta waarde van vorige diepte
	 * @param time			Hoeveel tijd er voorbij is sinds de 1ste loop
	 * @return				De zet die berekend is
	 */
	public int minimaxAvailableMoves(Board b, Player player, int depth, int maxDepth, int chosenScore, int chosenMove, int alpha, int beta, long time){
		ArrayList<ArrayList<Integer>> list = reversi.getValidMoves(b, player.color);		
    	long endTime = time;
		if (depth == 0) {
			// Time check
			long start = System.currentTimeMillis();
			endTime = start + 9*1000;
			
			// If big list, make it do 1 depth less
			if (list.size() > 6) {
				maxDepth--;
			}
			if (list.size() > 8) {
				maxDepth--;
			}
			if (list.size() < 3) {
				maxDepth++;
			}
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
	    	
	    	// Stability possibility check
			if (checkCorners (b, player).size() > 0) {
				ArrayList<ArrayList<Integer>> stableMoves = stability(list, b, player);
				if (stableMoves.size() > 0) {
					System.out.println("Hey kerel, ik houd het stabiel."+stableMoves);
					//list = stableMoves;
				}
			}
		}
		// Als de tijd op is, stop het algoritme
		if (System.currentTimeMillis() > endTime) {
			ArrayList<ArrayList<Integer>> goodList = (ArrayList<ArrayList<Integer>>) list.clone();
	    	
	    	for(int i = list.size() -1; i > -1; i--) {
	    		int a = list.get(i).get(0) + (list.get(i).get(1) * 8);
	    		int c = areaValue(b, player)[a];
	    		if (c < -50) {
	    			goodList.remove(i);
	    		}
	    	}
	    	int currentScore = removeBadMoves(list, b, player).size();
	    	if (depth % 2 == 0) {
	    		int output = 1000 * (currentScore - chosenScore) / (currentScore + chosenScore + 1);
	    		return output + reversi.calculateScore(player.color);
	    	}
	    	else {
	    		int output = 1000 * (chosenScore - currentScore) / (chosenScore + currentScore + 1);
	    		return output + reversi.calculateScore(player.opponent);
	    	}
		}
		
		// Make backup of current board state
		int boardSize = b.getBoardSize();
		int[][] currentBoard = b.getBoard();
		
		int[][] backup = new int[boardSize][boardSize];
		for (int i = 0; i < boardSize; i++) {
		  backup[i] = Arrays.copyOf(currentBoard[i], currentBoard[i].length);
		}
		// Als de max diepte bereikt is
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
	    	
	    	int[] corners = new int[]{0, 7, 56, 63};
	    	int cornerScore = 0;
	    	for (int corner : corners) {
	    		int x = corner % 8;
	    		int y = corner / 8;
	    		int check = b.getBoard()[x][y];
	    		if (check == player.color) {
	    			cornerScore = cornerScore + 1000;
	    		}
	    		else if (check == player.opponent) {
	    			cornerScore = cornerScore - 1000;    			
	    		}
	    	}
	    	
	    	if (depth % 2 == 0) {
	    		int output = 1000 * (currentScore - chosenScore) / (currentScore + chosenScore + 1);
	    		return output + reversi.calculateScore(player.color) + cornerScore;
	    	}
	    	else {
	    		int output = 1000 * (chosenScore - currentScore) / (chosenScore + currentScore + 1);
	    		return output + reversi.calculateScore(player.opponent) + (cornerScore * -1);
	    	}
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
		    	if (depth % 2 == 0) {
		    		
		    		return -100000;
		    	}
		    	else {
		    		return 1000;
		    	}
	        		
	        }
	        else {
	            for (int i = 0; i < (list.size()); i++) {	                
	                int move = list.get(i).get(0) + (list.get(i).get(1)*8);
	                reversi.makeForwardMove(player, move, b);
	                int currentscore = reversi.getValidMoves(b, player.color).size();
	
	                Player nextPlayer;
	                if (player.color == 1) {
	                	nextPlayer = new Player(PlayerType.AI, 2);
	                }
	                else {
	                	nextPlayer = new Player(PlayerType.AI, 1);	           
	                }
	                nextPlayer.setTurn(true);
	                player.setTurn(false);
	                
	             // Ga 1 stap dieper in de minimax boom
	                int score = minimaxAvailableMoves(b, nextPlayer, depth+1, maxDepth, currentscore, move, alpha, beta, endTime);
	                
	                // Zet het bord terug naar de oude situatie
	                int[][] restore = new int[boardSize][boardSize];
	        		for (int j = 0; j < boardSize; j++) {
	        		  restore[j] = Arrays.copyOf(backup[j], backup[j].length);
	        		}
	                b.setBoard(restore);
	                
	                // Kijk of er al een best move is, zo niet, is de huidige move de beste move.
	                if (bestMove == -1) {
	                	bestScore = score;
	                    bestMove = move;
	                }
	                
	                // Als de diepte even is
                	if (depth%2 == 0) {
                		// Als de score beter is dat de beste score, is er een nieuwe beste zet
	                	if (score > bestScore){
		                    bestScore = score;
		                    bestMove = move;
		                }
	                	// Als de score hoger is dan alpha, is er een nieuwe alpha.
	                	if(score > alpha) {
	                		alpha = score;
	                	}
	                	// Als alpha groter is dan Beta, zal deze node nooit gekozen worden, dus word er uit de loop gebroken.
	                	if(beta <= alpha) {
	                		break;
	                	}
	                }
	                else {
	                	// Als de score beter is dat de beste score, is er een nieuwe beste zet
	                	if (score < bestScore){
		                    bestScore = score;
		                    bestMove = move;
		                }
	                	// Als de score lager is dan beta, is er een nieuwe beta.
	                	if(score < beta) {
	                		beta = score;
	                	}
	                	// Als alpha groter is dan Beta, zal deze node nooit gekozen worden, dus word er uit de loop gebroken.
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
	
	/**
	 * Deze functie kijkt of er hoeken in bezig zijn van de speler die meegegeven
	 * wordt in de parameters.
	 * 
	 * @param b 		Het huidige bord
	 * @param player	De speler die aan zet is
	 * @return			Geef terug welke hoeken de speler heeft
	 */
	public ArrayList<int[]> checkCorners (Board b, Player player) {
		ArrayList<int[]> cornersTaken = new ArrayList<int[]>();
		
		// Linker bovenhoek
		if (b.getBoard()[0][0] == player.color) {
			int[] temp = {0,0};
			cornersTaken.add(temp);
		}
		// Rechter bovenhoek
		if (b.getBoard()[0][7] == player.color) {
			int[] temp = {0,7};
			cornersTaken.add(temp);
		}
		// Linker onderhoek
		if (b.getBoard()[7][0] == player.color) {
			int[] temp = {7,0};
			cornersTaken.add(temp);
		}
		// Rechter onderhoek
		if (b.getBoard()[7][7] == player.color) {
			int[] temp = {7,7};
			cornersTaken.add(temp);
		}
		return cornersTaken;		
	}
	
	/**
	 * Deze functie kijkt of er stabiele zetten mogelijk zijn. Stabiele zetten
	 * zijn zetten die voor de rest van de game niet meer overgenomen kunnen worden.
	 * 
	 * @param list		Lijst met mogelijke zetten
	 * @param b 		Het huidige bord
	 * @param player	De speler die een zet wilt doen
	 * @return			Geef terug een lijst met stabiele zetten.
	 */
	public ArrayList<ArrayList<Integer>> stability (ArrayList<ArrayList<Integer>> list, Board b, Player player) {
		ArrayList<int[]> cornersTaken = checkCorners(b, player);
		int boardSize = b.getBoardSize();
		ArrayList<ArrayList<Integer>> stableMoves = new ArrayList<ArrayList<Integer>>();
		
		for (int[] num : cornersTaken) {
			// Check Zuid
			if (num[0] == 0) {
	        	for(int i = 1; i < boardSize; i++) {
	        		int check = b.getBoard()[i][num[1]];
	        		// Kijk of mogelijk stability vak leeg is
	        		if (check == 0) {
		        		for (int j = 0; j < list.size(); j++) {
		        			for (int k = 0; k < list.get(j).size(); k = k+2) {
		        				if ((list.get(j).get(k) == num[1]) && (list.get(j).get(k+1) == i)) {
		        					//stableMoves.add(list.get(j).get(0) + (list.get(j).get(1)*8));
		        					stableMoves.add(list.get(j));
		        				}
		        			}
		        		}
	        		}
	        		else if (check != player.color){
	        			// Niet stabiel meer
	        			break;
	        		}
	        	}
			}
			
			// Check Noord
			if (num[0] == (boardSize-1)) {
	        	for(int i = (boardSize-1); i > -1; i--) {
	        		int check = b.getBoard()[i][num[1]];
	        		// Kijk of mogelijk stability vak leeg is
	        		if (check == 0) {
		        		for (int j = 0; j < list.size(); j++) {
		        			for (int k = 0; k < list.get(j).size(); k = k+2) {
		        				if ((list.get(j).get(k) == num[1]) && (list.get(j).get(k+1) == i)) {
		        					stableMoves.add(list.get(j));
		        				}
		        			}
		        		}
	        		}
	        		else if (check != player.color){
	        			// Niet stabiel meer
	        			break;
	        		}
	        	}
			}
			
			// Check Oost
			if (num[0] == 0) {
	        	for(int i = 1; i < boardSize; i++) {
	        		int check = b.getBoard()[num[0]][i];
	        		// Kijk of mogelijk stability vak leeg is
	        		if (check == 0) {
		        		for (int j = 0; j < list.size(); j++) {
		        			for (int k = 0; k < list.get(j).size(); k = k+2) {
		        				if ((list.get(j).get(k) == i) && (list.get(j).get(k+1) == num[0])) {
		        					stableMoves.add(list.get(j));
		        				}
		        			}
		        		}
	        		}
	        		else if (check != player.color){
	        			// Niet stabiel meer
	        			break;
	        		}
	        	}
			}
			
			// Check West
			if (num[1] == (boardSize-1)) {
	        	for(int i = (boardSize-1); i > -1; i--) {
	        		int check = b.getBoard()[num[0]][i];
	        		// Kijk of mogelijk stability vak leeg is
	        		if (check == 0) {
		        		for (int j = 0; j < list.size(); j++) {
		        			for (int k = 0; k < list.get(j).size(); k = k+2) {
		        				if ((list.get(j).get(k) == i) && (list.get(j).get(k+1) == num[0])) {
		        					stableMoves.add(list.get(j));
		        				}
		        			}
		        		}
	        		}
	        		else if (check != player.color){
	        			// Niet stabiel meer
	        			break;
	        		}
	        	}
			}
		}				
		return stableMoves;
	}
	
	
	
	/**
	 * Deze functie berekend de best mogelijke zet door middel van een minimax algoritme dat gebruik
	 * maakt van alpha-beta pruning. Er wordt vooral gekeken naar score en corner grab, maar er wordt ook in
	 * sommige gevallen gekeken naar, stability, mobiliy, board state, boardweight, en time passed
	 * 
	 * @param b 			Het huidige bord
	 * @param player		De speler die een zet wilt doen
	 * @param depth			Hoe diep het minimax algoritme is
	 * @param maxDepth		Hoe diep het minimax algortime moet gaan
	 * @param chosenScore	De score van de vorige diepte
	 * @param chosenMove	De zet die gekozen was in de vorige diepte
	 * @param alpha			Alpha waarde van vorige diepte
	 * @param beta			Beta waarde van vorige diepte
	 * @param time			Hoeveel tijd er voorbij is sinds de 1ste loop
	 * @return				De zet die berekend is
	 */
	public int minimaxTest(Board b, Player player, int depth, int maxDepth, int chosenScore, int chosenMove, int alpha, int beta, long time){
		ArrayList<ArrayList<Integer>> list = reversi.getValidMoves(b, player.color);		
    	long endTime = time;
		if (depth == 0) {
			// Time check
			long start = System.currentTimeMillis();
			endTime = start + 9*1000;

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
	    	
	    	// Stability possibility check
			if (checkCorners (b, player).size() > 0) {
				ArrayList<ArrayList<Integer>> stableMoves = stability(list, b, player);
				if (stableMoves.size() > 0) {
					System.out.println("Hey kerel, ik houd het stabiel."+stableMoves);
					//list = stableMoves;
				}
			}
		}	
		// Als de tijd op is, stop het algoritme
		if (System.currentTimeMillis() > endTime) {
			ArrayList<ArrayList<Integer>> goodList = (ArrayList<ArrayList<Integer>>) list.clone();
	    	
	    	for(int i = list.size() -1; i > -1; i--) {
	    		int a = list.get(i).get(0) + (list.get(i).get(1) * 8);
	    		int c = areaValue(b, player)[a];
	    		if (c < -50) {
	    			goodList.remove(i);
	    		}
	    	}
	    	int currentScore = removeBadMoves(list, b, player).size();
	    	if (depth % 2 == 0) {
	    		int output = 1000 * (currentScore - chosenScore) / (currentScore + chosenScore + 1);
	    		return output + (reversi.calculateScore(player.color) * 5);
	    	}
	    	else {
	    		int output = 1000 * (chosenScore - currentScore) / (chosenScore + currentScore + 1);
	    		return output + (reversi.calculateScore(player.opponent) * 5);
	    	}
		}
		
		// Make backup of current board state
		int boardSize = b.getBoardSize();
		int[][] currentBoard = b.getBoard();
		
		int[][] backup = new int[boardSize][boardSize];
		for (int i = 0; i < boardSize; i++) {
		  backup[i] = Arrays.copyOf(currentBoard[i], currentBoard[i].length);
		}
		// Als de max diepte bereikt is
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
	    	
	    	int[] corners = new int[]{0, 7, 56, 63};
	    	int cornerScore = 0;
	    	for (int corner : corners) {
	    		int x = corner % 8;
	    		int y = corner / 8;
	    		int check = b.getBoard()[x][y];
	    		if (check == player.color) {
	    			cornerScore = cornerScore + 1000;
	    		}
	    		else if (check == player.opponent) {
	    			cornerScore = cornerScore - 1000;    			
	    		}
	    	}
	    	
	    	if (depth % 2 == 0) {
	    		int output = 1000 * (currentScore - chosenScore) / (currentScore + chosenScore + 1);
	    		return output + (reversi.calculateScore(player.color)* 50) + cornerScore;
	    	}
	    	else {
	    		int output = 1000 * (chosenScore - currentScore) / (chosenScore + currentScore + 1);
	    		return output + (reversi.calculateScore(player.opponent)* 50) + (cornerScore * -1);
	    	}
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
	        	goodList = (ArrayList<ArrayList<Integer>>) list.clone();
		    	
		    	for(int i = list.size() -1; i > -1; i--) {
		    		int a = list.get(i).get(0) + (list.get(i).get(1) * 8);
		    		int c = areaValue(b, player)[a];
		    		if (c < -50) {
		    			goodList.remove(i);
		    		}
		    	}
		    	int currentScore = removeBadMoves(list, b, player).size();
		    	
		    	int[] corners = new int[]{0, 7, 56, 63};
		    	int cornerScore = 0;
		    	for (int corner : corners) {
		    		int x = corner % 8;
		    		int y = corner / 8;
		    		int check = b.getBoard()[x][y];
		    		if (check == player.color) {
		    			cornerScore = cornerScore + 1000;
		    		}
		    		else if (check == player.opponent) {
		    			cornerScore = cornerScore - 1000;    			
		    		}
		    	}
		    	
		    	if (depth % 2 == 0) {
		    		int output = 1000 * (currentScore - chosenScore) / (currentScore + chosenScore + 1);
		    		return output + (reversi.calculateScore(player.color)* 50) + cornerScore;
		    	}
		    	else {
		    		int output = 1000 * (chosenScore - currentScore) / (chosenScore + currentScore + 1);
		    		return output + (reversi.calculateScore(player.opponent)* 50) + (cornerScore * -1);
		    	}
	        		
	        }
	        else {
	            for (int i = 0; i < (list.size()); i++) {	                
	                int move = list.get(i).get(0) + (list.get(i).get(1)*8);
	                reversi.makeForwardMove(player, move, b);
	                int currentscore = reversi.getValidMoves(b, player.color).size();
	
	                Player nextPlayer;
	                if (player.color == 1) {
	                	nextPlayer = new Player(PlayerType.AI, 2);
	                }
	                else {
	                	nextPlayer = new Player(PlayerType.AI, 1);	           
	                }
	                nextPlayer.setTurn(true);
	                player.setTurn(false);
	                
	                // Ga 1 stap dieper in de minimax boom
	                int score = minimaxAvailableMoves(b, nextPlayer, depth+1, maxDepth, currentscore, move, alpha, beta, endTime);
	                
	                // Zet het bord terug naar de oude situatie
	                int[][] restore = new int[boardSize][boardSize];
	        		for (int j = 0; j < boardSize; j++) {
	        		  restore[j] = Arrays.copyOf(backup[j], backup[j].length);
	        		}
	                b.setBoard(restore);
	                
	                // Kijk of er al een best move is, zo niet, is de huidige move de beste move.
	                if (bestMove == -1) {
	                	bestScore = score;
	                    bestMove = move;
	                }
	                
	                // Als de diepte even is
                	if (depth%2 == 0) {
                		// Als de score beter is dat de beste score, is er een nieuwe beste zet
	                	if (score > bestScore){
		                    bestScore = score;
		                    bestMove = move;
		                }
	                	// Als de score hoger is dan alpha, is er een nieuwe alpha.
	                	if(score > alpha) {
	                		alpha = score;
	                	}
	                	// Als alpha groter is dan Beta, zal deze node nooit gekozen worden, dus word er uit de loop gebroken.
	                	if(beta <= alpha) {
	                		break;
	                	}
	                }
	                else {
	                	// Als de score beter is dat de beste score, is er een nieuwe beste zet
	                	if (score < bestScore){
		                    bestScore = score;
		                    bestMove = move;
		                }
	                	// Als de score lager is dan beta, is er een nieuwe beta.
	                	if(score < beta) {
	                		beta = score;
	                	}
	                	// Als alpha groter is dan Beta, zal deze node nooit gekozen worden, dus word er uit de loop gebroken.
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
}