package textarea;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

import system.Creature;
import system.Encounter;
import system.Functions;
import system.Human;


//Inspiration for this game: Cataclysm: Dark Days Ahead, Fallout 1-2, Hotline Miami, The Walking Dead

public class AsciiJavaGame extends JFrame implements KeyListener, Runnable {
	private static JTextArea gameArea = new JTextArea(); //game area width 32 chars and height 12 chars
	private static InfoTextArea infoArea = new InfoTextArea();
	private static JTextArea statsArea = new JTextArea();
	private BackgroundPanel background = new BackgroundPanel();
	private JPanel gameScreen = new JPanel();
	private Font consoleFont = new Font("monospaced",Font.PLAIN,20);
	private Font infoFont = new Font("monospaced",Font.PLAIN,12);
	private Font statsFont = new Font("monospaced",Font.PLAIN,11);
	private static int xAxis = 0;
	private static int yAxis = 0;
	private static Map currentMap; // not used
	private static Mask currentMask;
	private static Creature currentCreature = null;
	private static MaskObject hero;
	private boolean getDesc = true;

	private int xOffset = 0;
	private int yOffset = 0;
	
	AsciiJavaGame(){
		setTitle("Rougelike Game v0.003 Pre-Alpha");
		setResizable(false);
		setSize(700,750);
		
		this.add(background);
		Color myColor = new Color(50,109,190);
		Color almostBlack = new Color(20,20,20);
		Color almostBlack2 = new Color(25,25,25);
		
		background.setLayout(new FlowLayout(1,0,30));
		background.setBorder(BorderFactory.createLoweredSoftBevelBorder());
		
		gameScreen.setPreferredSize(new Dimension (500, 490)); //don't change
		gameScreen.setLayout(new BorderLayout());
		gameScreen.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		background.add(gameScreen);
		
		statsArea.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		statsArea.setPreferredSize(new Dimension (110, 0));
		statsArea.setBackground(almostBlack2);
		statsArea.setFont(statsFont);
		statsArea.setLineWrap(true);
		statsArea.setForeground(Color.white);
		statsArea.setWrapStyleWord(true);
		statsArea.setEditable(false);
		statsArea.setFocusable(false);
		statsArea.setForeground(Color.orange);
		
		infoArea.setForeground(Color.GREEN);
		infoArea.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		infoArea.setPreferredSize(new Dimension (400, 150));
		infoArea.setBackground(almostBlack);
		infoArea.setFont(infoFont);
		infoArea.setLineWrap(true);
		infoArea.setWrapStyleWord(true);
		infoArea.setEditable(false);
		infoArea.setText("\t\t\t- Info Area -");
		infoArea.append("\nWelcome to the pre-aplha of my Rougelike Adventure Game! 'd' gives a description of your character, 'i' looks at inventory, 's' looks at skills");
		
		gameArea.setPreferredSize(new Dimension (400, 360));
		gameArea.setEditable(true);
		gameArea.setBackground(Color.black);
		gameArea.setForeground(Color.white);
		gameArea.setFont(consoleFont);
		gameArea.setLineWrap(true);
		gameArea.setEditable(false);
		gameArea.addKeyListener(this);
		
		gameScreen.add(gameArea, BorderLayout.CENTER);
		gameScreen.add(infoArea, BorderLayout.SOUTH);
		gameScreen.add(statsArea, BorderLayout.EAST);

		setVisible(true);
	}
	
	public String Quotes(int n){
		switch (n){
		case 1: 
			return "'Don't cry because it's over, smile because it happened.'\n - Dr. Seuss";
		case 2:
			return "'You only live once, but if you do it right, once is enough.'\n- Mae West";
		case 3:
			return "'In three words I can sum up everything I've learned about life: it goes on.'\n- Robert Frost";
		case 4:
			return "'Life is like riding a bicycle. To keep your balance, you must keep moving.'\n- Albert Einstein";			
		default:
			return "'I'm the one that's got to die when it's time for me to die, so let me live my life the way I want to.'\n- Jimi Hendrix";
		}
	}
	
	private void setCurrentMap(Map m){ //sets the current map of this gamesession
		currentMap = m;
	}
	
	private void setCurrentMask(Mask m){ //sets the current mask of this gamesession
		currentMask = m;
	}
	
	private void setCurrentCreature(Creature c){ //sets the playing character
		currentCreature = c;
	}
	
	private void centerViewOn(MaskObject mo){
		xAxis = mo.posX - 18; // centers the view on the hero, this only has to be done once
		yAxis = mo.posY - 3;  
	}
	
	public void updateScreen(Map m, int x, int y) throws StringIndexOutOfBoundsException, ArrayIndexOutOfBoundsException { //updates the screen of this game session
		gameArea.setText(m.getScreen(x, y));
	}
	
	
	public static void main (String args[]){
		AsciiJavaGame testGame = new AsciiJavaGame();
		Mask mask1 = new Mask();
		
		mask1.loadMap("welcome.txt");
		testGame.setCurrentMask(mask1);
		Thread gt = new Thread(testGame);
		gt.start();
		
		String nejm = JOptionPane.showInputDialog(null, null,"Enter name of adventurer", JOptionPane.DEFAULT_OPTION);
		Human player = new Human(nejm);
		testGame.setCurrentCreature(player);
		gt.interrupt();
		
		mask1.loadMap("asciiMask.txt");		
		testGame.setCurrentMask(mask1);
		currentMask.saveMapObjects();
		testGame.centerViewOn(currentMask.getHero());
		testGame.updateScreen(currentMask, xAxis, yAxis);
		testGame.statsArea.setText(currentCreature.getSheet().getStats());
	}
	
	@Override
	public void keyPressed(KeyEvent e) { //the whole game runs on key events, when a certain key is pressed, the game is updated
		hero = currentMask.findHero();
		char objectChar;
		MaskObject mo;
		switch(e.getKeyCode()) { 
					
                case KeyEvent.VK_RIGHT:

                	objectChar = currentMask.getAsciiMap()[hero.posX][hero.posY+1];
                	mo = currentMask.getObjectByPosition(hero.posX, hero.posY+1);
                	
                	if (objectChar != '.'){
                		if ((mo.getCreature()!= null)&&mo.getCreature().isDescribed()){
                			infoArea.setText(mo.getCreature().getDesc());
                			mo.getCreature().setDescribed(false);
                			break;
                		}
                		new Encounter(currentCreature, objectChar, infoArea, mo.getCreature(), currentMask);
                		statsArea.setText(currentCreature.getSheet().getStats());
                		if (currentCreature.getSheet().getLife() <= 0){
                			gameArea.setText(Quotes(Functions.rdnNum(1,	5)));
                			gameArea.setFocusable(false);
                			gameArea.setWrapStyleWord(true);		
                		}
                		break;
                	}
                	
                	System.out.println("RIGHT");
        			xAxis++;
	                currentMask.tick("x++");
	                currentMask.redraw();
	                try{
	                	int offsetVar;
	                	if(xOffset == -1)
	                		offsetVar = -2;
	                	else
	                		offsetVar = xOffset;
	            		updateScreen(currentMask, (xAxis+offsetVar), yAxis);
	                } catch (StringIndexOutOfBoundsException|ArrayIndexOutOfBoundsException ex){
	                	xAxis--;
	                	xOffset++;
	                	updateScreen(currentMask,xAxis,yAxis);
	                	System.err.println("Kanten på mappen slutar här");
	                	System.out.println(xOffset);
	                }
	                break;

                case KeyEvent.VK_LEFT:
                	
                	objectChar = currentMask.getAsciiMap()[hero.posX][hero.posY-1];
                	mo = currentMask.getObjectByPosition(hero.posX, hero.posY-1);
                	
                	if (objectChar != '.'){
                		if ((mo.getCreature()!= null)&&mo.getCreature().isDescribed()){
                			infoArea.setText(mo.getCreature().getDesc());
                			mo.getCreature().setDescribed(false);
                			break;
                		}
                		new Encounter(currentCreature, objectChar, infoArea, mo.getCreature(), currentMask);
                		statsArea.setText(currentCreature.getSheet().getStats());
                		if (currentCreature.getSheet().getLife() <= 0){
                			gameArea.setText(Quotes(Functions.rdnNum(1,	5)));
                			gameArea.setFocusable(false);
                			gameArea.setWrapStyleWord(true);		
                		}
                		break;
                	}
                	
                	
	                System.out.println("LEFT"); 
	                xAxis--;
	                currentMask.tick("x--");
	                currentMask.redraw();
	                try{
	                	int offsetVar;
	                	if(xOffset == 1)
	                		offsetVar = 2;
	                	else
	                		offsetVar = xOffset;
	                	updateScreen(currentMask,(xAxis+offsetVar),yAxis);
	                } catch (StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException ex){
	                	xAxis++;
	                	xOffset--;
	                	updateScreen(currentMask,xAxis,yAxis);
	                    System.err.println("Kanten på mappen slutar här");
	                    System.out.println(xOffset);
	                }
	                break;
	                
                case KeyEvent.VK_UP:
                	
                	objectChar = currentMask.getAsciiMap()[hero.posX-1][hero.posY];
                	mo = currentMask.getObjectByPosition(hero.posX-1, hero.posY);
                	
                	if (objectChar != '.'){
                		if ((mo.getCreature()!= null)&&mo.getCreature().isDescribed()){
                			infoArea.setText(mo.getCreature().getDesc());
                			mo.getCreature().setDescribed(false);
                			break;
                		}
                		new Encounter(currentCreature, objectChar, infoArea, mo.getCreature(), currentMask);
                		statsArea.setText(currentCreature.getSheet().getStats());
                		if (currentCreature.getSheet().getLife() <= 0){
                			gameArea.setText(Quotes(Functions.rdnNum(1,	5)));
                			gameArea.setFocusable(false);
                			gameArea.setWrapStyleWord(true);		
                		}
                		break;
                	}
                	
                	
	                System.out.println("UP");
	                yAxis--;
	                currentMask.tick("y--");
	                currentMask.redraw();
	                try{
	                	int offsetVar;
	                	if(yOffset == 1)
	                		offsetVar = 2;
	                	else
	                		offsetVar = yOffset;
	                	updateScreen(currentMask,xAxis,(yAxis+offsetVar));
	                } catch (StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException ex){ //catches if the code tries to access a part of the gameasciimap array that doesnt exist
	                    yAxis++;																   //this means have tried to move out of the known area. this adds 1 to the yAxis so that the
	                    yOffset--;
	                    updateScreen(currentMask,xAxis,yAxis);                    																		   //"camrea" isnt out of the screen.
	                    System.err.println("Kanten på mappen slutar här");
	                    System.out.println(yOffset);
	                }
	                break;
                
                case KeyEvent.VK_DOWN: 
                	
                	objectChar = currentMask.getAsciiMap()[hero.posX+1][hero.posY];
                	mo = currentMask.getObjectByPosition(hero.posX+1, hero.posY);
                	
                	if (objectChar != '.'){
                		if ((mo.getCreature()!= null)&&mo.getCreature().isDescribed()){
                			infoArea.setText(mo.getCreature().getDesc());
                			mo.getCreature().setDescribed(false);
                			break;
                		}
                		new Encounter(currentCreature, objectChar, infoArea, mo.getCreature(), currentMask);
                		statsArea.setText(currentCreature.getSheet().getStats());
                		if (currentCreature.getSheet().getLife() <= 0){
                			gameArea.setText(Quotes(Functions.rdnNum(1,	5)));
                			gameArea.setFocusable(false);
                			gameArea.setWrapStyleWord(true);		
                		}
                		break;
                	}
                	               	
	                System.out.println("DOWN"); 
	                yAxis++;
	                currentMask.tick("y++");
	                currentMask.redraw();
	                try{
	                	int offsetVar;
	                	if(yOffset == -1)
	                		offsetVar = -2;
	                	else
	                		offsetVar = yOffset;
	            		updateScreen(currentMask,xAxis,(yAxis+offsetVar));
	                } catch (StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException ex){
	                    yAxis--;
	                    yOffset++;
	                    updateScreen(currentMask,xAxis,yAxis);  
	                    System.err.println("Kanten på mappen slutar här");
	                    System.out.println(yOffset);
	                }
	                break;
                
                case KeyEvent.VK_I:
                infoArea.setText(currentCreature.getInventory());   //returns some info to the infoArea
                break;
                
                case KeyEvent.VK_D: 
                infoArea.setText(currentCreature.getDesc());
                break;
                
                case KeyEvent.VK_S:
                infoArea.setText(currentCreature.getSheet().getSkills());
                break;
        } 
	} 

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void run() {
		int x = 0;
		int y = 0;

		while (true){
			if (currentCreature != null)
				break;
			if (x < 50)
				x++;
			if (x > 50)
				x--;
			if (x > 27 && !(y > 40))
				y++;
			if (y > 40)
				y--;
			updateScreen(currentMask, xAxis+x, yAxis+y);
			try {
				Thread.sleep(700);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}