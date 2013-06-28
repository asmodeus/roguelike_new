package textarea;

import java.util.ArrayList;
import java.util.Collections;

import system.Functions;

public class Mask extends Map {

	ArrayList<MaskObject> mapObjects = new ArrayList<MaskObject>();

	char[] liveCreatures = new char[100];
	private MaskObject hero;

	Mask() {
		super();
		this.setLiveCreatures();
	}

	public MaskObject getObjectByPosition(int x, int y) {
		for (MaskObject o : mapObjects) {
			if (o.posX == x && o.posY == y) {
				return o;
			}
		}
		return null;
	}

	public void setLiveCreatures() {
		liveCreatures[0] = 'Z';
		liveCreatures[1] = 'H';
	}

	public void printObjects() {
		for (MaskObject o : mapObjects)
			o.printInfo();
	}

	public MaskObject getHero() {
		return hero;
	}

	public void setHero(MaskObject mo) {
		hero = mo;
	}

	public MaskObject findHero() {
		for (MaskObject o : mapObjects) {
			if (o.name == '@')
				return o;
		}
		return null;
	}

	
	public void saveMapObjects() {
		mapObjects.clear();
		char[][] mask = this.getAsciiMap();

		for (int i = 0; i < 96; i++) {
			for (int j = 0; j < 96; j++) {
				if (mask[i][j] != '.') { // the mask skips dots and save
											// everything else into a maskObject
											// array

					mapObjects.add(new MaskObject(i, j, mask[i][j]));

				}
			}
		}
		detectNearbyObjects();
		setHero(findHero());
	}

	public void detectNearbyObjects() {
		for (MaskObject o1 : mapObjects) { // the coordinates are wrong, but its
											// too annoying to change all of the
											// code so it will stay this way
			for (MaskObject o2 : mapObjects) {
				int checkX;
				int checkY;

				checkX = o2.posX - o1.posX;
				checkY = o2.posY - o1.posY;
				if ((checkX == -1 || checkX == 0 || checkX == 1)
						&& (checkY == -1 || checkY == 0 || checkY == 1))
					o1.setBlockedAtDirection(checkX, checkY);

				/*
				 * // all the checking for the objects are done in
				 * the above four lines 
				 * east = false; south = false; 
				 * west = false; north = false;
				 * 
				 * if ((o1.posX == (o2.posX-1)) && ( o1.posY == (o2.posY-1) ||
				 * o1.posY == o2.posY || o1.posY == (o2.posY+1) )) north = true;
				 * else if (o1.posX == (o2.posX+1)&& ( o1.posY == (o2.posY-1) ||
				 * o1.posY == o2.posY || o1.posY == (o2.posY+1) )) south = true;
				 * if (o1.posY == (o2.posY-1) && ( o1.posX == (o2.posX-1) ||
				 * o1.posX == o2.posX || o1.posX == (o2.posX+1) )) west = true;
				 * else if (o1.posY == (o2.posY+1) && ( o1.posX == (o2.posX-1)
				 * || o1.posX == o2.posX || o1.posX == (o2.posX+1) )) east =
				 * true;
				 * 
				 * if (north && west) o1.setBlockedNearby(7); else if (north)
				 * o1.setBlockedNearby(6); else if (north && east)
				 * o1.setBlockedNearby(5); else if (west)
				 * o1.setBlockedNearby(4); else if (east)
				 * o1.setBlockedNearby(3); else if (south && west)
				 * o1.setBlockedNearby(2); else if (south)
				 * o1.setBlockedNearby(1); else if (south && east)
				 * o1.setBlockedNearby(0);
				 */
			}
		}
	}

	public void redraw() {
		char[][] mapp = new Map().getAsciiMap(); // reloads a new ascii map with
													// '.' to write the mask
													// upon
		for (MaskObject o : mapObjects) {
			if (o.posX > 95) // makes sure that no objects have walked outside
								// the scope of the array
				o.posX = 95;
			if (o.posX < 0)
				o.posX = 0;
			if (o.posY > 95)
				o.posY = 95;
			if (o.posY < 0)
				o.posY = 0;
			if (o.getCreature() == null)			
				mapp[o.posX][o.posY] = o.name;
			else if (o.getCreature().getSheet().getLife() > 0)
				mapp[o.posX][o.posY] = o.name;
		}
		this.setAsciiMap(mapp);
	}

	public void mergeMapOntoMap(Map m) {
		char[][] mask = this.getAsciiMap();
		char[][] map = m.getAsciiMap();
		for (int i = 0; i < 96; i++)
			for (int j = 0; j < 96; j++) {
				if (mask[i][j] != '.' && map[i][j] == '.') // the mask skips
															// dots and load
															// everything else
															// onto the map
					map[i][j] = mask[i][j];
			}
		m.setAsciiMap(map);
	}

	public void tick() {
		this.tick("");
	}

	public void tick(String arg) {
		int offsetX = 0;
		int offsetY = 0;

		if (arg == "x++") { // increases horizontal
			// Fix collision detection of player and initiate encounter if the
			// current space is occupied here
			offsetY++;
		}

		if (arg == "x--") {
			offsetY--;
		}

		if (arg == "y++") { // increases vertical
			offsetX++;
		}

		if (arg == "y--") {
			offsetX--;
		}

		ArrayList<MaskObject> backupMapObjects = new ArrayList<MaskObject>(
				mapObjects.size());

		for (MaskObject o : mapObjects) { // creating new maskobjects in the new
											// array. performing a deepcopy
			MaskObject copy = new MaskObject(o);
			backupMapObjects.add(copy);
			backupMapObjects.trimToSize();
		}

		for (MaskObject o : mapObjects) {
			if (o.name == '@') {
				o.posX += offsetX;
				o.posY += offsetY;
			}

			for (char c : liveCreatures) {
				if (o.name == c) {
					int moveX = Functions.rdnNum(-1, 1);
					int moveY = Functions.rdnNum(-1, 1);
					o.checkAnyBlockedIfTrue();
					// o.printInfo();
					if (!(o.getBlockedAtDirection(moveX, moveY))) {
						o.posX += moveX;
						o.posY += moveY;
					}
				}
			}
		}

		for (MaskObject o1 : mapObjects) {

			for (MaskObject o2 : mapObjects) {
				boolean isPlayer = false;
				if (o2.name == '@')
					isPlayer = true;

				if ((o1.posX == o2.posX) && (o1.posY == o2.posY) && !(o1 == o2)) {

					int num = Functions.rdnNum(0, 1);
					if (isPlayer) {
						int o1Index = mapObjects.lastIndexOf(o1);
						MaskObject last = backupMapObjects.get(o1Index);
						o1.posX = last.posX;
						o1.posY = last.posY;
					}

					else if (num == 0) {
						int o1Index = mapObjects.lastIndexOf(o1);
						MaskObject last = backupMapObjects.get(o1Index);
						o1.posX = last.posX;
						o1.posY = last.posY;
					}

					else if (num == 1) {
						int o2Index = mapObjects.lastIndexOf(o2);
						MaskObject last = backupMapObjects.get(o2Index);
						o2.posX = last.posX;
						o2.posY = last.posY;
					}
				}
			}
		}
	}
}
