package Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import ALexico.Estructuras;
import ASintactico.Parser;
import Assembler.Ensamblador;

public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args){
		Estructuras tablas = new Estructuras();
		Parser p = new Parser();
		p.run();
		Ensamblador elCapo = new Ensamblador();
		elCapo.ensamblar(p.getPolaca());
		try{
//		File arch = new File("salida.asm");
//		PrintWriter pp = new PrintWriter(new FileWriter(arch));
//		//Imprimir codigo assembler
//
//		String comc = "cmd /c .\\masm32\\bin\\ml /c /Zd /coff salida.asm ";
//		Process ptasm32 = Runtime.getRuntime().exec(comc);
//		InputStream is = ptasm32.getInputStream();
//
//		String coml = "cmd /c \\masm32\\bin\\Link /SUBSYSTEM:CONSOLE salida.obj ";
//		Process ptlink32 = Runtime.getRuntime().exec(coml);
//		InputStream is2 = ptlink32.getInputStream();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("HUBO EXCEPCION");
			// TODO: handle exception
		}
		System.out.println("Presione una tecla para finalizar");
		try {
			char a = (char)System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
