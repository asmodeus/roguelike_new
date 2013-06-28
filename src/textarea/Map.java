package textarea;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Map {

	private char[][] asciiMap = new char [96][96];

	Map(){ //constructs a new asciiMap with '.' inside
		for (int i = 0; i < 96 ; i++){
			for (int j = 0; j < 96 ; j++)
					asciiMap[i][j] = '.';	
		}
	}
	
	public char[][] getAsciiMap(){
		return asciiMap;
	}
	
	public void setAsciiMap(char[][] m){
		asciiMap = m;
	}
	
	public void loadMap(String arg) {
			BufferedReader in;
			try {
				in = new BufferedReader(new FileReader(arg));
				for (int i = 0 ; i < 96 ; i++)
					asciiMap[i] = in.readLine().toCharArray();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	
	
	public void printMap(){
		for(int i = 0 ; i < 96 ; i++)
			System.out.println(asciiMap[i]);
	}
	
	public void findChar (int x, int y){	
	}
	
	public String getScreen(int x, int y) throws StringIndexOutOfBoundsException, ArrayIndexOutOfBoundsException {
		String retStr = "";
		for (int i = 0; i < 12 ; i++){
			String str = new String(asciiMap[y]);
			retStr += str.substring(x,(x+32));
			y++;
		}
		System.out.println(x +" "+ y);
		return retStr;
	}
	
	public static void main(String[] args) throws IOException {	
		Map map = new Map();		
		map.loadMap("asciimap.txt");
		map.getScreen(0, 0);
	}
}