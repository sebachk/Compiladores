package Main;

import java.io.File;
import java.io.IOException;

import ALexico.Estructuras;
import ASintactico.Parser;

public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args){
		Estructuras tablas = new Estructuras();
		Parser p = new Parser();
		p.run();
		System.out.println("Presione una tecla para finalizar");
		try {
			char a = (char)System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
