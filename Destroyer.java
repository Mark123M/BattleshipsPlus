package Battleships;
/*
Destroyer.java
Author:  Mark Ma
Class:  ICS4U
Last Modified:  June 24th, 2021
This is a child class of Ship.java that represents a lightweight Destroyer.
It contains appropriate fields and is also capable of performing several operations.
*/
//demonstrates inheritance
public class Destroyer extends Ship{
	
	/*Destroyer()
	  
	  returns Destroyer - the default instance of Destroyer
	  
	  no parameters
	  
	  This constructor creates the default Destroyer given no parameters,
	  by assigning their pre-determined fields 
	 
	 */

	public Destroyer() {
		//assign fields to pre-determined values
		this.name = "Destroyer";
		this.art = "                 __/___\r\n" + 
				"           _____/______|\r\n" + 
				"   _______/_____\\_______\\_____\r\n" + 
				"   \\              < < <       |\r\n" + 
				"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
		this.desc = "\"A fast, maneuverable, long-endurance warship that is feared on the ocean surface...\"\n"
				  + "This ship has 2 mounted guns and 1 cannon";
		this.cost = 2000;
		healthPoints = 10; 
		this.placement = "XOOOO\nOOOOO\n";	
		this.length = 5;
		this.width = 2;
		this.numGuns = 2;
		this.numCannons = 1;
	}
	
	/*Destroyer(Ship other)
	  
	  returns Destroyer - the Destroyer instance created by copying the fields of another ship
	  
	  Ship other - this parameter is the Ship that is being copied
	  
	  This constructor creates a Destroyer by copying the fields of
	  the other Ship parameter
	 */
	public Destroyer(Ship other) {
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
