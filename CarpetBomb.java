package Battleships;
/*
CarpetBomb.java
Author:  Mark Ma
Class:  ICS4U
Last Modified:  June 23rd, 2021
This class is a child class of Weapon.java and represents a bomb attack, where a 2x2 square is hit.
it contains functions that overrides and fulfills the operations of a weapon
*/
public class CarpetBomb extends Weapon {//demonstrates inheritance

	/*CarpetBomb()
	  
	  returns CarpetBomb - the default instance of CarpetBomb
	  
	  no parameters
	  
	  This constructor creates the default CarpetBomb given no parameters,
	  by assigning their pre-determined fields 
	 
	 */
	public CarpetBomb() {
		this.name = "Carpet bomb";
		this.desc = "Used with ships that has mounted cannons, hits a 2x2 square from the specified tile"; //##
		this.cost = 1000;                                                                                  //##
		//maybe 3x3 so i could pick a center
		this.placement = "** where x is the target coordinate\n*x";
	}
	public void makeSound() {
		System.out.println("ka-BOOOM!");
	}
	@Override
	/*p1Attack(Player p1, GameField g, int x, int y)
	  
	  returns nothing
	  
	  Player p1 - this parameter is the player that is attacking
	  GameField g - this parameter is the GameField that the game takes place on
	  int x - this parameter is the x-coordinate of the target
	  int y - this parameter is the y-coordinate of the target
	  
	  this method lets Player p1 attack their opponent at
	  coordinate (x, y)
	 */
	public void p1Attack(Player p1, GameField g, int x, int y) {
		// TODO Auto-generated method stub
		for(int i = y; i>y-2; i--) {
			for(int j = x; j>x-2; j--) {
				//if i and j are within the board
				if(j>=0 && j<=24 && i>=0 && i<=24) {
					if(g.getPlayer2Ships()[i][j]!=-1) { ///if the current cell is not -1
						//signify that a ship has been hit
						g.setPlayer2Ships(i, j, -1);
						g.setField2Shown(i, j, 'Ø');
						g.setField2Hidden(i, j, 'Ø');
					}
					else {
						//if there hasn't been a previous ship cell that has been hit heree
						if(g.getField2Hidden(i, j)!='Ø') {
							//signify that an empty cell has been hit
							g.setField2Hidden(i, j, '/');
							g.setField2Shown(i, j, '/');
						}
					}
				}

			}
		}
		//delete weapon from p1's inventory
		p1.delWeapon(3);
		this.makeSound();
	}

	@Override
	/*p2Attack(Player p2, GameField g, int x, int y)
	  
	  returns nothing
	  
	  Player p2 - this parameter is the player that is attacking
	  GameField g - this parameter is the GameField that the game takes place on
	  int x - this parameter is the x-coordinate of the target
	  int y - this parameter is the y-coordinate of the target
	  
	  this method lets Player p2 attack their opponent at
	  coordinate (x, y)
	 */
	public void p2Attack(Player p2, GameField g, int x, int y) {
		// TODO Auto-generated method stub
		for(int i = y; i>y-2; i--) {
			for(int j = x; j>x-2; j--) {
				//if i and j are within the board
				if(j>=0 && j<=24 && i>=0 && i<=24) {
					if(g.getPlayer1Ships()[i][j]!=-1) { //if the current cell equals -1
						//signify that a ship has been hit
						g.setPlayer1Ships(i, j, -1);
						g.setField1Shown(i, j, 'Ø');
						g.setField1Hidden(i, j, 'Ø');
					}
					else {
						//if there hasn't been a previous ship cell that has been hit here
						if(g.getField1Hidden(i, j)!='Ø') {
							//signify that an empty cell has been hit
							g.setField1Hidden(i, j, '/');
							g.setField1Shown(i, j, '/');
						}
					}
				}
				
			}
		}
		//delete weapon from p2's inventory
		p2.delWeapon(3);
		this.makeSound();
	}
}
