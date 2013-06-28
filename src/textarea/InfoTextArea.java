package textarea;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextArea;

public class InfoTextArea extends JTextArea implements KeyListener{

	private char eventKeycode;

	InfoTextArea() {
		super();
		this.addKeyListener(this);
	}
	

	@Override
	public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_A:
					setEventKeycode('A');
					System.out.println(eventKeycode);
					break;
				case KeyEvent.VK_T:
					setEventKeycode('T');
					System.out.println(eventKeycode);
					break;
				case KeyEvent.VK_E: {
					setEventKeycode('E');
					System.out.println(eventKeycode);
					break;
				}
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

	public char getEventKeycode() {
		return eventKeycode;
	}

	public void setEventKeycode(char eventKeycode) {
		this.eventKeycode = eventKeycode;
	}
}
