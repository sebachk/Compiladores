package accionesSemanticas;

import java.io.BufferedInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import ALexico.AnalizadorLexico;
import ALexico.Estructuras;
import ALexico.TokenCreator;


public class ASFinId implements AccionSemantica {
	/**Vuelve la marca hacia atras **/
	
	String[] pr=AnalizadorLexico.PALABRAS_RESERVADAS;
	@Override
	public void Execute(BufferedInputStream f, char c, TokenCreator tc) {
		try {
			f.reset();
			String cadena=tc.getString();
			for(int i=0;i<pr.length;i++){
				if(cadena.equals(pr[i])){
					//Es una palabra reservada
					tc.createToken(cadena,-1);
					return;
				}
			}
			if(cadena.length()>15){

				Estructuras.addError("Warning en linea"+AnalizadorLexico.LineasContadas+": WARNING: El identificador "+cadena+" tiene un nombre muy largo, sera truncado");
				cadena=cadena.substring(0, 14)+"~";
			}
			int posicion=Estructuras.enTupla(cadena);
			if(posicion==-1)
				posicion=Estructuras.addTupla(cadena);
			tc.createToken(Estructuras.ID,posicion);	
		} catch (IOException e) {e.printStackTrace();}
	}
}
