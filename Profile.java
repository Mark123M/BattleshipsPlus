package Battleships;
/*
	Profile.java
	Author:  Mark Ma
	Class:  ICS4U
	Last Modified:  June 24th, 2021
	This class is a class that represents gaming profiles for battleships. It contains the 
	appropriate fields and is also capable of performing several
	operations. 
*/
public class Profile {
	//instance fields of Profile
	private String userName, location, rank;
	private int curLvl, curExp, neededExp, win, loss;

	/*Profile(String userName, String location)
	  
	  returns Profile - the instance created by constructor
	  
	  String userName - this parameter is the name of the Profile instance
	  String location - this parameter is the country of the Profile
	  
	  This constructor creates a new Profile based on the given parameters.

	 */
	public Profile(String userName, String location) {
		this.userName = userName;
		this.location = location;
		this.rank = "|Recruit|";  
		this.curLvl = 1;
		this.curExp = 0;
		this.neededExp = 100;
		this.win = 0;
		this.loss = 0;
	}
	/*Profile(String userName, String location)
	  
	  returns Profile - the instance created by constructor
	  
	  String userName - this parameter is the name of the Profile instance
	  String location - this parameter is the country of the Profile
	  String rank - this parameter is the rank of the profile
	  String curLvl - this parameter is the string representation of the level
	  String curExp - this parameter is the string representation of the current exp 
	  String neededExp - this parameter is the string representation the exp needed to level up
	  String win - this parameter is the String representation of the number of wins
	  String lost - this parameter is the String representation of the number of losses
	  
	  This constructor creates a Profile based on the given parameters.
	  This is used for loading a Profile list from a text file

	 */
	public Profile(String userName, String location, String rank, String curLvl, String curExp, String neededExp, String win, String loss) {
		this.userName = userName;
		this.location = location;
		this.rank = rank;
		this.curLvl = Integer.parseInt(curLvl);
		this.curExp = Integer.parseInt(curExp);
		this.neededExp = Integer.parseInt(neededExp);
		this.win = Integer.parseInt(win);
		this.loss = Integer.parseInt(loss);
	}
	/*Profile (Profile other)
	  
	  returns Profile - the instance created by constructor
	  
	  Profile other - this parameter is the Profile that is being copied
	  
	  This constructor creates a Profile that copies the fields of the
	  other Profile parameter

	 */
	public Profile (Profile other) {
		this.userName = other.userName;
		this.location = other.location;
		this.rank = other.rank;
		this.curLvl = other.curLvl;
		this.curExp = other.curExp;
		this.neededExp = other.neededExp;
		this.win = other.win;
		this.loss = other.loss;
	}
	//accessor method for name
	public String getName() {
		return this.userName;
	}
	//accessor method for location
	public String getLocation() {
		return this.location;
	}
	//accessor method for rank
	public String getRank() {
		return this.rank;
	}
	//accessor method for curLvl
	public int getCurLvl() {
		return this.curLvl;
	}
	//accessor method for curExp
	public int getCurExp() {
		return this.curExp;
	}
	//accessor method for neededExp
	public int getNeededExp() {
		return this.neededExp;
	}
	//accessor method for # of wins
	public int getWins() {
		return this.win;
	}
	//accessor method for # of losses
	public int getLosses() {
		return this.loss;
	}
	
	/* void changeExp
	 * 
	 * returns nothing
	 * 
	 * int expGained - this parameter is the number of exp that is going to be added to the profile
	 * 
	 * This method uses recursion to update the player's exp, level, and rank.
	 */
	public void addExp(int expGained) {
		//base case: if the exp added is less than the exp needed to level up
		if(expGained < neededExp - curExp) {
			this.curExp += expGained;
		}
		//recursive case:
		else {
			expGained = expGained - (neededExp-curExp); //subtract the exp needed to level up from the current level
			
			//update the exp bar for the new level
			this.curExp = 0;
			this.neededExp *= 2;
			this.curLvl += 1;
			
			//update rank from level
			updateRank(curLvl);
			
			//recursive method to handle extra exp
			addExp(expGained);
	
		}
	}
	/* void updateRank
	 * 
	 * returns nothing
	 * 
	 * int curLvl - the current level of the player
	 * 
	 * This method updates the player's rank according to what
	 * level they are.
	 */
	public void updateRank(int curLvl) {
		
		//updates the ranks from the player's current level
		//Novice, Apprentice, Experienced, Veteran, Hero
		if(curLvl<=5) {
			this.rank = "|Recruit|"; //novice
		}
		else if(curLvl <= 10) {
			this.rank = "-|Lieutenant|-"; //apprentice
		}
		else if (curLvl <= 15) {
			this.rank = "-[Commander]-"; //experienced
		}
		else if (curLvl <= 25) {
			this.rank = "<[Veteran]>";
		}
		else {
			this.rank = "|<[War Hero]>|";
		}
	}
	/* void changeStats
	 * 
	 * returns nothing
	 * 
	 * boolean win - whether the player won a game or not
	 * 
	 * This method updates the number of wins and losses 
	 */
	public void changeStats(boolean win) {
		
		//if win, add to the number of wins
		if(win) {
			this.win+=1;
		}
		//otherwise, add to the number of losses
		else {
			this.loss+=1;
		}
	}
	/* boolean isHigherExp(Profile other)
	 * 
	 * returns boolean - whether the implicit parameter has more experience that the other parameter
	 * 
	 * Profile other - this parameter is the Profile that is being compared with
	 * 
	 * This method determines and returns  whether the implicit parameter has 
	 * more experience that the other parameter
	 */
	public boolean isHigherExp(Profile other) {
		//compares levels first
		if(this.curLvl>other.curLvl) {
			return true;
		}
		else if (this.curLvl<other.curLvl) {
			return false;
		}//if same level, compare the current exp
		else if (this.curExp>other.curExp) {
			return true;
		}
		else {
			return false;
		}
	}
	/* boolean isHigherWinrate(Profile other)
	 * 
	 * returns boolean - whether the implicit parameter has a higher winrate that the other parameter
	 * 
	 * Profile other - this parameter is the Profile that is being compared with
	 * 
	 * This method determines and returns  whether the implicit parameter has 
	 * a higher winrate that the other parameter
	 */
	public boolean isHigherWinrate(Profile other) {
		//declare variables
		double thisWinrate, otherWinrate;
		
		//calculate winrates
		thisWinrate = (double)this.win/ (this.win + this.loss); //credits to kevin
		otherWinrate = (double)other.win/(other.win+other.loss);
		
		if(thisWinrate>=otherWinrate) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/*String toString()
	 * 
	 *returns String - the String representation of a Profile
	 *
	 *no parameters
	 *
	 *This method creates and returns the string representation of a Profile
	 *with its most important info
	 */
	public String toString() {
		String info;
		
		info = userName+":\nLocation: "+location+"\nRank: "+rank+"\nLevel: "+curLvl+"   Exp: "+curExp+"/"+neededExp+"\nW/L: " + win+" / "+loss;
		
		return info;
	
	}

}
