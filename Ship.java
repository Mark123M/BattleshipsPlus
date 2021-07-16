package Battleships;

/*
Ship.java
Author:  Mark Ma
Class:  ICS4U
Last Modified:  June 24th, 2021
This class is an abstract class that represents the warships. 
It contains appropriate fields for a warship and is also capable 
of performing several operations
*/

public abstract class Ship { //demonstrates encapsulation
	
	//instance fields
	protected String name, art, desc, placement;
	protected int x, y, length, width, cost, healthPoints, numGuns, numCannons;
	
	/*String stringInfo()
	  
	  returns String - The basic information about the ship.
	  
	  no parameters
	  
	  this method creates and returns a string carrying the basic 
	  information about a ship.
	 */
	public String stringInfo() {
		String info; //declare variable
		
		info = "Model: "+name+" \n"+art+"\nDesc: "+desc; //creates string
		
		return info;
	}
	
	/*String stringStats()
	  
	  returns String - The stats of the ship instance.
	  
	  no parameters
	  
	  this method creates and returns a string carrying the stats of a 
	  ship instance, including HP, #of guns, # of cannons, location
	 */
	public String stringStats() {
		String stats; //declare variable
		
		//creates string
		stats = "HP: "+healthPoints+"\n# of guns: "+numGuns+"		# of cannons: "+numCannons+"\nMap location:"+x+", "+y;
		
		return stats;
	}

	//accessor method for name of ship
	public String getName() {
		return this.name;
	}
	//accessor method for ship placement
	public String getPlacement() {
		return this.placement;
	}
	//accessor method for HP of ship
	public int getHp() {
		return this.healthPoints;
	}
	//accessor method for x-coordinate of ship
	public int getX() {
		return this.x;
	}
	//accessor method for y-coordinate of ship
	public int getY() {
		return this.y;
	}
	//accessor method for length of ship
	public int getLength() {
		return this.length;
	}
	//accessor method for width of ship
	public int getWidth() {
		return this.width;
	}
	
	//accessor method for cost of ship
	public int getCost() {
		return this.cost;
	}
	//accessor method for # of guns on a ship
	public int getNumGuns() {
		return this.numGuns;
	}
	//accessor method for # of cannons on a ship
	public int getNumCannons() {
		return this.numCannons;
	}
	//mutator method for HP of ship
	public void setHp(int hp) {
		this.healthPoints = hp;
	}
	//mutator method for x-coordinate of ship
	public void setX(int x) {
		this.x = x;
	}
	//mutator method for y-coordinate of ship
	public void setY(int y) {
		this.y = y;
	}
	//mutator method for length of ship
	public void setLength(int len) {
		this.length = len;
	}
	//mutator method for width of ship
	public void setWidth(int wid) {
		this.width = wid;
	}
}
