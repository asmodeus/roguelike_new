package system;

public class Zombie extends Creature {
private static final int LIFE_ZOMBIE = 50;

	char code = 'H';
	
	public Zombie(String name){ //this took a long time to get it to feel "realistic" and then i realized i didn't have any game system
		super(name);           //which means that everything i did here doens't have any real meaning.
				
		sheet.age += Functions.rdnNum(52, 76);
		double agevar = 1.0 - sheet.age*0.01;
		
		sheet.inst =  Functions.rdnNum(8, 10);
		sheet.smart = (10 - sheet.inst);
		
		sheet.str += (Functions.rdnNum(50, 70) * agevar)+ 0.5;
		sheet.agi += (Functions.rdnNum(50, 70) * agevar)+ 0.5;
		
		sheet.skillpoints += (( ((sheet.age/2)*(1+sheet.smart)) + 2.5)/5)+Functions.rdnNum(5, 10);
		
		
		sheet.life = (int) (LIFE_ZOMBIE + 50*agevar) + sheet.str; 
		
		sheet.mechanic = false;
		sheet.undead = false;
		sheet.talkable = true;
	
		sheet.generate();
		this.setWeapon(new Item("claws", 8, 1));
	}
	
}
