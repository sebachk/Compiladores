package compiler;

import java.io.File;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Estructuras tablas = new Estructuras();
		File archivo = new File("archivo.txt");
		AnalizadorLexico AL = new AnalizadorLexico(archivo);
		
		while(AnalizadorSintactico.pueda_tokenear(AL)){
			AnalizadorSintactico.tokenear();
		}
		tablas.PrintTablaS();
	}

}
