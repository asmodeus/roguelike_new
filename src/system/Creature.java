package system;

import java.util.ArrayList;

public abstract class Creature {
	private int creatureID = 0;
	private static int creaturesCreated = 0;
	protected Sheet sheet = new Sheet();
	private ArrayList<Item> inventory = new ArrayList<Item>();
	private boolean described = true;
	private String name;

	// here is a random item for testing purposes
	Item weapon;

	// situational variables, changes for each tick in the game
	int cover = 1;
	
	public void setID(int id){
		creatureID = id;
	}
	public void setName(String str){
		name = str;
	}
	
	public int getID(){
		return creatureID;
	}
	
	public String getName(){
		return name;
	}
	
	void setRandomWeapon() {
		switch (Functions.rdnNum(0, 5)) {
			case 0:{
				inventory.add(new Item("rusty nail", 5, 1));
				setWeapon(inventory.get(0));
				break;
			}
			
			case 1: {
				inventory.add(new Item("handaxe", 30, 25));
				setWeapon(inventory.get(0));
				break;
			}
	
			case 2: {
				inventory.add(new Item("hammer", 25, 15));
				setWeapon(inventory.get(0));
				break;
			}
	
			case 3: {
				inventory.add(new Item("machete", 35, 75));
				setWeapon(inventory.get(0));
				break;
			}
	
			case 4: {
				inventory.add(new Item("power drill", 40, 500));
				setWeapon(inventory.get(0));
				break;
			}
	
			case 5: {
				inventory.add(new Item("combat knife", 50, 400));
				setWeapon(inventory.get(0));
				break;
			}
		}
	}

	Creature() {// if you didnt write a name, you will be called Sven Svensson
				// forever, since its the most generic i can think of..
		this("Sven Svensson");
	}

	Creature(String name) {
		creaturesCreated++;
		creatureID = creaturesCreated;
		this.name = name;
	}

	public void addToInventory(Item itm){
		this.inventory.add(itm);
	}
	
	public String getDesc() { // returns strings to the info screen
		String retStr = "";
		retStr = "- Description -\nThis is a " + this.className() + " it is "
				+ this.sheet.age + " years old. It looks like it has "
				+ this.sheet.str + " strength and " + this.sheet.agi
				+ " agility. Is is weilding a(n) " + this.weapon.name;
		return retStr;
	}

	public String className() {
		String str = this.getClass().getName().toLowerCase();
		return str.replaceAll("system.", "");
	}

	public String getInventory() {
		String str = "- Inventory -";
		for (Item i : inventory) {
			int n = 1;
			str += "\n"+n + ". It's a " + i.name + " it looks like its worth "
					+ i.cost + " coins and does " + i.dmg + " damage";
			n++;
		}
		return str;
	}

	public String name() {
		return name;
	}

	public void setWeapon(Item i) {
		weapon = i;
	}

	public Sheet getSheet() {
		return this.sheet;
	}
	public boolean isDescribed() {
		return described;
	}
	public void setDescribed(boolean described) {
		this.described = described;
	}

}
