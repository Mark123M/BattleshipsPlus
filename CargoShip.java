package Battleships;
/*
CargoShip.java
Author:  Mark Ma
Class:  ICS4U
Last Modified:  June 24th, 2021
This is a child class of Ship.java that represents a standard CargoShip that generates revenue.
It contains appropriate fields and is also capable of performing several operations.
*/

//demonstrates inheritance
public class CargoShip extends Ship{

	/*Cargoship()
	  
	  returns Cargoship - the default instance of Cargoship
	  
	  no parameters
	  
	  This constructor creates the default Cargoship given no parameters,
	  by assigning their pre-determined fields 
	 
	 */
	public CargoShip() {
		//assign fields to pre-determined values
		this.name = "Cargo ship";
		this.art = "	\r\n" + 
				"                       $o\r\n" + 
				"                       $                     .........\r\n" + 
				"                      $$$      .oo..     'oooo'oooo'ooooooooo....\r\n" + 
				"                       $       $$$$$$$\r\n" + 
				"                   .ooooooo.   $$!!!!!\r\n" + 
				"                 .'.........'. $$!!!!!      o$$oo.   ...oo,oooo,oooo'ooo''\r\n" + 
				"    $          .o'  oooooo   '.$$!!!!!      $$!!!!!       'oo''oooo''\r\n" + 
				" ..o$ooo...    $                '!!''!.     $$!!!!!\r\n" + 
				" $    ..  '''oo$$$$$$$$$$$$$.    '    'oo.  $$!!!!!\r\n" + 
				" !.......      '''..$$ $$ $$$   ..        '.$$!!''!\r\n" + 
				" !!$$$!!!!!!!!oooo......   '''  $$ $$ :o           'oo.\r\n" + 
				" !!$$$!!!$$!$$!!!!!!!!!!oo.....     ' ''  o$$o .      ''oo..\r\n" + 
				" !!!$$!!!!!!!!!!!!!!!!!!!!!!!!!!!!ooooo..      'o  oo..    $\r\n" + 
				"  '!!$$!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!oooooo..  ''   ,$\r\n" + 
				"   '!!$!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!oooo..$$\r\n" + 
				"    !!$!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!$'\r\n" + 
				"    '$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$!!!!!!!!!!!!!!!!!!,\r\n" + 
				".....$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$.....";
		this.desc = "\"Also called a freighter, this is a merchant ship that tranports cargo, goods, and materials...\"\n"
				+ "This ship generates 500 dollars per turn. It can't attack.";
		this.cost = 3000;
		healthPoints = 18; 
		this.placement = "XOOOOO\nOOOOOO\nOOOOOO\n";
		this.length = 6;
		this.width = 3;
		this.numGuns = 0;
		this.numCannons = 0;
	}
	/*CargoShip(Ship other)
	  
	  returns CargoShip - the CargoShip instance created by copying the fields of another ship
	  
	  Ship other - this parameter is the Ship that is being copied
	  
	  This constructor creates a CargoShip by copying the fields of
	  the other Ship parameter
	 
	 */
	public CargoShip(Ship other) {
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
