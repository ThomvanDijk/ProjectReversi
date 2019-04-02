package com.reversi.model;

import java.awt.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Reversi {
	
	 public Reversi() {
		//Verander naar 3 voor tic-tac-toe
		int boardsize = 8;
		
		//Bord aanmaken
		ArrayList<String> line = new ArrayList<String>();
		ArrayList<ArrayList> board = new ArrayList<ArrayList>();
		
		int i = 0;
		
		while (i < boardsize) {
			line.add(".");
			i++;
		}
		
		i = 0;
		while (i < boardsize) {
			board.add((ArrayList) line.clone());
			i++;
		}
		
		//Reversi begin zetten(haal dit weg voor tic-tac-toe)
		start(board);
		
		//Bord op scherm tonen
		printBoard(boardsize, board);
		
		//Input vragen aan gebruiker
		Scanner myObj = new Scanner(System.in);
	    String coordinate;
	    
	    // Enter coordinate and press Enter
	    System.out.println("Enter coordinate"); 
	    coordinate = myObj.nextLine();
	    String[] explode = coordinate.split("(?!^)");    
	    
	    board.get(Integer.parseInt(explode[0])).set(Integer.parseInt(explode[1]), "W");
	    printBoard(boardsize, board);
	}
	 public static void printBoard(int boardsize, ArrayList<ArrayList> board){
		 for (int i = 0; i < boardsize; i++) {
				ArrayList<String> temp = board.get(i);
				for (int j = 0; j < boardsize; j++) {
					System.out.printf(temp.get(j)+" ");
				}
				System.out.println();
			}
		 System.out.println();
	 }
	 
	 public static void start(ArrayList<ArrayList> board) {
		 board.get(3).set(3, "W");
		 board.get(3).set(4, "Z");
		 board.get(4).set(3, "Z");
		 board.get(4).set(4, "W");
	 }
}

