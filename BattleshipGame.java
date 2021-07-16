package Battleships;
import java.util.*;
import java.io.*;
/*
BattleshipGame.java
Author:  Mark Ma
Class:  ICS4U
Last Modified:  June 23rd, 2021
	This program is used to run and play the main "Battleships plus" game. It incorporates
	all the other classes as well as external code to create this functioning program. 
	This game is an upgraded version of the classic battleships game, with a variety of ships
	and weapons and a intricate profile and ranking system. It is highly recommended to read the info section 
	from the main menu before battling. 
	
	Also, sometimes large amounts of text show up, read things carefully.
*/
public class BattleshipGame {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		//declare variables for loading/saving and registration
		final String fileName = "profileList.txt";
		String[] linesIn; //array for reading in each profile from profileList.txt
		ArrayList<Profile> list = new ArrayList<>();
		String newUserName, newUserCountry;

		int selection; // declare variable for menu selections
		
		//declare variables for battling
		int player1Index, player2Index, startingBal; //indicies of the 2 players within the profile list
		Player p1, p2; 
		Ship newShip;
		int orientation;
		boolean skipPurchase;
		boolean placable;
		GameField g; 
		int gunCount, cannonCount, hitCount, usedGuns, usedCannons;
		int attackX, attackY;

		//declare variables for searching
		boolean foundName;
		boolean foundLvl;
		int searchedLvl;
		String searchedName;

		//initialize variables
		p1 = null; 
		p2 = null;
		linesIn = new String[8];

		//declare constant arrays for default ship and weapon types
		final Ship[] allShips = {new Battleship(), new Destroyer(), new Frigate(), new CargoShip()};
		final Weapon[] allWeapons = {new BasicShot(), new PiercingShot(), new CarpetBomb(), new GuidedMissle(p1, p2)};



		//loads all saved profiles into the ArrayList of profiles
		//demonstrates File input
		try {
			BufferedReader in = new BufferedReader(new FileReader(fileName));

			//reads first line of the first profile block
			linesIn[0] = in.readLine();

			//continues reading from file until no line is left
			while(linesIn[0]!=null) {
				
				//read the rest of the profile block
				linesIn[1] = in.readLine();
				linesIn[2] = in.readLine();
				linesIn[3] = in.readLine();
				linesIn[4] = in.readLine();
				linesIn[5] = in.readLine();
				linesIn[6] = in.readLine();
				linesIn[7] = in.readLine();

				//use constructor to create a new profile with the information stored in linesIn and add to the arraylist of Profiles
				Profile p = new Profile(linesIn[0], linesIn[1], linesIn[2], linesIn[3], linesIn[4], linesIn[5], linesIn[6], linesIn[7]);
				list.add(p);
				
				//read the start of the next profile block
				linesIn[0] = in.readLine();
			}
			in.close(); //close reader
		}
		catch(IOException e) {
			System.out.println(e+" Problem reading "+fileName); //exception message
		}

		
		System.out.println("<> Welcome to Battleships Plus <>");

		//prompting for input for the main menu options
		selection = askInput(sc, "\n[1] Battle!\n[2] Leaderboards\n[3] Register \n[4] Info\n[5] Search Players\n[6] Quit\n");
		//keep prompting if the input is out of range or invalid data type
		while(selection!=1&&selection!=2&&selection!=3&&selection!=4&&selection!=5&&selection!=6) {
			System.out.println("Please enter a valid option");
			selection = askInput(sc, "\n[1] Battle!\n[2] Leaderboards\n[3] Register \n[4] Info\n[5] Search Players\n[6] Quit\n");
		}
		
		//the game runs until the user enters 6
		while(selection!=6) {
			
			//Leaderboards System - demonstrates sorting with two criterias
			if(selection==2) {
				System.out.println("<> War Heroes <>");
				
				//prompt for input for menu options
				selection = askInput(sc, "[1] sort by experience\n[2] Sort by winrate");
				//keep prompting if the input is out of range or invalid
				while(selection!=1&&selection!=2) {
					System.out.println("Please enter a valid option");
					selection = askInput(sc, "[1] sort by experience\n[2] Sort by winrate");
				}
				System.out.println();
				
				//sort all profiles by descending experience and print 
				if(selection == 1) {
					System.out.println("<> The Most Experienced <>");
					sortByExp(list); //selection sort the profiles
					for(int i = 0; i<list.size(); i++) {
						System.out.println(list.get(i).toString()+"\n");
					}
				}
				//sort all profiles by descending winrate and print
				else {
					System.out.println("<> The Best Winrates <>");
					sortByWinrate(list); //selection sort the profiles
					for(int i = 0; i<list.size(); i++) {
						System.out.println(list.get(i).toString()+"\n");
					}
				}
			}
			//Registration system - demonstrates File output with saving system
			else if(selection == 3) {
				System.out.println("Welcome to the crew! :)");
				
				//prompt user for name and country
				System.out.print("Enter your username: ");
				sc.nextLine();
				newUserName = sc.nextLine();
				System.out.print("Enter your country: ");
				newUserCountry = sc.nextLine();
			
				//keep prompting if an account with that name already exists
				while(searchName(newUserName, list)!= -1) { //search list if the name is taken
					System.out.print("Sorry, that name is taken. Try another one: "); //notify user
					newUserName = sc.nextLine();
				}
				
				//create and add the new profile and notify the user.
				list.add(new Profile(newUserName, newUserCountry));
				System.out.println("Registration successful! Have fun!");

				saveProfiles(list, fileName); //save profile list
			}
			//Combat system - demonstrates encapsulation, polymorphism, inheritance, array of objects, recursion
			//*this section is long, so usage indicators would be provided in the classes of the objects
			else if(selection == 1) { //Evan: make error message for negative money, display board at the start of every turn, purchase multiple weapons //should have had an option to go back on a menu 
				
				//initialize game field
				g = new GameField();

				sc.nextLine();
				
				//prompt players 1 and 2 to log in and finds and assigns their indexes in the profile list 
				System.out.println("Player 1, login!");
				player1Index = searchName(sc.nextLine(), list);
				System.out.println("Player 2, login!");
				player2Index = searchName(sc.nextLine(), list);

				//prompting players for their starting balance
				System.out.print("Enter the starting balance of both players: ");
				startingBal = sc.nextInt();

				//initialize both players by creating two instances given their names and starting balance 
				p1 = new Player(list.get(player1Index), startingBal);
				p2 = new Player(list.get(player2Index), startingBal);

				System.out.println();


				//buying phase, where each player buys ships and positions them on the field
				
				//prompt player1 to select ships to buy
				System.out.println(p1.getName()+"'s buying phase, pick one below:");
				selection = askInput(sc, "[1] Battleship ($"+allShips[0].getCost()+")\n[2] Destroyer ($"+allShips[1].getCost()+")\n[3] Frigate ($"+allShips[2].getCost()+")\n[4] Cargo Ship ($"+allShips[3].getCost()+")\n[5] End phase\n");

				//keep prompting until 5 is entered
				while(selection!=5) {
					newShip = new Battleship(); //initialized newShip for scoping
					
					skipPurchase = false; //initialize variable
					
					//switch case statement to select and create a ship
					switch(selection) {
					case 1:
						newShip = new Battleship();
						break;
					case 2:
						newShip = new Destroyer();
						break;
					case 3:
						newShip = new Frigate();
						break;
					case 4:
						newShip = new CargoShip();
						break;
					case 5: 
						System.out.println("Get ready!");
						skipPurchase = true; //skip purchase if 5 is entered
						break;
					default:
						System.out.println("Please enter a valid option");
						skipPurchase = true; //skip purchase if an invalid option is entered
					}
					if(!skipPurchase) {
						//if the ship could be afforded
						if(p1.getBal()-newShip.getCost()>=0) {
							//prompt player for the coordinates of ship
							System.out.print("Enter the x-coordinate: ");
							newShip.setX(sc.nextInt());
							System.out.print("Enter the y-coordinate: ");
							newShip.setY(sc.nextInt());

							//prompt player for the orientation of the ship
							orientation = askInput(sc, "Orientation: [1] Horizontal, [2] Vertical");
							//keep prompting if a number out of bounds or an invalid data is entered
							while(orientation!=1&&orientation!=2) {
								System.out.println("Please select a valid option");
								orientation = askInput(sc, "Orientation: [1] Horizontal, [2] Vertical");
							}

							//switch the width and the height if the ship is vertical
							if(orientation == 2) {
								int tmp = newShip.getLength();
								newShip.setLength(newShip.getWidth());
								newShip.setWidth(tmp);
							}

							//checks if the current ship can be placed on the field
							placable = g.checkAndAddShip(1, p1.getFleet().length, newShip.getX(), newShip.getY(), newShip.getLength(), newShip.getWidth());

							//if the ship could be placed
							if(placable) {
								//add the ship, update the balance, and show its placement on the player's field
								p1.addShip(newShip);
								p1.setBal(p1.getBal()-newShip.getCost());
								g.showPlayer1Field();
								
								//print the ship's stats
								System.out.println(p1.getFleet()[p1.getFleet().length-1].stringStats());
							}
							else {
								//notify user if ship cant be placed
								System.out.println("Invalid placement, either out of bounds or colliding with another ship");
							}
						}
						else {
							//notify user if they can't afford ship
							System.out.println("Insufficient funds.");
						}
					}
					//show the remaining balance and prompt again
					System.out.println("Remaining balance: $"+p1.getBal());
					System.out.println();
					selection = askInput(sc, "[1] Battleship ($"+allShips[0].getCost()+")\n[2] Destroyer ($"+allShips[1].getCost()+")\n[3] Frigate ($"+allShips[2].getCost()+")\n[4] Cargo Ship ($"+allShips[3].getCost()+")\n[5] End phase\n");
				}
				
				//prompt player2 for ships to buy
				System.out.println(p2.getName()+"'s buying phase, pick one below:");
				selection = askInput(sc, "[1] Battleship ($"+allShips[0].getCost()+")\n[2] Destroyer ($"+allShips[1].getCost()+")\n[3] Frigate ($"+allShips[2].getCost()+")\n[4] Cargo Ship ($"+allShips[3].getCost()+")\n[5] End phase\n");

				//keep prompting for purchases until 5 is entered
				while(selection!=5) {
					newShip = new Battleship(); //initialized newShip to take care of scoping
					
					skipPurchase = false; //initialize variable

					//switch case statement to select and create a ship
					switch(selection) {
					case 1:
						newShip = new Battleship();
						break;
					case 2:
						newShip = new Destroyer();
						break;
					case 3:
						newShip = new Frigate();
						break;
					case 4:
						newShip = new CargoShip();
						break;
					case 5:
						System.out.println("Get ready!");
						skipPurchase = true; //if 5 is entered, purchase should be skipped
						break;
					default:
						System.out.println("Please enter a valid option");
						skipPurchase = true; //skip purchase if invalid option is entered
					}
					if(!skipPurchase) {
						//if player can afford ship
						if(p2.getBal()-newShip.getCost()>=0) {
							
							//prompt for the coordinates of the ship
							System.out.print("Enter the x coordinate: ");
							newShip.setX(sc.nextInt());
							System.out.print("Enter the y coordinate: ");
							newShip.setY(sc.nextInt());

							//prompt player for the orientation of their ship
							orientation = askInput(sc, "Orientation: [1] Horizontal, [2] Vertical");
							//keep prompting if selection is out of range or an invalid type
							while(orientation!=1&&orientation!=2) {
								System.out.println("Please select a valid option");
								orientation = askInput(sc, "Orientation: [1] Horizontal, [2] Vertical");
							}

							//swtich the width and the height if the orientation is vertical
							if(orientation == 2) {
								int tmp = newShip.getLength();
								newShip.setLength(newShip.getWidth());
								newShip.setWidth(tmp);
							}

							//checks if the current ship can be placed on the field
							placable = g.checkAndAddShip(2, p2.getFleet().length, newShip.getX(), newShip.getY(), newShip.getLength(), newShip.getWidth());

							//if the ship can be placed
							if(placable) {
								//add ship, update balance, and show player2's field.
								p2.addShip(newShip);
								p2.setBal(p2.getBal()-newShip.getCost());
								g.showPlayer2Field();
								
								//display the stats of the new ship
								System.out.println(p2.getFleet()[p2.getFleet().length-1].stringStats());
							}
							else {
								//notify user if ship can't be placed
								System.out.println("Invalid placement, either out of bounds or colliding with another ship");
							}
						}
						else {
							//notify user if ship is too expensive
							System.out.println("Insufficient funds.");
						}
					}
					//show remaining balance and prompting for purchases
					System.out.println("Remaining balance: $"+p2.getBal());
					System.out.println();
					selection = askInput(sc, "[1] Battleship ($"+allShips[0].getCost()+")\n[2] Destroyer ($"+allShips[1].getCost()+")\n[3] Frigate ($"+allShips[2].getCost()+")\n[4] Cargo Ship ($"+allShips[3].getCost()+")\n[5] End phase\n");
				}
				
				//fighting phase, where users purchase Ammo and fire them at their opponent's field
				//alternates turns between player1 and player2, like connect4
				System.out.println("The Battle begins, may the tides turn in your favor!\n");
				while(p1.getFleet().length>0&&p2.getFleet().length>0) {
					
					//every turn, initialize the number of guns used and cannons used
					usedGuns = 0;
					usedCannons = 0;


					//before player 1's turn, check for damages for of player 1's ships
					for(int i = 0; i<p1.getFleet().length; i++) {
						hitCount = 0; 
						//loops through all cells ship i occupies on the field
						for(int j = p1.getFleet()[i].getY(); j<p1.getFleet()[i].getY()+p1.getFleet()[i].getWidth(); j++) {
							for(int k = p1.getFleet()[i].getX(); k<p1.getFleet()[i].getX()+p1.getFleet()[i].getLength(); k++) {
								if(g.getPlayer1Ships()[j][k]==-1) {
									hitCount += 1; //add to the number of hits taken
								}
							}
						}
						//if ship i has been hit the number of the times equal to its hp, delete it from game
						if(hitCount == p1.getFleet()[i].getHp()) {
							System.out.println(p1.getName()+"'s " + p1.getFleet()[i].getName()+" has been sank!");
							p1.delShip(i); //deleting ship from player1's ship array, demonstrating deletion system
						}
					} 
					
					//prompts the user for an action
					System.out.println(">>>"+p1.getName()+"'s turn <<<");
					selection = askInput(sc, "[1] Purchase Ammo\n[2] Attack\n[3] Inventory\n[4] End turn");

					//automatically generates 500 dollars for every ALIVE cargo ship
					System.out.println("Generating balance from cargo ships.");
					for(int i = 0; i<p1.getFleet().length; i++) {
						if(p1.getFleet()[i] instanceof CargoShip) {
							p1.setBal(p1.getBal()+500);
						}
					}
					//keeps prompting the user for actions until 4 is entered
					while(selection!=4) {
						//keeps prompting if selection is out of bounds or invalid
						while(selection!=1 && selection!=2 && selection!=3 && selection!=4) {
							System.out.println("Please enter a valid option");
							selection = askInput(sc, "[1] Purchase Ammo\n[2] Attack\n[3] Inventory\n[4] End turn");
						}
						//buying weapons
						if(selection==1) {
							//prompt user to select a weapon
							selection = askInput(sc, "[1] Basic shot ($"+allWeapons[0].getCost()+")\n[2] Piercing shot($"+allWeapons[1].getCost()+")\n[3] Carpet bomb ($"+allWeapons[2].getCost()+")\n[4] Guided Missle ($"+allWeapons[3].getCost()+")");
							//keep prompting if selection is out of bounds or invalid type
							while(selection!=1 && selection!=2 && selection!=3 && selection!=4) {
								System.out.println("Please enter a valid option");
								selection = askInput(sc, "[1] Basic shot ($"+allWeapons[0].getCost()+")\n[2] Piercing shot($"+allWeapons[1].getCost()+")\n[3] Carpet bomb ($"+allWeapons[2].getCost()+")\n[4] Guided Missle ($"+allWeapons[3].getCost()+")");
							}
							//if player1 can afford weapon
							if(p1.getBal()-allWeapons[selection-1].getCost()>=0) {
								//add weapon, update balance, and print player1's inventory
								p1.addWeapon(selection);
								p1.setBal(p1.getBal()-allWeapons[selection-1].getCost());
								printInventory(p1);
							}
							else {
								//notify user if too expensive
								System.out.println("Insufficient funds.");
							}
						}
						//attacking
						else if(selection == 2) {
							g.showPlayer1Field(); //show player1's field
							
							//count the number of guns and cannons in player 1's current fleet
							gunCount = 0;
							cannonCount = 0;
							for(int i = 0; i<p1.getFleet().length; i++) {
								gunCount += p1.getFleet()[i].getNumGuns();
								cannonCount += p1.getFleet()[i].getNumCannons();
							}
							//print the times that your fleet can fire
							System.out.println("Your fleet has a total of "+(gunCount-usedGuns)+" gunshots and "+(cannonCount-usedCannons)+" cannon shots left this turn");
							
							//prompt user to select a weapon to use
							selection = askInput(sc, "[1] Basic shot (1 tile)\n[2] Piercing shot (3 vertical tiles)\n[3] Carpet bomb (2x2 area)\n[4] Guided Missle (nearest tile with enemy from tile)");
							//keep prompting if selection is out of range or invalid type
							while(selection!=1 && selection!=2 && selection!=3 && selection!=4) {
								System.out.println("Please enter a valid option");
								selection = askInput(sc, "[1] Basic shot (1 tile)\n[2] Piercing shot (3 vertical tiles)\n[3] Carpet bomb (2x2 area)\n[4] Guided Missle (nearest tile with enemy from tile)");
							}
							
							//prompt player for coordinates of attack
							attackX = askInput(sc, "Enter the x-coordinate");
							attackY = askInput(sc, "Enter the y-coordinate");
							
							//selected basic shot
							if(selection == 1) {
								//if there are gunshots remaining and the user has a BasicShot in inventory
								if(usedGuns<gunCount&&p1.getWeaponCount(1)>0) { 
									//attack player 2 and update usedGuns
									new BasicShot().p1Attack(p1, g, attackX, attackY);
									usedGuns++;
								}
								else {
									//notifies user if can't fire
									System.out.println("Cannot attack. Check the gun shots you have left as well as your inventory");
								}
							}
							//selected piercing shot
							else if(selection == 2) { 
								//if there are gunshots remaining and the user has a PiercingShot in inventory
								if(usedGuns<gunCount&&p1.getWeaponCount(2)>0) {
									//attack player 2 and update usedGuns
									new PiercingShot().p1Attack(p1, g, attackX, attackY);
									usedGuns++;
								}
								else {
									//notifies user if can't fire
									System.out.println("Cannot attack. Check the guns you have left as well as your inventory");
								}
							}
							//selected carpet bomb
							else if(selection == 3) {
								//if there are cannon shots remaining and the user has a CarpetBomb in inventory
								if(usedCannons<cannonCount&&p1.getWeaponCount(3)>0) {
									//attack and update usedCannons
									new CarpetBomb().p1Attack(p1, g, attackX, attackY);
									usedCannons++;
								}
								else {
									//notify user if can't attack
									System.out.println("Cannot attack. Check the cannons you have left as well as your inventory");
								}
							}
							//selected guided missle
							else if(selection == 4){
								//if there are cannon shots remaining and the user has a GuidedMissle in inventory
								if(usedCannons<cannonCount&&p1.getWeaponCount(4)>0) {
									//attack and update usedCannons
									new GuidedMissle(p1, p2).p1Attack(p1, g, attackX, attackY);
									usedCannons++;
								}
								else {
									//notify user if can't attack
									System.out.println("Cannot attack. Check the cannons you have left as well as your inventory");
								}
							}
							g.showPlayer1Field(); //show player1's field
						}
						//show the inventory
						else if(selection == 3) {
							printInventory(p1);
						}
						selection = askInput(sc, "[1] Purchase Ammo\n[2] Attack\n[3] Inventory\n[4] End turn");
					}


					//every new turn, initialize the number of guns used and cannons used
					usedGuns = 0;
					usedCannons = 0;

					//before player 2's turn, check for damages from attacks for each of player 2's ships
					for(int i = 0; i<p2.getFleet().length; i++) {
						hitCount = 0; 
						//loops through all cells occupied by ship i on field
						for(int j = p2.getFleet()[i].getY(); j<p2.getFleet()[i].getY()+p2.getFleet()[i].getWidth(); j++) {
							for(int k = p2.getFleet()[i].getX(); k<p2.getFleet()[i].getX()+p2.getFleet()[i].getLength(); k++) {
								if(g.getPlayer2Ships()[j][k]==-1) {
									hitCount += 1; //add to the number of hits
								}
							}
						}
						//if ship i has been hit the number of the times equal to its hp, it is dead
						if(hitCount == p2.getFleet()[i].getHp()) {
							System.out.println(p2.getName()+"'s " + p2.getFleet()[i].getName()+" has been sank!");
							p2.delShip(i); //deletes the ship from the fleet
						}
					} 
					System.out.println(">>>"+p2.getName()+"'s turn <<<");
					//prompt player 2 for an action
					selection = askInput(sc, "[1] Purchase Ammo\n[2] Attack\n[3] Inventory\n[4] End turn");
					
					//generate balance from each of Player 2's ALIVE cargo ships
					System.out.println("Generating balance from cargo ships.");
					for(int i = 0; i<p2.getFleet().length; i++) {
						if(p2.getFleet()[i] instanceof CargoShip) {
							p2.setBal(p2.getBal()+500);
						}
					}
					//keep prompting until 4 is selected
					while(selection!=4) {
						//keep prompted if selection is out of range or invalid type
						while(selection!=1 && selection!=2 && selection!=3 && selection!=4) {
							System.out.println("Please enter a valid option");
							selection = askInput(sc, "[1] Purchase Ammo\n[2] Attack\n[3] Inventory\n[4] End turn");
						}
						//buying ammo
						if(selection==1) {
							//prompting user to select an ammo
							selection = askInput(sc, "[1] Basic shot ($"+new BasicShot().getCost()+")\n[2] Piercing shot($"+new PiercingShot().getCost()+")\n[3] Carpet bomb ($"+new CarpetBomb().getCost()+")\n[4] Guided Missle ($"+new GuidedMissle(p1, p2).getCost()+")");
							//keep prompting if selection is out of range or invalid type
							while(selection!=1 && selection!=2 && selection!=3 && selection!=4) {
								System.out.println("Please enter a valid option");
								selection = askInput(sc, "[1] Basic shot ($"+new BasicShot().getCost()+")\n[2] Piercing shot($"+new PiercingShot().getCost()+")\n[3] Carpet bomb ($"+new CarpetBomb().getCost()+")\n[4] Guided Missle ($"+new GuidedMissle(p1, p2).getCost()+")");
							}
							//if user can afford the weapon
							if(p2.getBal()-allWeapons[selection-1].getCost()>=0) {
								//add weapon, update balance, and print p2's inventory
								p2.addWeapon(selection);
								p2.setBal(p2.getBal()-allWeapons[selection-1].getCost());
								printInventory(p2);
							}
							else {
								System.out.println("Insufficient funds.");
							}
						}
						//attacking
						else if(selection == 2) {
							g.showPlayer2Field(); ////show player2's field
							
							//first count the number of guns and cannons in player 1's current fleet
							gunCount = 0;
							cannonCount = 0;
							for(int i = 0; i<p2.getFleet().length; i++) {
								gunCount += p2.getFleet()[i].getNumGuns();
								cannonCount += p2.getFleet()[i].getNumCannons();
								//	gunCount +=
							}
							//print the number of attacks p2 has left this turn
							System.out.println("Your fleet has a total of "+(gunCount-usedGuns)+" gunshots and "+(cannonCount-usedCannons)+" cannon shots left this turn");
							
							//prompt user to select an ammo
							selection = askInput(sc, "[1] Basic shot (1 tile)\n[2] Piercing shot (3 vertical tiles)\n[3] Carpet bomb (2x2 area)\n[4] Guided Missle (nearest tile with enemy from tile)");
							//keeps prompting if selection out of range or invalid type
							while(selection!=1 && selection!=2 && selection!=3 && selection!=4) {
								System.out.println("Please enter a valid option");
								selection = askInput(sc, "[1] Basic shot (1 tile)\n[2] Piercing shot (3 vertical tiles)\n[3] Carpet bomb (2x2 area)\n[4] Guided Missle (nearest tile with enemy from tile)");
							}
							
							//prompts user for the coordinate to attack
							attackX = askInput(sc, "Enter the x-coordinate");
							attackY = askInput(sc, "Enter the y-coordinate");

							//selected basic shot
							if(selection == 1) {
								//if there are gunshots remaining and player has a BasicShot in their inventory
								if(usedGuns<gunCount&&p2.getWeaponCount(1)>0) { 
									//attack and update usedGuns
									new BasicShot().p2Attack(p2, g, attackX, attackY);
									usedGuns++;
								}
								else {
									System.out.println("Cannot attack. Check the guns you have left as well as your inventory");
								}
							}
							//selected piercing shot 
							else if(selection == 2) { 
								//if there are gunshots remaining and player has a PiercingShot in their inventory
								if(usedGuns<gunCount&&p2.getWeaponCount(2)>0) {
									//attack and update usedGuns
									new PiercingShot().p2Attack(p2, g, attackX, attackY);
									usedGuns++;
								}
								else {
									System.out.println("Cannot attack. Check the guns you have left as well as your inventory");
								}
							}
							//selected carpet bomb
							else if(selection == 3) {
								//if there are cannon shots remaining and player has a CarpetBomb in their inventory
								if(usedCannons<cannonCount&&p2.getWeaponCount(3)>0) {
									//attack and update usedCannons
									new CarpetBomb().p2Attack(p2, g, attackX, attackY);
									usedCannons++;
								}
								else {
									System.out.println("Cannot attack. Check the cannons you have left as well as your inventory");
								}
							}
							//selected guided missle
							else if(selection == 4){
								//if there are cannon shots remaining and player has a GuidedMissle in their inventory
								if(usedCannons<cannonCount && p2.getWeaponCount(4)>0) {
									//attack and update usedCannons
									new GuidedMissle(p1, p2).p2Attack(p2, g, attackX, attackY);
									usedCannons++;
								}
								else {
									System.out.println("Cannot attack. Check the cannons you have left as well as your inventory");
								}
							}
							g.showPlayer2Field(); //show player2's field
						}
						//show the inventory
						else if(selection == 3) {
							printInventory(p2);
						}
						selection = askInput(sc, "[1] Purchase Ammo\n[2] Attack\n[3] Inventory\n[4] End turn");
					}
				}
				//if player 2 has no ships left, player1 has won
				if(p2.getFleet().length==0) {
					//give exp reports for both players
					System.out.println(p1.getName()+" has won!");
					System.out.println("+200 exp for winning");
					System.out.println("+50 exp for playing\n");
					System.out.println(p2.getName()+ " has lost...");
					System.out.println("50 exp for playing");
					
					//update the stats and exp on both profiles
					list.get(player1Index).changeStats(true); //add 1 win to player 1's profile
					list.get(player2Index).changeStats(false); //add 1 loss to player 2's profile
					list.get(player1Index).addExp(150);
					list.get(player2Index).addExp(50);
				}
				//if player 1 has no ships left, player 2 has won
				else if (p1.getFleet().length == 0) {
					//give exp reports for both players
					System.out.println(p2.getName()+" has won!");
					System.out.println("+100 exp for winning");
					System.out.println("+50 exp for playing\n");
					System.out.println(p1.getName()+ " has lost...");
					System.out.println("50 exp for playing");
					
					//update the stats and exp on both profiles
					list.get(player2Index).changeStats(true); //add 1 win to player 2's profile
					list.get(player1Index).changeStats(false); //add 1 loss to player 1's profile
					list.get(player2Index).addExp(150);
					list.get(player1Index).addExp(50);
				}
				System.out.println("Thank you both for playing!\n");

				saveProfiles(list, fileName); //saves all the profiles after exp change

			}
			//Information document - demonstrates inheritance
			else if(selection == 4) {
				//loops and shows the info of all ship types 
				System.out.println("Ships info: ");
				for(int i = 0; i<allShips.length; i++) {
					System.out.println(allShips[i].stringInfo());
					System.out.println("Horizontal placement: ");
					System.out.println(allShips[i].getPlacement()+"\n");
				}
				
				//loops and shows the info of all weapon types
				System.out.println("Ammunition info: ");	
				for(int i = 0; i<allWeapons.length; i++) {
					System.out.println(allWeapons[i].stringInfo()+"\n");
				}
			}
			//Player searching system - demonstrates searching concept
			else if(selection == 5) {

				//initialize variables
				foundName = false;
				foundLvl = false;
				
				//prompt user for searching criteria
				selection = askInput(sc, "\n[1] search by name\n[2] search by level");
				//keep prompting if selection is out of range or invalid type
				while(selection!=1&&selection!=2) {
					System.out.println("Please enter a valid option");
					selection = askInput(sc, "\n[1] search by name\n[2] search by level");
				}
				//selected search by name
				if(selection==1) {
					sc.nextLine();
					//prompt for the name to search for 
					System.out.print("Enter the username to search: ");
					searchedName = sc.nextLine();
					
					//Searching for profile by looping through arraylist
					for(int i = 0; i<list.size(); i++) {
						if(list.get(i).getName().equals(searchedName)) {
							//notifies user if profile found, and print its stats
							System.out.println("Profile found! Retrieving stats...");
							System.out.println(list.get(i).toString());
							
							foundName = true;
						}
					}
					//notify user if profile not found
					if(!foundName) {
						System.out.println("Sorry, no profile could be found...");
					}
				}
				//selected search by level
				else {
					//prompt user for the level to search for 
					searchedLvl = askInput(sc, "Enter the level to search: ");
					
					for(int i = 0; i<list.size(); i++) {
						if(list.get(i).getCurLvl()==searchedLvl) {
							if(!foundLvl) {
								//print this message only when the first profile is found
								System.out.println("Profile(s) found! Retrieving stats...");
								foundLvl = true;
							}
							//print the stats of each profile found with the desired level
							System.out.println(list.get(i).toString()+"\n");
						}
					}
					//notify user if profile not found
					if(!foundLvl) {
						System.out.println("Sorry, no profile could be found...");
					}
				}
			}
			//show menu screen after an action was completed
			System.out.println("<> Welcome to Battleships Plus <>");

			//prompt user to select an action
			selection = askInput(sc, "\n[1] Battle!\n[2] Leaderboards\n[3] Register \n[4] Info\n[5] Search Players\n[6] Quit\n");
			//keep prompting if selection is our of range or invalid type
			while(selection!=1&&selection!=2&&selection!=3&&selection!=4&&selection!=5&&selection!=6) {
				System.out.println("Please enter a valid option");
				selection = askInput(sc, "\n[1] Battle!\n[2] Leaderboards\n[3] Register \n[4] Info\n[5] Search Players\n[6] Quit\n");
			}
		}
		System.out.println("See you next time!");
	}
	public static void saveProfiles(ArrayList<Profile> list, String fileName) {
		try {
			BufferedWriter in = new BufferedWriter(new FileWriter(fileName, false));

			for(int i = 0; i<list.size(); i++) {
				//write all the neccesary fields of each profile
				in.write(list.get(i).getName());
				in.newLine();
				in.write(list.get(i).getLocation());
				in.newLine();
				in.write(list.get(i).getRank());
				in.newLine();
				in.write(Integer.toString(list.get(i).getCurLvl()));
				in.newLine();
				in.write(Integer.toString(list.get(i).getCurExp()));
				in.newLine();
				in.write(Integer.toString(list.get(i).getNeededExp()));
				in.newLine();
				in.write(Integer.toString(list.get(i).getWins()));
				in.newLine();
				in.write(Integer.toString(list.get(i).getLosses()));
				in.newLine();
			}


			in.close(); //close reader
		}
		catch(IOException e) {
			System.out.println(e+" Problem writing "+fileName); //exception message
		}
	}
	/*int searchName(String name, ArrayList<Profile> list)
	  
	  returns int - the index where a name is found in the profile list
	  
	  String name - this parameter is the name that is being searched for
	  ArrayList<Profile> list - this parameter is the list that is being searched from
	  
	  This method searches the profile list and returns the index if a profile is registered 
	  with the name in String name, otherwise return -1;
	
	 */
	public static int searchName(String name, ArrayList<Profile> list) {
		int idx; //declare variable

		idx = -1; //initialize variable
		for(int i = 0; i<list.size(); i++) {
			//compare names
			if(list.get(i).getName().equals(name)) {
				idx = i;
			}
		}
		return idx;	
	}
	
	/*void printInventory(Player p)
	  
	  returns nothing
	  
	  Player p - this parameter is the player whose inventory is being printed
	  
	  This method prints all the items in Player p's current inventory.
	  This includes ships and ammunition.
	 */
	public static void printInventory(Player p) {
		//prints the amount of ammunition of each type
		System.out.println(p.getName()+"'s ammunition: \n");
		System.out.println("Basic Shot x"+p.getWeaponCount(1)+"\nPiercing shot x"+p.getWeaponCount(2)+"\nCarpet Bomb x"+p.getWeaponCount(3)+"\nGuided Missle x"+p.getWeaponCount(4));
		//prints the remaining balance
		System.out.println("Remaining balance: $"+p.getBal()+"\n");

		//prints the info of each of their ships
		System.out.println(p.getName()+"'s fleet: \n");
		for(int i = 0; i<p.getFleet().length; i++) {
			System.out.println(p.getFleet()[i].getName()+":\n"+p.getFleet()[i].stringStats());
		}
	}
	/*void sortByExp(ArrayList<Profile> list)
	  
	  returns nothing
	  
	  ArrayList<Profile> list - this parameter is the list that is being sorted 
	  
	  This method uses the compare function that compares exp along with
	  the selection sort algorithm to sort the list of profiles in descending exp. 
	 */

	public static void sortByExp(ArrayList<Profile> list) {
		//declare variables
		Profile minProf;
		int minIdx;
		Profile tmp;

		for(int i = 0; i<list.size(); i++) {
			//assign minProf to the initial element and minIdx to the initial index
			minProf = new Profile(list.get(i));
			minIdx = i;
			//loop through each element after the current index
			for(int j = i+1; j<list.size(); j++) {
				if(list.get(j).isHigherExp(minProf)) { //compare exp amounts and update minProf and minIdx
					minProf = new Profile(list.get(j));
					minIdx = j;
				}
			}
			//switch the initial element with the element with minIdx as their index
			tmp = new Profile(list.get(i));
			list.set(i, new Profile(list.get(minIdx)));
			list.set(minIdx, new Profile(tmp));
		}
	}
	/*void sortByWinrate(ArrayList<Profile> list)
	  
	  returns nothing
	  
	  ArrayList<Profile> list - this parameter is the list that is being sorted 
	  
	  This method uses the compare function that compares winrates along with
	  the selection sort algorithm to sort the list of profiles in descending winrates. 
	 */
	//this method uses selection sort to sort the list of profiles in descnending winrate
	public static void sortByWinrate(ArrayList<Profile> list) {
		//declare variables
		Profile minProf;
		int minIdx;
		Profile tmp;

		for(int i = 0; i<list.size(); i++) {
			//assign minProf to the initial element and minIdx to the initial index
			minProf = new Profile(list.get(i));
			minIdx = i;
			//loop through each element after the current index
			for(int j = i+1; j<list.size(); j++) { 
				if(list.get(j).isHigherWinrate(minProf)) { //compare winrates and update minProf and minIdx
					minProf = new Profile(list.get(j));
					minIdx = j;
				}
			}
			//switch the initial element with the element with minIdx as their index
			tmp = new Profile(list.get(i));
			list.set(i, new Profile(list.get(minIdx)));
			list.set(minIdx, new Profile(tmp));
		}
	}
	/* int  askInput(Scanner sc, String prompt)                  

	   returns int  - the value of the input if the user enters a valid int 

	   Scanner sc - this parameter lets the method use the scanner to scan in values
	   String prompt - this parameter is the question asked by the program for each input	

	   This method takes the Scanner and prompt to ask the user to enter a int  value. If the user
	   enters an invalid type (bad data), it continuously asks until the user enters the correct type. 

	 */

	public static int askInput(Scanner sc, String prompt) {
		//declaring variables
		int num;
		boolean inputValid;

		//initializing variables
		num = -1;
		inputValid = false;

		do
		{
			try
			{
				//prompt user for an integer value
				System.out.print(prompt);
				num = sc.nextInt();

				inputValid = true; //if no exception encountered, input is consider valid
			}
			catch (InputMismatchException e)
			{
				System.out.println("Invalid input type  " + e); //exception message
				sc.nextLine();
			}
		} while (!inputValid); //continues looping if input is not valid

		return num; //returns the valid input
	}
}
