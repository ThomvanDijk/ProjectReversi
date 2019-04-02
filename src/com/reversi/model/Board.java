package com.reversi.model;

import java.util.ArrayList;

import com.reversi.model.*;

public class Board {

	public Board() {
	}
	
	public void updateBoard() {
		int boardsize = 8;
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

		for (i = 0; i < boardsize; i++) {
			ArrayList<String> temp = board.get(i);
			for (int j = 0; j < boardsize; j++) {
				System.out.printf(temp.get(j)+" ");
			}
			System.out.println();
		}
	}

}
