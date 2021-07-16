package Battleships;
/*
GuidedMissle.java
Author:  Mark Ma
Class:  ICS4U
Last Modified:  June 23rd, 2021
This class is a child class of Weapon.java and represents a Guided missle attack, where the nearest ship 
from target is hit. it contains functions that overrides and fulfills the operations of a weapon
*/
public class GuidedMissle extends Weapon { //demonstrates inheritance
 
	private Player p1, p2; //2 fields referencing the location as p1 and p2 so i could use them to attack
	
	/*GuidedMissle(Player p1, Player p2)
	  
	  returns GuidedMissle - the default instance of CarpetBomb
	  
	  Player p1 - This parameter is the reference to p1
	  Player p2 - this parameter is the reference to p2
	  
	  This constructor creates the default GuidedMissle,
	  by assigning their pre-determined fields 
	 
	 */
	public GuidedMissle(Player p1, Player p2) {
		this.name = "Guided Missle";
		this.desc = "Used with ships that has mounted cannons, hits the nearest target from the specified tile"; 
		this.cost = 1500;                                                                                  
		this.p1 = p1;
		this.p2 = p2;
		this.placement = "closest ship tile from the target coordinate";
	}
	public void makeSound() {
		System.out.println("Pfshoooo-BOOOM!");
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
		//declare variables
		int minDistSquared;
		int closestShipIdx;

		//initialize variables
		minDistSquared = Integer.MAX_VALUE;
		closestShipIdx = -1;

		//find closest enemy ship
		for(int i = 0; i<p2.getFleet().length; i++) {
			if(minDistSquared>(p2.getFleet()[i].getY() - y) * (p2.getFleet()[i].getY() - y) +  (p2.getFleet()[i].getX() - x) * (p2.getFleet()[i].getX() - x)) {
				//update minDistSquared and closestShipIdx
				minDistSquared = (p2.getFleet()[i].getY() - y) * (p2.getFleet()[i].getY() - y) +  (p2.getFleet()[i].getX() - x) * (p2.getFleet()[i].getX() - x);
				closestShipIdx = i;
			}
		}
		//signify that the closest ship from x,y is hit
		g.setPlayer2Ships(p2.getFleet()[closestShipIdx].y, p2.getFleet()[closestShipIdx].x, -1);
		g.setField2Hidden(p2.getFleet()[closestShipIdx].y, p2.getFleet()[closestShipIdx].x, 'Ø');
		g.setField2Shown(p2.getFleet()[closestShipIdx].y, p2.getFleet()[closestShipIdx].x, 'Ø');
		//delete weapon from p1's inventory
		p1.delWeapon(4);
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
		//declare variables
		int minDistSquared;
		int closestShipIdx;

		//initialize variables
		minDistSquared = Integer.MAX_VALUE;
		closestShipIdx = -1;

		//find closest enemy ship
		for(int i = 0; i<p1.getFleet().length; i++) {
			if(minDistSquared>(p1.getFleet()[i].getY() - y) * (p1.getFleet()[i].getY() - y) +  (p1.getFleet()[i].getX() - x) * (p1.getFleet()[i].getX() - x)) {
				//update minDistSquared and closestShipIdx
				minDistSquared = (p1.getFleet()[i].getY() - y) * (p1.getFleet()[i].getY() - y) +  (p1.getFleet()[i].getX() - x) * (p1.getFleet()[i].getX() - x);
				closestShipIdx = i;
			}
		}
		//signify that the closest ship from x,y is hit
		g.setPlayer1Ships(p1.getFleet()[closestShipIdx].y, p1.getFleet()[closestShipIdx].x, -1);
		g.setField1Hidden(p1.getFleet()[closestShipIdx].y, p1.getFleet()[closestShipIdx].x, 'Ø');
		g.setField1Shown(p1.getFleet()[closestShipIdx].y, p1.getFleet()[closestShipIdx].x, 'Ø');
		//delete weapon from p2's inventory
		p2.delWeapon(4);
		this.makeSound();
	}
}
