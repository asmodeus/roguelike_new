package system;

import java.util.ArrayList;
import java.util.Scanner;

import textarea.MaskObject;
public class Functions {
	
	public static void enterkey(Scanner enter){
		System.out.println("Press enter...");
		enter.useDelimiter("");
		enter.nextLine();
	}
	
	public static int rdnNum(int min, int max){ //randomizes a number between min and max, negative values are possible
		double num = max - min;					//min must always be smaller than max though
		return (int) ((Math.random() * num)+0.5) + min;
	}
	

}
