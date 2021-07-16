package Battleships;
/*
Weapon.java
Author:  Mark Ma
Class:  ICS4U
Last Modified:  June 23rd, 2021
This class is a child class of Weapon.java and represents a basic attack, where 1 tile is hit.
it contains functions that overrides and fulfills the operations of a weapon
*/

public class BasicShot extends Weapon { //demonstrates inheritance
	/*BasicShot()
	  
	  returns BasicShot - the default instance of BasicShot
	  
	  no parameters
	  
	  This constructor creates the default BasicShot given no parameters,
	  by assigning their pre-determined fields 
	 
	 */
	public BasicShot() {
		this.name = "Basic shot";
		this.desc = "Used with ships that has mounted guns, hits 1 specified tile"; //#
		this.cost = 200;
		this.placement = "x where x is the target coordinate";
	}
	@Override
	public void makeSound() {
		System.out.println("Pew!");
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
		//if x and y is within the game board
		if(x>=0 && x<=24 &&y>=0 && y <=24) {
			if(g.getPlayer2Ships()[y][x]!=-1) { //if the current cell is not -1
				//signify on the game board the a ship has been hit
				g.setPlayer2Ships(y, x, -1);
				g.setField2Shown(y, x, 'Ø');
				g.setField2Hidden(y, x, 'Ø'); 
			}
			else {
				//if there hasn't been a previous ship cell hit here
				if(g.getField2Hidden(y, x)!='Ø') {
					//signify that an empty tile has been hit
					g.setField2Shown(y, x, '/');
					g.setField2Hidden(y, x, '/'); 
				}
			}
		}
		//delete the weapon from p1's inventory
		p1.delWeapon(1);
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
		//if x and y is within the game board
		if(x>=0 && x<=24 &&y>=0 && y <=24) { 
			if(g.getPlayer1Ships()[y][x]!=-1) { //if the current cell is not -1
				//signify on the game board the a ship has been hit
				g.setPlayer1Ships(y, x, -1);
				g.setField1Shown(y, x, 'Ø');
				g.setField1Hidden(y, x, 'Ø'); 
			}
			else {
				//if there hasn't been a previous ship cell hit here
				if(g.getField1Hidden(y, x)!='Ø') {
					//signify that an empty tile has been hit
					g.setField1Shown(y, x, '/');
					g.setField1Hidden(y, x, '/'); 
				}
			}
		}
		//delete the weapon from p2's inventory
		p2.delWeapon(1);
		this.makeSound();

	}
}
