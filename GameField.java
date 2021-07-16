package Battleships;

/*
GameField.java
Author:  Mark Ma
Class:  ICS4U
Last Modified:  June 23rd, 2021
This class represents the field that the Players are battling on. It contains
methods to display, change the battlefield, and also checks whether boats could be placed.
*/

public class GameField {
	//instance fields
	public static int SIZE = 25;
	private char[][] field1Hidden, field1Shown, field2Hidden, field2Shown;
	private int[][] player1Ships, player2Ships;
	
	/*GameField()
	  
	  returns GameField - the default instance of GameField
	  
	  no parameters
	  
	  This constructor creates the default GameField given no parameters,
	  by assigning their pre-determined fields 
	 
	 */
	public GameField() {
		field1Hidden = new char[SIZE][SIZE]; //field of player1 from the perspective of player2
		field1Shown = new char[SIZE][SIZE];  //field of player1 from the perspective of player1
		
		field2Hidden = new char[SIZE][SIZE]; //field of player2 from the perspective of player1
		field2Shown = new char[SIZE][SIZE];  //field of player2 from the perspective of player2
		
		//ship arrays of both players
		player1Ships = new int[SIZE][SIZE]; 
		player2Ships = new int[SIZE][SIZE];
		
		for(int i = 0; i<SIZE; i++) {
			for(int j = 0; j<SIZE; j++) {
				//default game board is full with • and default ships is filled with -1 (indicating empty)
				field1Hidden[i][j] = field1Shown[i][j] = field2Hidden[i][j] = field2Shown[i][j] = '•';
				player1Ships[i][j] = player2Ships[i][j] = -1;
			}
		}
		
	}
	//accessor method for field1Hidden cells
	public char getField1Hidden(int y, int x) {
		return field1Hidden[y][x];
	}
	//accessor method for field2Hidden cells
	public char getField2Hidden(int y, int x) {
		return field2Hidden[y][x];
	}
	//mutator method for field1Hidden cells
	public void setField1Hidden(int y, int x, char ch) {
		field1Hidden[y][x] = ch;
	}
	//mutator method for field2Hidden cells
	public void setField2Hidden(int y, int x, char ch) {
		field2Hidden[y][x] = ch;
	}
	//mutator method for field1Shown cells
	public void setField1Shown(int y, int x, char ch) {
		field1Shown[y][x] = ch;
	}
	//mutator method for field2Shown cells
	public void setField2Shown(int y, int x, char ch) {
		field2Shown[y][x] = ch;
	}
	//mutator method for player1Ships cells
	public void setPlayer1Ships(int y, int x, int val) {
		player1Ships[y][x] = val;
	}
	//mutator method for player2Ships cells
	public void setPlayer2Ships(int y, int x, int val) {
		player2Ships[y][x] = val;
	}
	//accessor method for player1Ships
	public int[][] getPlayer1Ships() {
		return this.player1Ships;
	}
	//accessor method for player2Ships
	public int[][] getPlayer2Ships() {
		return this.player2Ships;
	}
	/*void showPlayer1Field()
	  
	  returns nothing
	  
	  no parameters
	  
	  This method is used to print out the game board from player1's
	  perspective.
	 */
	
	public void showPlayer1Field() {
		//print x-axis indicators
		System.out.print("   ");
		for(int i = 0; i<SIZE; i++) {
			System.out.printf("%-3d", i);
		}
		
		System.out.println();
		
		for(int i = 0; i<SIZE; i++) {
			//print y-axis indicators
			System.out.printf("%-3d", i);
			//print each cell of field2Hidden, player 2's field whose cells reveal only if fired at
			for(int j = 0; j<SIZE; j++) {
				System.out.print(field2Hidden[i][j]+"  ");
			}
			System.out.println();
		}
		//separator
		System.out.println();
		System.out.println("============================================================================");
		System.out.println();
		
		//print x-axis indicators
		System.out.print("   ");
		for(int i = 0; i<SIZE; i++) {
			System.out.printf("%-3d", i);
		}
		
		System.out.println();
		
		for(int i = 0; i<SIZE; i++) {
			//print y-axis indicators
			System.out.printf("%-3d", i);
			//print each cell of field1Shown, whose cells are all revealed to player 1
			for(int j = 0; j<SIZE; j++) {
				System.out.print(field1Shown[i][j]+"  ");
			}
			System.out.println();
		}
	}
	/*void showPlayer2Field()
	  
	  returns nothing
	  
	  no parameters
	  
	  This method is used to print out the game board from player2's
	  perspective.
	 */
	public void showPlayer2Field() {
		//print x-axis indicators
		System.out.print("   ");
		for(int i = 0; i<SIZE; i++) {
			System.out.printf("%-3d", i);
		}
		
		System.out.println();
		
		for(int i = 0; i<SIZE; i++) {
			//print y-axis indicators
			System.out.printf("%-3d", i);
			//print each cell of field1Hidden, player 1's field whose cells reveal only if fired at
			for(int j = 0; j<SIZE; j++) {
				System.out.print(field1Hidden[i][j]+"  ");
			}
			System.out.println();
		}
		//separator
		System.out.println();
		System.out.println("======================================================================");
		System.out.println();
		
		//print x-axis indicators
		System.out.print("   ");
		for(int i = 0; i<SIZE; i++) {
			System.out.printf("%-3d", i);
		}
		
		System.out.println();
		
		for(int i = 0; i<SIZE; i++) {
			//print y-axis indicators
			System.out.printf("%-3d", i);
			//print each cell of field2Shown, whose cells are all revealed to player 2
			for(int j = 0; j<SIZE; j++) {
				System.out.print(field2Shown[i][j]+"  ");
			}
			System.out.println();
		}
	}
	/*boolean checkAndAddShip(int player, int shipIdx, int x, int y, int length, int width)
	  
	  returns boolean - whether or not the ship could be placed 
	  
	  int player - an integer parameter indicating if this is player 1 or player 2
	  int shipIdx - this parameter is the index of the ship in the player's fleet array
	  int x - this parameter is the x-coordinate of the placement
	  int y - this parameter is the y-coordinate of the placement
	  int length - this parameter is the length of the ship
	  int width - this parameter is the width of the ship
	  
	  This method checks whether or not a ship could be added to the gamefield without
	  any collisions while also staying in boundaries. If it is allowed, it draws
	  the ship onto the field
	 
	 */
	public boolean checkAndAddShip(int player, int shipIdx, int x, int y, int length, int width) {
		//check if ship will go out of bounds
		if(x<0||y<0) {
			return false;
		}
		if(x+length>=SIZE||y+width>=SIZE) {
			return false;
		}
		
		//if player1 is adding ship
		if(player == 1) {
			for(int i = y; i<y+width; i++) {
				for(int j = x; j<x+length; j++) {
					//checks if all the cells are empty, if not return false;
					if(player1Ships[i][j]!=-1) { 
						return false;
					}
				}
			}
			//drawing the ship
			for(int i = y; i<y+width; i++) {
				for(int j = x; j<x+length; j++) {
					player1Ships[i][j] = shipIdx;
					field1Shown[i][j] = 'O';
				}
			}
		}
		//if player2 is adding ship
		else {
			for(int i = y; i<y+width; i++) {
				for(int j = x; j<x+length; j++) {
					//checks if all the cells are empty, if not return false;
					if(player2Ships[i][j]!=-1) {
						return false;
					}
				}
			}
			//drawing the ship
			for(int i = y; i<y+width; i++) {
				for(int j = x; j<x+length; j++) {
					player2Ships[i][j] = shipIdx;
					field2Shown[i][j] = 'O';
				}
			}
		}
		return true;
	}
}
