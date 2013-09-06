package accionesSemanticas;

import java.io.FileReader;
import java.io.IOException;

import compiler.Estructuras;

public class ASFinCTE implements AccionSemantica{

	@Override
	public void Execute(FileReader f, char c, TokenCreator tc) {
			try {
				f.reset();
				//Cequear Rango >0 <2^16-1;
				String cadena=tc.getString();
				int valor= Integer.parseInt(cadena);
				if(valor<0 || valor>Math.pow(2, 16)-1){
					//Error lexico
				}
				int posicion=Estructuras.addTupla(cadena);
				tc.createToken(cadena,posicion);
				
				
			} catch (IOException e) {e.printStackTrace();}
	}

}
