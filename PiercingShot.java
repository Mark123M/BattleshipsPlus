package Battleships;
/*
PiercingShot.java
Author:  Mark Ma
Class:  ICS4U
Last Modified:  June 23rd, 2021
This class is a child class of Weapon.java and represents a piercing attack, where 3 vertical tiles are hit.
it contains functions that overrides and fulfills the operations of a weapon
*/

public class PiercingShot extends Weapon { //demonstrates inheritance
	/*PiercingShot()
	  
	  returns PiercingShot - the default instance of BasicShot
	  
	  no parameters
	  
	  This constructor creates the default PiercingShot given no parameters,
	  by assigning their pre-determined fields 
	 
	 */
	public PiercingShot() {
		this.name = "Piercing shot";
		this.desc = "Used with ships that has mounted guns, hits 3 tiles vertically from the specified tile"; //#
		this.cost = 800;
		this.placement = "* where x is the target coordinate \n*\nx";
	}
	public int getCost() {
		return super.getCost();
	}
	public void makeSound() {
		System.out.println("Pshhhhew!");
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
	void p1Attack(Player p1, GameField g, int x, int y) {
		for(int i = y; i>y-3; i--) {
			//if i and x are within the board
			if(x>=0 && x<=24 &&i>=0 && i <=24) {
				if(g.getPlayer2Ships()[i][x]!=-1) {
					//signify on the game board the a ship has been hit
					g.setPlayer2Ships(i, x, -1);
					g.setField2Hidden(i, x, 'Ø'); 
					g.setField2Shown(i, x, 'Ø');
				}
				else {
					//if a previous ship cell wasn't hit here
					if(g.getField2Hidden(i, x)!='Ø') {
						//signify that an empty tile has been hit
						g.setField2Hidden(i, x, '/'); 
						g.setField2Shown(i, x, '/');
					}
				}
			}
		}
		//delete weapon from p1's inventory
		p1.delWeapon(2);
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
	void p2Attack(Player p2, GameField g, int x, int y) {
		for(int i = y; i>y-3; i--) {
			//if i and x are within the board
			if(x>=0 && x<=24 &&i>=0 && i <=24) {
				if(g.getPlayer1Ships()[i][x]!=-1) {
					//signify on the game board the a ship has been hit
					g.setPlayer1Ships(i, x, -1);
					g.setField1Hidden(i, x, 'Ø'); 
					g.setField1Shown(i, x, 'Ø');
				}
				else {
					//if a previous ship cell wasn't hit here
					//signify that an empty tile has been hit
					if(g.getField1Hidden(i, x)!='Ø') {
						g.setField1Hidden(i, x, '/'); 
						g.setField1Shown(i, x, '/');
					}
				}
			}
		}
		//delete weapon from p2's inventory
		p2.delWeapon(2);
		this.makeSound();

	}
}
