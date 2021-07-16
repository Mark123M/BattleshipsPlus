package Battleships;
/*
	Weapon.java
	Author:  Mark Ma
	Class:  ICS4U
	Last Modified:  June 23rd, 2021
	This class is an abstract class that represents the weapons/ammo that is fired by ships during battle.
	It contains 2 abstract methods for the attack function as well as 1 abstract method for sound effects.
*/
public abstract class Weapon { //demonstrates encapsulation and polymorphism
	//instance fields
	protected String name, desc, placement;
	protected int cost;
	
	//accessor method for cost of weapon
	public int getCost() {
		return this.cost;
	}
	/*String stringInfo()
	  
	  returns String - the info of this weapon
	  
	  no parameters
	  
	  this method creates and returns a string about the info
	  of this weapon
	 
	 */
	public String stringInfo() {
		return name+": "+desc+"	cost: $"+cost+"\narea hit: \n"+this.placement;
	}
	//abstract methods 
	abstract void makeSound();
	abstract void p1Attack(Player p1, GameField g, int x, int y);
	abstract void p2Attack(Player p2, GameField g, int x, int y);
}
