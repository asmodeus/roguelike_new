package textarea;

import system.Creature;
import system.Human;
import system.Zombie;

public class MaskObject { //this is the MaskObject which right now represents everything that isn't
	int posX, posY;		  //a '.' as a object. Look into the getObjectPos method for more info.
	char name;			 
	private Creature cre = null;
	private boolean [] blocked = new boolean [9]; 	//0 = North West | 1 = N | 2 = NE |  3 = W | 4 = E etc..
	private static int objectsCreated;
	
	private int position [][] = new int[2][8];	
	

	
	public MaskObject(int x, int y, char n){
		this.posX = x;
		this.posY = y;
		this.name = n;
		for (boolean b : this.blocked){
			b = false;
		}
		
		if (name == 'H' && cre == null)
			cre = new Human("Human");
		if (name == 'Z' && cre == null)
			cre = new Zombie("Zombie");
	}
	
	
	public MaskObject(MaskObject from){
		this.posX = from.posX;
		this.posY = from.posY;
		this.name = from.name;
		this.blocked = from.blocked;
		this.position = from.position;
	}
	
	public Creature getCreature(){
		return cre;		
	}
	
	public boolean getBlockedAtDirection(int x, int y){
		{
		if (x == -1 && y == -1)
			return blocked[0];
		if (x == -1 && y == 0)
			return blocked[1];
		if (x == -1 && y == +1)
			return blocked[2];
		if (x == 0 && y == -1)
			return blocked[3];
		if (x == 0 && y == +1)
			return blocked[4];
		if (x == +1 && y == -1)
			return blocked[5];
		if (x == +1 && y == 0)
			return blocked[6];
		if (x == +1 && y == +1)
			return blocked[7];
		}
		return blocked[8]; //False means that all surrounding squares are free
	}
	
	public void setBlockedAtDirection(int x, int y){
		if (x == -1 && y == -1)
			blocked[0] = true;
		if (x == -1 && y == 0)
			blocked[1] = true;
		if (x == -1 && y == +1)
			blocked[2] = true;
		if (x == 0 && y == -1)
			blocked[3] = true;
		if (x == 0 && y == +1)
			blocked[4] = true;
		if (x == +1 && y == -1)
			blocked[5] = true;
		if (x == +1 && y == 0)
			blocked[6] = true;
		if (x == +1 && y == +1)
			blocked[7] = true;
	}
	
	public void checkAnyBlockedIfTrue(){
		for (boolean b: this.blocked)
			if (b == true)
				this.blocked[8] = true;
		this.blocked[8] = false;
	}
	
	public void setBlockedNearby(int n){
		blocked[n] = true;
	}
	
	public boolean isBlockedAt(int n){
		return blocked[n];
	}
	
	void printInfo(){
		System.out.println("pos X: "+posX);
		System.out.println("pos Y: "+posY);
		System.out.println(name);
		int c = 0;
		String dirStr = "";
		for (boolean b : blocked){
			switch (c){
				case 0:
					dirStr = "North-West";
					break;
				case 1:
					dirStr = "North     ";
					break;
				case 2:
					dirStr = "North-East";
					break;
				case 3:
					dirStr = "West      ";
					break;
				case 4:
					dirStr = "East      ";
					break;
				case 5:
					dirStr = "South-West";
					break;
				case 6:
					dirStr = "South     ";
					break;
				case 7:
					dirStr = "South-East";
					break;
				case 8:
					dirStr = "Check Free";
					break;
			}
			
			System.out.println(dirStr+" "+b+" :"+c);
			c++;
		}
	}
}