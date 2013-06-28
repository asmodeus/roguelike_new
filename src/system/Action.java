package system;



public class Action {

	
	public static String attack(Creature attacker, Creature target, final String atktype, int distance) {
		double dodgeMod = 1;
		double distanceMod = 1.0 - (distance/100);
		double coverMod = target.cover;
		int hitRoll = Functions.rdnNum(0, 100);
		int skillChance = 0;
		int damage = 0;
		
		
		if (attacker.getID() == target.getID())
			distanceMod = 1.0;
			
		switch (atktype) {
		
		case "MELEE":
			dodgeMod = 1;
			distanceMod = 2; //100% increased chance for melee attack
			{
				skillChance = (int) (attacker.sheet.melee*distanceMod*coverMod);
				damage = (attacker.sheet.baseMeleeDamage + attacker.weapon.dmg);
			}
			
		case "UNARMED":
			dodgeMod = 0.8;  //20% harder to dodge unarmed attacks
			distanceMod = 2; //100% increased chance for melee attack
			{
				skillChance = (int) (attacker.sheet.unarmed*distanceMod*coverMod);
				damage = (attacker.sheet.baseMeleeDamage);
			}
			
		case "GUNS":
			dodgeMod = 0.2; //80% decreased chance to dodge due to bullet speed
			{
				skillChance = (int) (attacker.sheet.guns*distanceMod*coverMod);
				damage = (attacker.weapon.dmg);
			}
			
		case "RANGED":
			dodgeMod = 0.5; //50% decreased chance to dodge due to "arrow" speed
			{
				skillChance = (int) (attacker.sheet.guns*distanceMod*coverMod);
				damage = (attacker.weapon.dmg);
			}
			
		default:
			System.out.println("dice roll: "+hitRoll+"\n"+"chance of sucesss: "+skillChance);
			boolean critMiss = false;
			boolean critHit = false;
			if (hitRoll>95) {
				critMiss = true; //attacker misses the attack, and the next turn
				System.out.println("Critical Miss!");
			}
			
			
			if (hitRoll<5) {
				critHit = true; //attacker damage doubled. Target must make two dodge checks to avoid damage 
				System.out.println("Critical Hit!"); 
			}
			
			if (hitRoll<=skillChance && !critMiss){ //If the attack is a hit these statements execute
				System.out.println("attack hit");
				int dodgeChance = (int) (target.sheet.dodge*dodgeMod);
				int dodgeRoll = Functions.rdnNum(0, 100); 
				if (dodgeRoll>dodgeChance || critHit){ //critical hits cannot be dodged
					double dmgReduc = 1.0 - target.sheet.toughness*0.01;
					String critStr = "";
					if (critHit) {dmgReduc = 2;
						critStr = " critically";
					}
					int dmg_tot = (int) ((damage*dmgReduc)*((1.05)*Functions.rdnNum(1, 2)));
					target.sheet.life -= dmg_tot;
					return attacker.getName()+critStr+" strikes opponent for "+dmg_tot+" with his "+attacker.weapon.name;
				}
				else //dodge success
					return attacker.getName()+" tries to strike but his opponent dodged";
			}
			else {//hit missed
				return attacker.getName()+" tries to strike but he missed";
			}

		}
	}
}
