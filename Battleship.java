package Battleships;

/*
Battleship.java
Author:  Mark Ma
Class:  ICS4U
Last Modified:  June 24th, 2021
This is a child class of Ship.java that represents a standard heavy-duty battleship.
It contains appropriate fields and is also capable 
of performing several operations.
*/

//demonstrates inheritance
public class Battleship extends Ship{
	
	/*Battleship()
	  
	  returns Battleship - the default instance of Battleship
	  
	  no parameters
	  
	  This constructor creates the default Battleship given no parameters,
	  by assigning their pre-determined fields 
	 
	 */
	public Battleship() {
		//assign fields to pre-determined values
		this.name = "Battleship";
		this.art = "                                  )___(\r\n" + 
				"                           _______/__/_\r\n" + 
				"                  ___     /===========|   ___\r\n" + 
				" ____       __   [\\\\\\]___/____________|__[///]   __\r\n" + 
				" \\   \\_____[\\\\]__/___________________________\\__[//]___\r\n" + 
				"  \\40A                                                 |\r\n" + 
				"   \\                                                  /\r\n" + 
				"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
		this.desc = "\"A large armored warship with a main battery consisting of heavy caliber guns...\"\n "
				  + "This ship has 3 mounted guns and 2 cannons";
		this.cost = 3500;
		this.healthPoints = 16; 
		this.placement = "xOOOOOOO\nOOOOOOOO\n";
		this.length = 8;
		this.width = 2;
		this.numGuns = 3;
		this.numCannons = 2;
	}
	/*Battleship(Ship other)
	  
	  returns Battleship - the Battleship instance created by copying the fields of another ship
	  
	  Ship other - this parameter is the Ship that is being copied
	  
	  This constructor creates a Battleship by copying the fields of
	  the other Ship parameter
	 
	 */
	public Battleship(Ship other) {
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
