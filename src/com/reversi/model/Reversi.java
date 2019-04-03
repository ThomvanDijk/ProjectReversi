import java.awt.List;
import java.util.ArrayList;
import java.util.Scanner;

public class reversi{
	//Verander naar 3 voor tic-tac-toe
	public final static int boardsize = 8;
	public static String aanZet = "Z";
	public static String nietAanZet = "W";
	
	 @SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		
		
		boolean gameEnd = false;
		
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
		reversiStart(board);
		
		//Bord op scherm tonen
		printBoard(boardsize, board);
		
		//Input vragen aan gebruiker
		Scanner myObj = new Scanner(System.in);
	    String coordinate;

	    while(gameEnd == false) {
		    //Voer coordinaat in en druk op enter (bijv: 44 of 17)
		    System.out.println("Enter coordinate");
		    //haal informatie uit scanner
		    coordinate = myObj.nextLine();
		    //split string in array met 2 variabelen
		    String[] explode = coordinate.split("(?!^)");
		    String currentCoordinate = (Integer.parseInt(explode[0])-1 + "" + (Integer.parseInt(explode[1])-1));
		    //haal geldige zetten op
		    ArrayList<ArrayList> validMoves = getValidMoves(boardsize, board);
		    //check of zet geldig is
		    boolean geldig = false;
		    for (int check=0; check<validMoves.size(); check++) {		    	
		    	if(validMoves.get(check).get(0).equals(currentCoordinate)){
		    		geldig = true;
		    		for (int check2=0; check2<validMoves.get(check).size(); check2++) {
		    			String temp = (String) validMoves.get(check).get(check2);
		    			String[] explode2 = temp.split("(?!^)");
		    			board.get(Integer.parseInt(explode2[1])).set(Integer.parseInt(explode2[0]), aanZet);
		    			System.out.println(explode2[0] + " " + explode2[1]);
		    		}		    		
		    	}		    	
		    }
		    if(geldig == false) {
	    		System.out.println("Geen geldige zet");
	    	}
		    else {
			    //update board
			    printBoard(boardsize, board);
			    
			    //infinite loop stoppen

			    if (aanZet =="W") {
			    	aanZet = "Z";
			    	nietAanZet = "W";
			    }
			    else {
			    	aanZet = "W";
			    	nietAanZet = "Z";
			    }
		    }
	    }	    
	}
	 public static void printBoard(int boardsize, ArrayList<ArrayList> board){
		 System.out.println("  1 2 3 4 5 6 7 8");
		 for (int i = 0; i < boardsize; i++) {
				ArrayList<String> temp = board.get(i);
				System.out.printf(i+1+" ");
				for (int j = 0; j < boardsize; j++) {
					System.out.printf(temp.get(j)+" ");
				}
				System.out.println();
			}
		 System.out.println();
	 }
	 
	 @SuppressWarnings("unchecked")
	public static void reversiStart(ArrayList<ArrayList> board) {
		 board.get(3).set(3, "W");
		 board.get(3).set(4, "Z");
		 board.get(4).set(3, "Z");
		 board.get(4).set(4, "W");
	 }
	 
	 public static ArrayList<ArrayList> getValidMoves(int boardsize, ArrayList<ArrayList> board) {
		 //Array met mogelijke zetten
		 ArrayList<ArrayList> validMoves = new ArrayList<ArrayList>();
		 
		 //coordinaten
		 int x;
		 int y;
		 	 
		 //alle mogelijke zetten opslaan in arraylist
		 for (x = 0; x < boardsize; x++) {
			 for (y = 0; y < boardsize; y++) {
				
				 //coördinaat opslaan in string
				 String temp = (String) board.get(y).get(x);
				 
				 //check of het veld leeg is (volle vakken zijn nooit geldig)
				 if(temp.equals(".")){
					 ArrayList<String> tempList = new ArrayList<String>();
					 
					 //check in elke richting of de zet geldig is:
					 //noordwest
					 if(checkDirection(x,y,board,-1,-1).isEmpty() == false) {
						 tempList.addAll(checkDirection(x,y,board,-1,-1));
					 }
					 //noord				
					 if(checkDirection(x,y,board,0,-1).isEmpty() == false) {
						 tempList.addAll(checkDirection(x,y,board,0,-1));
					 }			
					 //noordoost					
					 if(checkDirection(x,y,board,1,-1).isEmpty() == false) {
						 tempList.addAll(checkDirection(x,y,board,1,-1));
					 }							
					 //oost				
					 if(checkDirection(x,y,board,1,0).isEmpty() == false) {
						 tempList.addAll(checkDirection(x,y,board,1,0));
					 }						
					 //zuidoost				
					 if(checkDirection(x,y,board,1,1).isEmpty() == false) {
						 tempList.addAll(checkDirection(x,y,board,1,1));
					 }					
					 //zuid
					 if(checkDirection(x,y,board,0,1).isEmpty() == false) {
						 tempList.addAll(checkDirection(x,y,board,0,1));
					 }			
					 //zuidwest
					 if(checkDirection(x,y,board,-1,1).isEmpty() == false) {
						 tempList.addAll(checkDirection(x,y,board,-1,1));
					 }		
					 //west
					 if(checkDirection(x,y,board,-1,0).isEmpty() == false) {
						 tempList.addAll(checkDirection(x,y,board,-1,0));
					 }
					 
					 if(tempList.isEmpty() == false) {
						 validMoves.add(tempList);
					 }
	 
				 }
			 }
		 }
		 return validMoves;
	 }
	 public static ArrayList<String> checkDirection(int x, int y, ArrayList<ArrayList> board, int intX, int intY) {
		 ArrayList<String> fields = new ArrayList<String>();
		 
		 //Richting increment
		 int yd = intY;
		 int xd = intX;
		 int i = 0;
		 boolean loop = true;
		 
		 //out of bounds check
		 if((y+intY < 1 || x+intX < 1 || y+intY > boardsize-2 || x+intX > boardsize-2) == false) {
			 //check of er een tegenstander aan het veld is
			 if(board.get(y+intY).get(x+intX).equals(nietAanZet)){
				 //increment de richting
				 intY = intY + yd;
				 intX = intX + xd;
				 //loop totdat er geen tegenstander stukken meer zijn in deze richting
				 while(loop == true) {
					 //als er een leeg vakje gevonden is, is de richting niet geldig voor deze zet.
					 if(board.get(y+intY).get(x+intX).equals(".")) {
						 loop = false;
					 }
					 //als er een vakje met een eigen steen gevonden wordt, is het een geldige zet.
					 else if(board.get(y+intY).get(x+intX).equals(aanZet)){
						 for (int j = 0; j <= i+1; j++) {
							 int newX = x+(j*xd);
							 int newY = y+(j*yd);
							 fields.add(newX+""+newY);							 
						 }
						 loop = false;
					 }
					 //als er out of bounds gegaan wordt, is de richting niet geldig voor deze zet.
					 if(y+intY == 0 || x+intX == 0 || y+intY == boardsize-1 || x+intX == boardsize-1) {
						 loop = false;
					 }
					 //increment de richting
					 intY = intY + yd;
					 intX = intX + xd;
					 i++;
				 }

			 }
		 }
		 return fields;
	 }
}

