package compiler;

import java.io.File;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Estructuras tablas = new Estructuras();
		File archivo = new File("Docs/codigo.txt");
		AnalizadorLexico AL = new AnalizadorLexico(archivo);
		int p=4;
		String cadena ="NDD";
		if(p>4)
			if(cadena=="hola")
				System.out.println("nadA");
			else
				System.out.println("otra cosa");
		
		
		while(AnalizadorSintactico.pueda_tokenear(AL)){
			AnalizadorSintactico.tokenear();
		}
		tablas.PrintTablaS();
	}

}
