package system;

import textarea.InfoTextArea;
import textarea.Mask;

public class Encounter {
	private char ch;
	private Creature hero;
	private Creature NPC;
	private InfoTextArea infoArea;
	private char eventChar = '1';
	private Mask mask;

	public Encounter(Creature h, char c, InfoTextArea iA, Creature cre, Mask msk) {
		hero = h;
		ch = c;
		NPC = cre;
		infoArea = iA;
		this.mask = msk;
		
		if (cre != null) {
			infoArea.setText(Action.attack(h, cre, "MELEE", 1));
			infoArea.append("\n");

			if (cre.sheet.life > 0)
				infoArea.append(Action.attack(cre, h, "MELEE", 1));
			else {
				infoArea.setText(cre.getName() + " is no more...");
				
				h.addToInventory(cre.weapon);
				infoArea.append("\n You pick up a(n) "+cre.weapon.name);
				msk.redraw();
			}

			if (h.sheet.life <= 0) {
				infoArea.setText("You died!");
				msk.redraw();
			}
		}
		if (cre == null)
			infoArea.setText("You are trying to walk into an occupied space");
	}

}
