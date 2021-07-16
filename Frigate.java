package Battleships;
/*
Frigate.java
Author:  Mark Ma
Class:  ICS4U
Last Modified:  June 24th, 2021
This is a child class of Ship.java that represents an agile Frigate.
It contains appropriate fields and is also capable of performing several operations.
*/
//demonstrates inheritance
public class Frigate extends Ship{

	/*Frigate()
	  
	  returns Frigate - the default instance of Frigate
	  
	  no parameters
	  
	  This constructor creates the default Frigate given no parameters,
	  by assigning their pre-determined fields 
	 
	 */
	public Frigate() {
		//assign fields to pre-determined values
		this.name = "Frigate";
		this.art = "         |_\r\n" + 
				"   _____|~ |____\r\n" + 
				"  (  --         ~~~~--_,\r\n" + 
				"   ~~~~~~~~~~~~~~~~~~~'`";
		this.desc = "\"A light and fast watercraft capable of independant underwater operations...\"\n"
				+ "This ship has 1 mounted gun";
		this.cost = 1000;
		healthPoints = 5; 
		this.placement = "XOOOO\n";
		this.length = 5;
		this.width = 1;
		this.numGuns = 1;
		this.numCannons = 0;
	}
	
	/*Frigate(Ship other)
	  
	  returns Frigate - the Frigate instance created by copying the fields of another ship
	  
	  Ship other - this parameter is the Ship that is being copied
	  
	  This constructor creates a Frigate by copying the fields of
	  the other Ship parameter
	 */
	
	public Frigate(Ship other) {
		//assign all its fields to other's fields
		this.name = other.name;
		this.art = other.art;
		this.desc = other.desc;
		this.cost = other.cost;
		this.healthPoints = other.healthPoints;
		this.x = other.x;
		this.y = other.y;
		this.length = other.length;
		this.width = other.width;
		this.numGuns = other.numGuns;
		this.numCannons = other.numCannons;
	}
}
