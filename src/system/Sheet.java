package system;

import java.util.ArrayList;

public class Sheet {
	
	Sheet(){
	}

	int skillpoints;
	int age = 0;// Effects starting attributes
	
	int str = 0;// Strength effects carry cap, melee damage, life, and skills
				// that is influenced by it.
	
	int agi = 0;// Effects dodge, hit chances with ranged weapons. Improves
				// skills that require agility.
	
	int inst;	// Instincts, the opposite of intelligence. Makes a character stronger and
				// deadlier in combat, improves damage reduction.
	
	int smart;	// Intelligence. The opposite of instincts. Effects starting
				// skillpoints, increases social skills and the guns skill.
	
	int life;

	int unarmed;
	int melee;
	int guns;
	int medic;
	
	// secondary attributes
	int baseMeleeDamage;
	int carryCap;
	int sequence;
	int toughness;
	int dodge;

	private boolean STATSCREATED = false;
	boolean mechanic;
	boolean undead;
	boolean talkable;
	
	protected void generate() {
		if (!STATSCREATED){
			calcSkills();
			calcSecondaryAttrib();
			STATSCREATED = true;
		}
	}

	private void calcSkills() { //sets the skills, this system is somewhat inspired by the fallout 1-2 system
		melee = (str * 3 + agi * 4 + inst * 3) / 10;
		unarmed = (str * 5 + agi * 2 + inst * 3) / 10;
		medic = (smart * 5 + agi * 2) / 10;
		guns = (agi * 5 + smart * 20 + inst * 3) / 10;
	}

	private void calcSecondaryAttrib() {
		baseMeleeDamage = ((str / 10) * inst);
		carryCap = str;
		sequence = (agi / 10) * inst;
		toughness = inst * 2;
		dodge = (agi * 7 + inst * 3) / 10;
	}

	public String getSkills(){
		return "- Skills -\nUnarmed: "+unarmed+"\nMelee: "+melee+"\nGuns: "+guns+"\nMedic: "+medic;
	}
	
	public String getStats(){
		return "Base Melee Dmg: "+baseMeleeDamage+"\nCarry Capacity: "+carryCap+"kg\nDmg Reduction: "+toughness+"%\nSequence: "+"\n"+sequence+"\nDodge: "+"\n"+dodge+"%"+"\nLifepoints: "+"\n"+life;
	}
	
	public int getLife(){
		return life;
	}
}
