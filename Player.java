package Battleships;
/*
Player.java
Author:  Mark Ma
Class:  ICS4U
Last Modified:  June 24th, 2021
This class is a class that represents the players in a 1v1 game. It contains the 
appropriate fields and is also capable of performing several
operations. 
*/

public class Player {
	//instance fields 
	private String name; 
	private int balance;
	private Ship[] fleet;
	private int armory[]; 
	
	/*Player(Profile p, int balance)
	  
	  returns Player - the instance created by constructor
	  
	  Profile p - this parameter is the Profile that we are making a player from
	  int balance - this parameter is the starting balancing of a player
	  
	  This constructor creates a new Player based on the given parameters.

	 */
	public Player(Profile p, int balance) {
		this.name = p.getName();
		this.balance = balance;
		fleet = new Ship[0]; 
		armory = new int[5];
	}
	//accessor method for the cells of armory
	public int getWeaponCount(int weaponIndex) {
		return armory[weaponIndex];
	}
	//mutator method for adding a weapon
	public void addWeapon(int weaponIndex) {
		armory[weaponIndex]+=1;
	}
	//mutator method for deleting a weapon
	public void delWeapon(int weaponIndex) {
		armory[weaponIndex]-=1;
	}
	//accessor method for the player's fleet
	public Ship[] getFleet() {
		return this.fleet;
	}
	//mutator method for the player's balance
	public void setBal(int balance) {
		this.balance = balance;
	}
	//accessor method for the player's balance
	public int getBal() {
		return this.balance;
	}
	//accessor method for the player's name
	public String getName() {
		return this.name;
	}
	
	/* void addShip(Ship newShip)
	 * 
	 * returns nothing
	 * 
	 * Ship newShip - this parameter is the new ship that is going to be added 
	 * 
	 * This method adds a new Ship into the player's fleet
	 */
	public void addShip(Ship newShip) {
		//create a new fleet size 1 greater than the current
		Ship[] newFleet = new Ship[fleet.length+1];
		
		//copy every ship in the current fleet into the new one
		for(int i = 0; i<fleet.length; i++) {
			if(fleet[i] instanceof Battleship) {
				newFleet[i] = new Battleship(fleet[i]);
			} 
			else if (fleet[i] instanceof Destroyer) {
				newFleet[i] = new Destroyer(fleet[i]);
			}
			else if (fleet[i] instanceof Frigate) {
				newFleet[i] = new Frigate(fleet[i]);
			}
			else if (fleet[i] instanceof CargoShip){
				newFleet[i] = new CargoShip(fleet[i]);
			}
		}
		//add the new ship at the end
		newFleet[fleet.length] = newShip;
		fleet = newFleet; //assign the reference of fleet with the reference of newfleet
	}
	/* void delShip(int shipIdx)
	 * 
	 * returns nothing
	 * 
	 * int shipIdx - this parameter is the index of the ship that is going to be deleted
	 * 
	 * This method deletes the ship at the index specified by the parameters
	 */
	public void delShip(int shipIdx) {
		//make a newFleet with size 1 less than current fleet
		Ship[] newFleet = new Ship[fleet.length-1];
		
		//copy the first ships up to shipIdx
		for(int i = 0; i<shipIdx; i++) {
			if(fleet[i] instanceof Battleship) {
				newFleet[i] = new Battleship(fleet[i]);
			} 
			else if (fleet[i] instanceof Destroyer) {
				newFleet[i] = new Destroyer(fleet[i]);
			}
			else if (fleet[i] instanceof Frigate) {
				newFleet[i] = new Frigate(fleet[i]);
			}
			else if (fleet[i] instanceof CargoShip){
				newFleet[i] = new CargoShip(fleet[i]);
			}
		/*	else {
				newFleet[i] = new Submarine(fleet[i]);
			} */
		}
		//skip the shipIdx and loop until the end, assign the current ship to i-1 of the newfleet
		for(int i = shipIdx+1; i<fleet.length; i++) {
			if(fleet[i] instanceof Battleship) {
				newFleet[i-1] = new Battleship(fleet[i]);
			} 
			else if (fleet[i] instanceof Destroyer) {
				newFleet[i-1] = new Destroyer(fleet[i]);
			}
			else if (fleet[i] instanceof Frigate) {
				newFleet[i-1] = new Frigate(fleet[i]);
			}
			else if (fleet[i] instanceof CargoShip){
				newFleet[i-1] = new CargoShip(fleet[i]);
			}
		}
		fleet = newFleet; //assign the reference of fleet with the reference of newfleet
	}

}
