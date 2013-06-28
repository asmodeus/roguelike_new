package system;

public class Human extends Creature {
private static final int LIFE_HUMAN = 100;

	char code = 'H';
	
	public Human(String name){ //this took a long time to get it to feel "realistic" and then i realized i didn't have any game system
		super(name);           //which means that everything i did here doens't have any real meaning.
				
		sheet.age += Functions.rdnNum(22, 56);
		double agevar = 1.0 - sheet.age*0.01;
		
		sheet.inst =  Functions.rdnNum(3, 7);
		sheet.smart = (10 - sheet.inst);
		
		sheet.str += (Functions.rdnNum(50, 70) * agevar)+ 0.5;
		sheet.agi += (Functions.rdnNum(50, 70) * agevar)+ 0.5;
		
		sheet.skillpoints += (( ((sheet.age/2)*(1+sheet.smart)) + 2.5)/5)+Functions.rdnNum(5, 10);
		
		
		sheet.life = (int) (LIFE_HUMAN + 50*agevar) + sheet.str; 
		
		sheet.mechanic = false;
		sheet.undead = false;
		sheet.talkable = true;
	
		sheet.generate();
		setRandomWeapon();
	}
	
}
